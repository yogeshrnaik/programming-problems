"""
Google Recommendation Decision Analyzer

Analyzes recommendedDecision and recommendationReasons for Google records
in MDES authorize service logs.
"""

import csv
import json
from typing import Dict, List, Any
from collections import Counter


class GoogleRecommendationAnalyser:
    """Analyzes Google recommendation decisions."""
    
    def __init__(self, csv_file_path: str):
        """
        Initialize the analyzer.
        
        Args:
            csv_file_path: Path to the MDES log CSV file
        """
        self.csv_file_path = csv_file_path
        self.google_records = []
        self.decision_counter = Counter()
        self.reasons_counter = Counter()
        
    def parse_request_json(self, request_json_str: str) -> Dict[str, Any]:
        """
        Parse the request JSON string.
        
        Args:
            request_json_str: JSON string from the CSV
            
        Returns:
            Parsed JSON as dictionary
        """
        try:
            json_str = request_json_str.replace("'", '"').replace('None', 'null')
            return json.loads(json_str)
        except json.JSONDecodeError as e:
            print(f"Error parsing JSON: {e}")
            return {}
    
    def analyze_google_records(self):
        """Analyze all Google records for recommendation decisions."""
        print(f"Analyzing Google records from: {self.csv_file_path}")
        print("-" * 80)
        
        with open(self.csv_file_path, 'r', encoding='utf-8') as csvfile:
            reader = csv.DictReader(csvfile)
            
            for row in reader:
                request_json_str = row.get('request_json', '{}')
                request_data = self.parse_request_json(request_json_str)
                
                # Filter for Google records
                provider_name = request_data.get('consumerFacingEntityName', '')
                if 'google' in provider_name.lower():
                    # Extract wallet provider decisioning info
                    decisioning_info = request_data.get('walletProviderDecisioningInfo', {})
                    
                    recommended_decision = decisioning_info.get('recommendedDecision')
                    recommendation_reasons = decisioning_info.get('recommendationReasons')
                    
                    record = {
                        'request_id': row.get('request_id'),
                        'provider_name': provider_name,
                        'recommended_decision': recommended_decision,
                        'recommendation_reasons': recommendation_reasons,
                        'device_score': decisioning_info.get('deviceScore'),
                        'account_score': decisioning_info.get('accountScore'),
                        'phone_number_score': decisioning_info.get('phoneNumberScore'),
                        'account_life_time': decisioning_info.get('accountLifeTime'),
                        'recommendation_standard_version': decisioning_info.get('recommendationStandardVersion'),
                        'token_requestor_id': request_data.get('tokenRequestorId'),
                        'correlation_id': request_data.get('correlationId'),
                    }
                    
                    self.google_records.append(record)
                    
                    # Count decisions and reasons
                    if recommended_decision:
                        self.decision_counter[recommended_decision] += 1
                    else:
                        self.decision_counter['NULL/None'] += 1
                    
                    if recommendation_reasons:
                        # Handle list of reasons
                        if isinstance(recommendation_reasons, list):
                            for reason in recommendation_reasons:
                                self.reasons_counter[reason] += 1
                        else:
                            self.reasons_counter[str(recommendation_reasons)] += 1
                    else:
                        self.reasons_counter['NULL/None'] += 1
    
    def print_summary(self):
        """Print summary of Google recommendation decisions."""
        total_google_records = len(self.google_records)
        
        print("\n" + "=" * 80)
        print("GOOGLE RECOMMENDATION DECISION ANALYSIS")
        print("=" * 80)
        print(f"Total Google Records: {total_google_records}")
        
        print("\n" + "-" * 80)
        print("RECOMMENDED DECISION BREAKDOWN:")
        print("-" * 80)
        for decision, count in self.decision_counter.most_common():
            percentage = (count / total_google_records * 100) if total_google_records > 0 else 0
            print(f"  {decision:30s}: {count:6d} ({percentage:6.2f}%)")
        
        print("\n" + "-" * 80)
        print("RECOMMENDATION REASONS BREAKDOWN:")
        print("-" * 80)
        for reason, count in self.reasons_counter.most_common():
            percentage = (count / total_google_records * 100) if total_google_records > 0 else 0
            print(f"  {reason:30s}: {count:6d} ({percentage:6.2f}%)")
        
        print("\n" + "-" * 80)
        print("SAMPLE GOOGLE RECORDS (First 10):")
        print("-" * 80)
        for i, record in enumerate(self.google_records[:10], 1):
            print(f"\nRecord {i}:")
            print(f"  Request ID: {record['request_id']}")
            print(f"  Provider: {record['provider_name']}")
            print(f"  Recommended Decision: {record['recommended_decision']}")
            print(f"  Recommendation Reasons: {record['recommendation_reasons']}")
            print(f"  Device Score: {record['device_score']}")
            print(f"  Account Score: {record['account_score']}")
            print(f"  Phone Number Score: {record['phone_number_score']}")
            print(f"  Account Life Time: {record['account_life_time']}")
            print(f"  Token Requestor ID: {record['token_requestor_id']}")
            print(f"  Correlation ID: {record['correlation_id']}")
    
    def print_detailed_report(self):
        """Print detailed report of all Google records."""
        print("\n" + "=" * 80)
        print("DETAILED GOOGLE RECORDS REPORT")
        print("=" * 80)
        
        for i, record in enumerate(self.google_records, 1):
            print(f"\n{'-' * 80}")
            print(f"Record {i} of {len(self.google_records)}:")
            print(f"{'-' * 80}")
            for key, value in record.items():
                print(f"  {key:35s}: {value}")
    
    def export_to_csv(self, output_file: str):
        """
        Export Google records to CSV.
        
        Args:
            output_file: Path to output CSV file
        """
        if not self.google_records:
            print("No Google records to export.")
            return
        
        fieldnames = self.google_records[0].keys()
        
        with open(output_file, 'w', newline='', encoding='utf-8') as csvfile:
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
            writer.writeheader()
            writer.writerows(self.google_records)
        
        print(f"\n✓ Exported {len(self.google_records)} Google records to: {output_file}")


def main():
    """Main execution function."""
    csv_file_path = '/Users/ynaik1/workspace/personal/programming-problems/python/resources/mdes-log-authorizeService.csv'
    output_csv_path = '/Users/ynaik1/workspace/personal/programming-problems/python/resources/google-recommendations.csv'
    
    # Create analyzer
    analyzer = GoogleRecommendationAnalyser(csv_file_path)
    
    # Analyze Google records
    analyzer.analyze_google_records()
    
    # Print summary
    analyzer.print_summary()
    
    # Export to CSV
    analyzer.export_to_csv(output_csv_path)
    
    # Uncomment below to see all records in detail
    # analyzer.print_detailed_report()


if __name__ == '__main__':
    main()
