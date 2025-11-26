"""
MDES Authorize Service Log Analyzer

This module analyzes MDES authorize service logs to identify user data
that can be used to match against our User DB (address, phone, name, email).
"""

import csv
import json
from typing import Dict, List, Set, Any
from collections import defaultdict
from dataclasses import dataclass, field


@dataclass
class UserIdentifiableData:
    """Stores identifiable user data from authorize service requests."""
    account_holder_name: str = None
    account_holder_address: str = None
    account_holder_email: str = None
    account_holder_mobile_phone: str = None
    mobile_number_suffix: str = None
    source_ip: str = None
    device_location: str = None
    consumer_identifier: str = None
    
    def has_identifiable_data(self) -> bool:
        """Check if any identifiable field is present."""
        return any([
            self.account_holder_name,
            self.account_holder_address,
            self.account_holder_email,
            self.account_holder_mobile_phone,
            self.mobile_number_suffix,
            self.source_ip,
            self.device_location,
            self.consumer_identifier
        ])
    
    def get_available_fields(self) -> List[str]:
        """Return list of available non-None fields."""
        fields = []
        if self.account_holder_name:
            fields.append('accountHolderName')
        if self.account_holder_address:
            fields.append('accountHolderAddress')
        if self.account_holder_email:
            fields.append('accountHolderEmailAddress')
        if self.account_holder_mobile_phone:
            fields.append('accountHolderMobilePhoneNumber')
        if self.mobile_number_suffix:
            fields.append('mobileNumberSuffix')
        if self.source_ip:
            fields.append('sourceIp')
        if self.device_location:
            fields.append('deviceLocation')
        if self.consumer_identifier:
            fields.append('consumerIdentifier')
        return fields


@dataclass
class AnalysisResult:
    """Stores analysis results for a specific wallet provider."""
    provider_name: str
    total_calls: int = 0
    calls_with_identifiable_data: int = 0
    calls_without_identifiable_data: int = 0
    field_availability: Dict[str, int] = field(default_factory=lambda: defaultdict(int))
    sample_records: List[Dict[str, Any]] = field(default_factory=list)


