#!/usr/bin/env python3
"""PDF text removal utility.

Given an input folder, this script searches for the exact text `Atos India Pvt Ltd` in every PDF
and redacts it (white-fill) before writing the updated PDF.

This uses PyMuPDF (fitz) because removing visual text from PDFs reliably requires operating
at the page content level.
"""

from __future__ import annotations

import argparse
from dataclasses import dataclass
from pathlib import Path
import tempfile

try:
    import fitz  # PyMuPDF
except Exception as e:  # noqa: BLE001
    raise RuntimeError(
        "Failed to import PyMuPDF (module 'fitz'). This usually happens when the wrong 'fitz' "
        "package is installed instead of 'PyMuPDF'.\n\n"
        "Fix (recommended):\n"
        "  1) pip uninstall -y fitz\n"
        "  2) pip install -U PyMuPDF\n\n"
        "Then re-run this script."
    ) from e


DEFAULT_TARGET_TEXT = "Atos India Pvt Ltd"


class PdfTextRemovalError(Exception):
    pass


@dataclass(frozen=True)
class PdfProcessingResult:
    input_file: Path
    output_file: Path
    pages_scanned: int
    redactions_applied: int


def _redact_text_in_pdf(input_file: Path, output_file: Path, target_text: str) -> PdfProcessingResult:
    if not input_file.exists():
        raise FileNotFoundError(f"Input file not found: {input_file}")
    if input_file.suffix.lower() != ".pdf":
        raise PdfTextRemovalError(f"Input file is not a PDF: {input_file}")

    redactions_applied = 0
    pages_scanned = 0

    try:
        doc = fitz.open(str(input_file))
        try:
            pages_scanned = doc.page_count
            for page in doc:
                rects = page.search_for(target_text)
                if not rects:
                    continue

                for rect in rects:
                    page.add_redact_annot(rect, fill=(1, 1, 1))

                page.apply_redactions()
                redactions_applied += len(rects)

            output_file.parent.mkdir(parents=True, exist_ok=True)
            if input_file.resolve() == output_file.resolve():
                with tempfile.NamedTemporaryFile(
                    mode="wb",
                    suffix=".pdf",
                    prefix=f"{output_file.stem}.__tmp__.",
                    dir=str(output_file.parent),
                    delete=False,
                ) as tmp:
                    tmp_path = Path(tmp.name)

                try:
                    doc.save(str(tmp_path))
                    tmp_path.replace(output_file)
                finally:
                    if tmp_path.exists():
                        try:
                            tmp_path.unlink()
                        except OSError:
                            pass
            else:
                doc.save(str(output_file))
        finally:
            doc.close()

        return PdfProcessingResult(
            input_file=input_file,
            output_file=output_file,
            pages_scanned=pages_scanned,
            redactions_applied=redactions_applied,
        )

    except Exception as e:  # noqa: BLE001
        raise PdfTextRemovalError(f"Failed processing PDF {input_file}: {e}") from e


def remove_text_from_pdfs_in_folder(
    input_folder: str,
    target_text: str = DEFAULT_TARGET_TEXT,
    output_folder: str | None = None,
    recursive: bool = False,
    overwrite: bool = False,
) -> list[PdfProcessingResult]:
    folder = Path(input_folder)

    if not folder.exists():
        raise FileNotFoundError(f"Folder not found: {input_folder}")
    if not folder.is_dir():
        raise PdfTextRemovalError(f"Path is not a directory: {input_folder}")

    if output_folder is None:
        out_dir = folder / "redacted"
    else:
        out_dir = Path(output_folder)

    pattern = "**/*.pdf" if recursive else "*.pdf"
    pdf_files = sorted(folder.glob(pattern))

    results: list[PdfProcessingResult] = []

    for pdf_file in pdf_files:
        rel = pdf_file.relative_to(folder)
        out_file = out_dir / rel

        if out_file.exists() and not overwrite:
            continue

        results.append(_redact_text_in_pdf(pdf_file, out_file, target_text))

    return results


def main() -> None:
    # parser = argparse.ArgumentParser(description="Remove a specific text from all PDFs in a folder")
    # parser.add_argument("input_folder", help="Folder containing PDFs")
    # parser.add_argument(
    #     "--text",
    #     default=DEFAULT_TARGET_TEXT,
    #     help=f"Exact text to remove (default: {DEFAULT_TARGET_TEXT!r})",
    # )
    # parser.add_argument("--output", default=None, help="Output folder (default: <input>/redacted)")
    # parser.add_argument("--recursive", action="store_true", help="Search PDFs recursively")
    # parser.add_argument("--overwrite", action="store_true", help="Overwrite existing output PDFs")
    #
    # args = parser.parse_args()
    #
    # input_folder = args.input_folder
    # target_text = args.text
    # output_folder = args.output
    # recursive = args.recursive
    # overwrite = args.overwrite

    input_folder = "/Users/ynaik1/Library/CloudStorage/GoogleDrive-yogeshrnaik@gmail.com/My Drive/Personal/Jobs/Intuit/Reimbursements/Vodafone/Unclaimed"
    target_text = "Atos India Pvt Ltd"
    output_folder = "/Users/ynaik1/Library/CloudStorage/GoogleDrive-yogeshrnaik@gmail.com/My Drive/Personal/Jobs/Intuit/Reimbursements/Vodafone/Unclaimed"
    recursive = True
    overwrite = True

    results = remove_text_from_pdfs_in_folder(
        input_folder=input_folder,
        target_text=target_text,
        output_folder=output_folder,
        recursive=recursive,
        overwrite=overwrite,
    )

    total = len(results)
    redacted = sum(1 for r in results if r.redactions_applied > 0)
    total_redactions = sum(r.redactions_applied for r in results)

    print(f"Processed PDFs: {total}")
    print(f"PDFs with redactions: {redacted}")
    print(f"Total redactions applied: {total_redactions}")


if __name__ == "__main__":
    main()
