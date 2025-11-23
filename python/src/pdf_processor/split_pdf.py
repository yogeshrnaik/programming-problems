"""PDF Splitter with Two Modes

This script supports two splitting modes:
1. Medical Mode (for paths containing 'OPD-Claims'):
   - Page 1: Doctor's prescription
   - Page 2: Doctor's consultation bill
   - Page 3 (optional): Medicines bill

2. Simple Mode (for all other PDFs):
   - Splits each page into separate files: filename-1.pdf, filename-2.pdf, etc.
"""

import os
import sys
from pathlib import Path
from PyPDF2 import PdfReader, PdfWriter


def is_medical_pdf(pdf_path: str) -> bool:
    """
    Determine if a PDF should be processed as a medical document.
    
    Args:
        pdf_path: Path to the PDF file
    
    Returns:
        True if path contains 'OPD-Claims', False otherwise
    """
    return "OPD-Claims" in pdf_path


def split_simple_pdf(input_pdf_path: str, output_dir: str = None) -> bool:
    """
    Split a PDF into separate files, one per page.
    
    Args:
        input_pdf_path: Path to the input PDF file
        output_dir: Directory to save the split PDFs (defaults to same directory as input)
    
    Returns:
        True if successful, False otherwise
    
    Raises:
        FileNotFoundError: If the input PDF doesn't exist
    """
    # Validate input file
    input_path = Path(input_pdf_path)
    if not input_path.exists():
        raise FileNotFoundError(f"Input PDF not found: {input_pdf_path}")
    
    # Get base filename without extension
    base_filename = input_path.stem
    
    # Set output directory - create a folder named after the PDF file
    if output_dir is None:
        # Create folder in same directory as input PDF
        output_dir = input_path.parent / base_filename
    else:
        # Create folder inside specified output directory
        output_dir = Path(output_dir) / base_filename
    
    # Create the output directory
    output_dir.mkdir(parents=True, exist_ok=True)
    
    # Read the PDF
    try:
        reader = PdfReader(str(input_path))
        total_pages = len(reader.pages)
        
        print(f"  Processing: {input_path.name}")
        print(f"  Total pages: {total_pages}")
        print(f"  Output folder: {output_dir.name}/")
        print(f"  Mode: Simple (page-by-page)")
        
        # Split and save each page
        for page_index in range(total_pages):
            output_filename = f"{base_filename}-{page_index + 1}.pdf"
            output_path = output_dir / output_filename
            
            # Create a new PDF writer for this page
            writer = PdfWriter()
            writer.add_page(reader.pages[page_index])
            
            # Write to file
            with open(output_path, 'wb') as output_file:
                writer.write(output_file)
            
            print(f"    ✓ Created: {output_filename} (Page {page_index + 1})")
        
        print(f"  ✓ Successfully split into {total_pages} files\n")
        return True
        
    except Exception as e:
        print(f"  ✗ Error: {str(e)}\n", file=sys.stderr)
        return False


def split_medical_pdf(input_pdf_path: str, output_dir: str = None) -> bool:
    """
    Split a medical PDF into separate files for prescription, consultation, and medicines bill.
    
    Args:
        input_pdf_path: Path to the input PDF file
        output_dir: Directory to save the split PDFs (defaults to same directory as input)
    
    Returns:
        True if successful, False otherwise
    
    Raises:
        FileNotFoundError: If the input PDF doesn't exist
        ValueError: If the PDF has fewer than 2 pages
    """
    # Validate input file
    input_path = Path(input_pdf_path)
    if not input_path.exists():
        raise FileNotFoundError(f"Input PDF not found: {input_pdf_path}")
    
    # Get base filename without extension
    base_filename = input_path.stem
    
    # Set output directory - create a folder named after the PDF file
    if output_dir is None:
        # Create folder in same directory as input PDF
        output_dir = input_path.parent / base_filename
    else:
        # Create folder inside specified output directory
        output_dir = Path(output_dir) / base_filename
    
    # Create the output directory
    output_dir.mkdir(parents=True, exist_ok=True)
    
    # Read the PDF
    try:
        reader = PdfReader(str(input_path))
        total_pages = len(reader.pages)
        
        print(f"  Processing: {input_path.name}")
        print(f"  Total pages: {total_pages}")
        print(f"  Output folder: {output_dir.name}/")
        print(f"  Mode: Medical (prescription/consultation/medicines)")
        
        if total_pages < 2:
            raise ValueError(f"PDF must have at least 2 pages (prescription and consultation). Found: {total_pages}")
        
        # Define page mappings
        page_mappings = [
            (0, "prescription", "Doctor's Prescription"),
            (1, "consultation", "Consultation Bill"),
        ]
        
        # Add medicines bill if 3rd page exists
        if total_pages >= 3:
            page_mappings.append((2, "medicines-bill", "Medicines Bill"))
        
        # Split and save each page
        for page_index, suffix, description in page_mappings:
            output_filename = f"{base_filename}-{suffix}.pdf"
            output_path = output_dir / output_filename
            
            # Create a new PDF writer for this page
            writer = PdfWriter()
            writer.add_page(reader.pages[page_index])
            
            # Write to file
            with open(output_path, 'wb') as output_file:
                writer.write(output_file)
            
            print(f"    ✓ Created: {output_filename} ({description})")
        
        print(f"  ✓ Successfully split into {len(page_mappings)} files\n")
        return True
        
    except Exception as e:
        print(f"  ✗ Error: {str(e)}\n", file=sys.stderr)
        return False


