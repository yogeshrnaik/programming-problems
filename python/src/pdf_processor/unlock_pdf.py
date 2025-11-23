#!/usr/bin/env python3
"""
PDF Unlocker Utility

Unlocks password-protected PDF files by removing encryption.
Supports processing single files or entire folders.
"""

import os
import sys
from pathlib import Path
from typing import Optional
from PyPDF2 import PdfReader, PdfWriter


class PdfUnlockError(Exception):
    """Custom exception for PDF unlock operations."""
    pass


def unlock_pdf(input_path: str, password: str, output_path: Optional[str] = None) -> str:
    """
    Unlock a single password-protected PDF file.
    
    Args:
        input_path: Path to the encrypted PDF file
        password: Password to decrypt the PDF
        output_path: Optional output path. If not provided, adds '_unlocked' suffix
        
    Returns:
        Path to the unlocked PDF file
        
    Raises:
        PdfUnlockError: If the PDF cannot be unlocked
        FileNotFoundError: If the input file doesn't exist
    """
    input_file = Path(input_path)
    
    if not input_file.exists():
        raise FileNotFoundError(f"Input file not found: {input_path}")
    
    if not input_file.is_file():
        raise PdfUnlockError(f"Input path is not a file: {input_path}")
    
    if input_file.suffix.lower() != '.pdf':
        raise PdfUnlockError(f"Input file is not a PDF: {input_path}")
    
    # Determine output path
    if output_path is None:
        output_file = input_file.parent / f"{input_file.stem}_unlocked.pdf"
    else:
        output_file = Path(output_path)
        # If output is a directory, create filename in that directory
        if output_file.is_dir():
            output_file = output_file / f"{input_file.stem}_unlocked.pdf"
    
    try:
        # Read the encrypted PDF
        reader = PdfReader(str(input_file))
        
        # Check if PDF is encrypted
        if not reader.is_encrypted:
            raise PdfUnlockError(f"PDF is not encrypted: {input_path}")
        
        # Try to decrypt with the provided password
        if not reader.decrypt(password):
            raise PdfUnlockError(f"Incorrect password for: {input_path}")
        
        # Create a writer object and copy all pages
        writer = PdfWriter()
        for page in reader.pages:
            writer.add_page(page)
        
        # Write the unlocked PDF
        output_file.parent.mkdir(parents=True, exist_ok=True)
        with open(output_file, 'wb') as output_pdf:
            writer.write(output_pdf)
        
        return str(output_file)
    
    except PdfUnlockError:
        raise
    except Exception as e:
        raise PdfUnlockError(f"Failed to unlock PDF {input_path}: {str(e)}")


def unlock_folder(folder_path: str, password: str, output_dir: Optional[str] = None) -> dict:
    """
    Unlock all password-protected PDFs in a folder.
    
    Args:
        folder_path: Path to folder containing PDF files
        password: Password to decrypt the PDFs
        output_dir: Optional output directory. If not provided, creates '_unlocked' suffix files
        
    Returns:
        Dictionary with 'successful', 'failed', and 'skipped' lists containing file info
        
    Raises:
        FileNotFoundError: If the folder doesn't exist
        PdfUnlockError: If the folder path is not a directory
    """
    folder = Path(folder_path)
    
    if not folder.exists():
        raise FileNotFoundError(f"Folder not found: {folder_path}")
    
    if not folder.is_dir():
        raise PdfUnlockError(f"Path is not a directory: {folder_path}")
    
    # Find all PDF files
    pdf_files = list(folder.glob("*.pdf"))
    
    if not pdf_files:
        print(f"⚠️  No PDF files found in: {folder_path}")
        return {'successful': [], 'failed': [], 'skipped': []}
    
    print(f"Found {len(pdf_files)} PDF file(s) in: {folder_path}\n")
    
    results = {
        'successful': [],
        'failed': [],
        'skipped': []
    }
    
    for idx, pdf_file in enumerate(pdf_files, 1):
        print(f"[{idx}/{len(pdf_files)}] {pdf_file.name}")
        print("-" * 70)
        
        try:
            # Check if PDF is encrypted first
            reader = PdfReader(str(pdf_file))
            if not reader.is_encrypted:
                print(f"  ⊘ Skipped: PDF is not encrypted")
                results['skipped'].append({
                    'file': pdf_file.name,
                    'reason': 'Not encrypted'
                })
                print()
                continue
            
            # Unlock the PDF
            output_path = unlock_pdf(str(pdf_file), password, output_dir)
            print(f"  ✓ Successfully unlocked: {Path(output_path).name}")
            results['successful'].append({
                'file': pdf_file.name,
                'output': output_path
            })
        
        except PdfUnlockError as e:
            print(f"  ✗ Failed: {str(e)}")
            results['failed'].append({
                'file': pdf_file.name,
                'error': str(e)
            })
        
        except Exception as e:
            print(f"  ✗ Unexpected error: {str(e)}")
            results['failed'].append({
                'file': pdf_file.name,
                'error': str(e)
            })
        
        print()
    
    # Print summary
    print("=" * 70)
    print("📊 Summary:")
    print(f"  Total PDFs: {len(pdf_files)}")
    print(f"  ✓ Unlocked: {len(results['successful'])}")
    print(f"  ⊘ Skipped: {len(results['skipped'])}")
    print(f"  ✗ Failed: {len(results['failed'])}")
    
    return results


def main():
    """Main entry point for command-line usage."""
    # if len(sys.argv) < 3:
    #     print("Usage: python unlock_pdf.py <file_or_folder_path> <password> [output_path]")
    #     print()
    #     print("Examples:")
    #     print("  # Unlock a single PDF")
    #     print("  python unlock_pdf.py document.pdf mypassword")
    #     print()
    #     print("  # Unlock a single PDF with custom output path")
    #     print("  python unlock_pdf.py document.pdf mypassword unlocked_document.pdf")
    #     print()
    #     print("  # Unlock all PDFs in a folder")
    #     print("  python unlock_pdf.py ./pdfs mypassword")
    #     print()
    #     print("  # Unlock all PDFs in a folder to a different directory")
    #     print("  python unlock_pdf.py ./pdfs mypassword ./unlocked_pdfs")
    #     sys.exit(1)
    
    # input_path = sys.argv[1]
    # password = sys.argv[2]
    # output_path = sys.argv[3] if len(sys.argv) > 3 else None

    input_path = "/Users/ynaik1/Library/CloudStorage/GoogleDrive-yogeshrnaik@gmail.com/My Drive/Personal/Jobs/Intuit/Reimbursements/Jio/Unclaimed"
    password = ""
    output_path = None
    
    try:
        path = Path(input_path)
        
        if path.is_file():
            # Process single file
            print(f"Unlocking PDF: {input_path}")
            print("-" * 70)
            output_file = unlock_pdf(input_path, password, output_path)
            print(f"✓ Successfully unlocked: {output_file}")
        
        elif path.is_dir():
            # Process folder
            unlock_folder(input_path, password, output_path)
        
        else:
            print(f"✗ Error: Path not found: {input_path}")
            sys.exit(1)
    
    except FileNotFoundError as e:
        print(f"✗ Error: {str(e)}")
        sys.exit(1)
    
    except PdfUnlockError as e:
        print(f"✗ Error: {str(e)}")
        sys.exit(1)
    
    except KeyboardInterrupt:
        print("\n\n⚠️  Operation cancelled by user")
        sys.exit(1)
    
    except Exception as e:
        print(f"✗ Unexpected error: {str(e)}")
        sys.exit(1)


if __name__ == "__main__":
    main()