class AuthorizeServiceAnalyser:
    """Analyzes MDES authorize service logs."""
    
    def __init__(self, csv_file_path: str):
        """
        Initialize the analyzer.
        
        Args:
            csv_file_path: Path to the MDES log CSV file
        """
        self.csv_file_path = csv_file_path
        self.results: Dict[str, AnalysisResult] = {}
        
    def parse_request_json(self, request_json_str: str) -> Dict[str, Any]:
        """
        Parse the request JSON string.
        
        Args:
            request_json_str: JSON string from the CSV
            
        Returns:
            Parsed JSON as dictionary
        """
        try:
            # Replace single quotes with double quotes for valid JSON
            json_str = request_json_str.replace("'", '"').replace('None', 'null')
            return json.loads(json_str)
        except json.JSONDecodeError as e:
            print(f"Error parsing JSON: {e}")
            return {}
    
    def extract_identifiable_data(self, request_data: Dict[str, Any]) -> UserIdentifiableData:
        """
        Extract user identifiable data from request.
        
        Args:
            request_data: Parsed request JSON
            
        Returns:
            UserIdentifiableData object with extracted fields
        """
        user_data = UserIdentifiableData()
        
        # Extract mobile number suffix from top level
        user_data.mobile_number_suffix = request_data.get('mobileNumberSuffix')
        
        # Extract account holder data
        funding_info = request_data.get('fundingAccountInfo', {})
        funding_account_data = funding_info.get('fundingAccountData', {})
        account_holder_data = funding_account_data.get('accountHolderData', {})
        
        if account_holder_data:
            user_data.account_holder_name = account_holder_data.get('accountHolderName')
            user_data.account_holder_address = account_holder_data.get('accountHolderAddress')
            user_data.account_holder_email = account_holder_data.get('accountHolderEmailAddress')
            user_data.account_holder_mobile_phone = account_holder_data.get('accountHolderMobilePhoneNumber')
            user_data.source_ip = account_holder_data.get('sourceIp')
            user_data.device_location = account_holder_data.get('deviceLocation')
            user_data.consumer_identifier = account_holder_data.get('consumerIdentifier')
        
        return user_data
    
    def analyze_csv(self, max_samples: int = 5):
        """
        Analyze the CSV file and generate statistics.
        
        Args:
            max_samples: Maximum number of sample records to store per provider
        """
        print(f"Analyzing CSV file: {self.csv_file_path}")
        print("-" * 80)
        
        with open(self.csv_file_path, 'r', encoding='utf-8') as csvfile:
            reader = csv.DictReader(csvfile)
            
            for row in reader:
                request_json_str = row.get('request_json', '{}')
                request_data = self.parse_request_json(request_json_str)
                
                # Get consumer facing entity name (wallet provider)
                provider_name = request_data.get('consumerFacingEntityName', 'Unknown')
                
                # Initialize result for this provider if not exists
                if provider_name not in self.results:
                    self.results[provider_name] = AnalysisResult(provider_name=provider_name)
                
                result = self.results[provider_name]
                result.total_calls += 1
                
                # Extract identifiable data
                user_data = self.extract_identifiable_data(request_data)
                
                if user_data.has_identifiable_data():
                    result.calls_with_identifiable_data += 1
                    
                    # Track which fields are available
                    for field in user_data.get_available_fields():
                        result.field_availability[field] += 1
                    
                    # Store sample records
                    if len(result.sample_records) < max_samples:
                        result.sample_records.append({
                            'request_id': row.get('request_id'),
                            'fields': user_data.get_available_fields(),
                            'data': user_data
                        })
                else:
                    result.calls_without_identifiable_data += 1
    
    def print_report(self, provider_filter: str = None):
        """
        Print analysis report.
        
        Args:
            provider_filter: Optional provider name to filter results (e.g., 'Google')
        """
        print("\n" + "=" * 80)
        print("MDES AUTHORIZE SERVICE ANALYSIS REPORT")
        print("=" * 80)
        
        # Filter results if provider_filter is specified
        results_to_display = self.results
        if provider_filter:
            results_to_display = {
                k: v for k, v in self.results.items() 
                if provider_filter.lower() in k.lower()
            }
        
        for provider_name, result in sorted(results_to_display.items()):
            print(f"\n{'=' * 80}")
            print(f"Provider: {provider_name}")
            print(f"{'=' * 80}")
            print(f"Total Calls: {result.total_calls}")
            print(f"Calls WITH Identifiable Data: {result.calls_with_identifiable_data} "
                  f"({result.calls_with_identifiable_data / result.total_calls * 100:.2f}%)")
            print(f"Calls WITHOUT Identifiable Data: {result.calls_without_identifiable_data} "
                  f"({result.calls_without_identifiable_data / result.total_calls * 100:.2f}%)")
            
            if result.field_availability:
                print(f"\nField Availability Breakdown:")
                print(f"{'-' * 80}")
                for field, count in sorted(result.field_availability.items(), 
                                          key=lambda x: x[1], reverse=True):
                    percentage = count / result.total_calls * 100
                    print(f"  {field:40s}: {count:6d} ({percentage:6.2f}%)")
                
                print(f"\n{'=' * 80}")
                print(f"MATCHABLE FIELDS FOR USER DB:")
                print(f"{'=' * 80}")
                print(f"  ✓ accountHolderName          -> Can match with User.name")
                print(f"  ✓ accountHolderAddress       -> Can match with User.address")
                print(f"  ✓ accountHolderEmailAddress  -> Can match with User.email")
                print(f"  ✓ accountHolderMobilePhone   -> Can match with User.phone_number")
                print(f"  ✓ mobileNumberSuffix         -> Can match with User.phone_number (partial)")
                print(f"  ~ sourceIp                   -> Indirect match (location-based)")
                print(f"  ~ deviceLocation             -> Indirect match (location-based)")
                print(f"  ~ consumerIdentifier         -> May need additional mapping")
            
            if result.sample_records:
                print(f"\nSample Records with Identifiable Data:")
                print(f"{'-' * 80}")
                for i, sample in enumerate(result.sample_records, 1):
                    print(f"\n  Sample {i}:")
                    print(f"    Request ID: {sample['request_id']}")
                    print(f"    Available Fields: {', '.join(sample['fields'])}")
    
    def get_summary_stats(self) -> Dict[str, Any]:
        """
        Get summary statistics across all providers.
        
        Returns:
            Dictionary with summary statistics
        """
        total_calls = sum(r.total_calls for r in self.results.values())
        total_with_data = sum(r.calls_with_identifiable_data for r in self.results.values())
        
        return {
            'total_providers': len(self.results),
            'total_calls': total_calls,
            'total_with_identifiable_data': total_with_data,
            'total_without_identifiable_data': total_calls - total_with_data,
            'percentage_with_data': (total_with_data / total_calls * 100) if total_calls > 0 else 0
        }


def main():
    """Main execution function."""
    csv_file_path = '/Users/ynaik1/workspace/personal/programming-problems/python/resources/mdes-log-authorizeService.csv'
    
    # Create analyzer
    analyzer = AuthorizeServiceAnalyser(csv_file_path)
    
    # Analyze the CSV
    analyzer.analyze_csv(max_samples=10)
    
    # Print overall summary
    summary = analyzer.get_summary_stats()
    print("\n" + "=" * 80)
    print("OVERALL SUMMARY")
    print("=" * 80)
    print(f"Total Providers: {summary['total_providers']}")
    print(f"Total Calls: {summary['total_calls']}")
    print(f"Calls with Identifiable Data: {summary['total_with_identifiable_data']} "
          f"({summary['percentage_with_data']:.2f}%)")
    
    # Print report for GOOGLE specifically
    print("\n" + "=" * 80)
    print("GOOGLE-SPECIFIC ANALYSIS")
    print("=" * 80)
    analyzer.print_report(provider_filter='Google')
    
    # Print report for all providers
    print("\n" + "=" * 80)
    print("ALL PROVIDERS ANALYSIS")
    print("=" * 80)
    analyzer.print_report()


if __name__ == '__main__':
    main()