def split_pdfs_in_folder(input_folder_path: str, output_dir: str = None) -> None:
    """
    Split all PDF files in a folder into separate files for prescription, consultation, and medicines bill.
    
    Args:
        input_folder_path: Path to the folder containing PDF files
        output_dir: Directory to save the split PDFs (defaults to same directory as input)
    
    Raises:
        FileNotFoundError: If the input folder doesn't exist
        ValueError: If no PDF files are found in the folder
    """
    # Validate input folder
    input_folder = Path(input_folder_path)
    if not input_folder.exists():
        raise FileNotFoundError(f"Input folder not found: {input_folder_path}")
    
    if not input_folder.is_dir():
        raise ValueError(f"Path is not a directory: {input_folder_path}")
    
    # Find all PDF files in the folder
    pdf_files = sorted(input_folder.glob("*.pdf"))
    
    if not pdf_files:
        raise ValueError(f"No PDF files found in folder: {input_folder_path}")
    
    print(f"Found {len(pdf_files)} PDF file(s) in: {input_folder}\n")
    print("=" * 70)
    
    # Process each PDF
    successful = 0
    failed = 0
    
    for pdf_file in pdf_files:
        print(f"\n[{successful + failed + 1}/{len(pdf_files)}] {pdf_file.name}")
        print("-" * 70)
        
        try:
            # Determine which splitting mode to use
            if is_medical_pdf(str(pdf_file)):
                success = split_medical_pdf(str(pdf_file), output_dir)
            else:
                success = split_simple_pdf(str(pdf_file), output_dir)
            
            if success:
                successful += 1
            else:
                failed += 1
        except Exception as e:
            print(f"  ✗ Failed to process: {str(e)}\n", file=sys.stderr)
            failed += 1
    
    # Summary
    print("=" * 70)
    print(f"\n📊 Summary:")
    print(f"  Total PDFs: {len(pdf_files)}")
    print(f"  ✓ Successful: {successful}")
    print(f"  ✗ Failed: {failed}")
    
    if output_dir:
        print(f"\nOutput directory: {Path(output_dir).absolute()}")
    else:
        print(f"\nOutput directory: Same as input folder")


def main():
    """Main entry point for the script."""
    if len(sys.argv) < 2:
        print("Usage: python split_pdf.py <input_pdf_folder_path> [output_directory]")
        print("\nExample:")
        print("  python split_pdf.py ./medical_pdfs")
        print("  python split_pdf.py ./medical_pdfs ./output")
        sys.exit(1)

    input_folder = sys.argv[1]
    output_dir = sys.argv[2] if len(sys.argv) > 2 else None

    # input_folder = "/Users/ynaik1/Library/CloudStorage/GoogleDrive-yogeshrnaik@gmail.com/My Drive/Personal/Jobs/Intuit/OPD-Claims/Unclaimed"
    output_dir = input_folder + "/split_pdfs"
    
    try:
        split_pdfs_in_folder(input_folder, output_dir)
    except Exception as e:
        print(f"\n✗ Failed to process PDFs: {str(e)}", file=sys.stderr)
        sys.exit(1)


if __name__ == "__main__":
    main()
