#!/usr/bin/env python3
"""
Example usage of the PDF unlock utility.

This demonstrates how to use unlock_pdf.py as a module in your own scripts.
"""

from unlock_pdf import unlock_pdf, unlock_folder, PdfUnlockError


def example_single_file():
    """Example: Unlock a single PDF file."""
    print("Example 1: Unlock a single PDF")
    print("-" * 50)
    
    try:
        # Unlock with default output (adds _unlocked suffix)
        output = unlock_pdf("encrypted_document.pdf", "mypassword")
        print(f"✓ Unlocked successfully: {output}")
    except FileNotFoundError as e:
        print(f"✗ File not found: {e}")
    except PdfUnlockError as e:
        print(f"✗ Unlock failed: {e}")
    
    print()


def example_custom_output():
    """Example: Unlock with custom output path."""
    print("Example 2: Unlock with custom output path")
    print("-" * 50)
    
    try:
        output = unlock_pdf(
            "encrypted_document.pdf",
            "mypassword",
            "decrypted_document.pdf"
        )
        print(f"✓ Unlocked successfully: {output}")
    except FileNotFoundError as e:
        print(f"✗ File not found: {e}")
    except PdfUnlockError as e:
        print(f"✗ Unlock failed: {e}")
    
    print()


def example_folder():
    """Example: Unlock all PDFs in a folder."""
    print("Example 3: Unlock all PDFs in a folder")
    print("-" * 50)
    
    try:
        results = unlock_folder("./encrypted_pdfs", "mypassword")
        
        print("\nResults:")
        print(f"  Successful: {len(results['successful'])}")
        for item in results['successful']:
            print(f"    ✓ {item['file']} -> {item['output']}")
        
        print(f"  Failed: {len(results['failed'])}")
        for item in results['failed']:
            print(f"    ✗ {item['file']}: {item['error']}")
        
        print(f"  Skipped: {len(results['skipped'])}")
        for item in results['skipped']:
            print(f"    ⊘ {item['file']}: {item['reason']}")
    
    except FileNotFoundError as e:
        print(f"✗ Folder not found: {e}")
    except PdfUnlockError as e:
        print(f"✗ Operation failed: {e}")
    
    print()


def example_folder_with_output_dir():
    """Example: Unlock folder and save to different directory."""
    print("Example 4: Unlock folder with custom output directory")
    print("-" * 50)
    
    try:
        results = unlock_folder(
            "./encrypted_pdfs",
            "mypassword",
            "./unlocked_pdfs"
        )
        
        print(f"\n✓ Processed {len(results['successful'])} files")
        print(f"  Output directory: ./unlocked_pdfs")
    
    except FileNotFoundError as e:
        print(f"✗ Folder not found: {e}")
    except PdfUnlockError as e:
        print(f"✗ Operation failed: {e}")
    
    print()


if __name__ == "__main__":
    print("PDF Unlock Examples")
    print("=" * 50)
    print()
    
    # Uncomment the examples you want to run:
    
    # example_single_file()
    # example_custom_output()
    # example_folder()
    # example_folder_with_output_dir()
    
    print("Note: Uncomment the examples in the code to run them.")
    print("Make sure you have encrypted PDF files to test with.")
