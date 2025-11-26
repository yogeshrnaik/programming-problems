"""
Comprehensive MDES Authorize Service Analyzer

Extracts all relevant fields from MDES authorize service logs including
decisioning info, user data, device info, and provider details.
"""

import ast
import csv
import json
from typing import Dict, Any, Optional


class ComprehensiveAnalyser:
    """Extracts comprehensive data from MDES authorize service logs."""
    
    def __init__(self, csv_file_path: str):
        """
        Initialize the analyzer.
        
        Args:
            csv_file_path: Path to the MDES log CSV file
        """
        self.csv_file_path = csv_file_path
        self.records = []
        
    def parse_request_json(self, request_json_str: str) -> Dict[str, Any]:
        """
        Parse the request JSON string.
        
        Args:
            request_json_str: JSON string from the CSV (can be Python dict literal or JSON)
            
        Returns:
            Parsed JSON as dictionary
        """
        try:
            # First try parsing as standard JSON
            return json.loads(request_json_str)
        except (json.JSONDecodeError, ValueError):
            try:
                # If that fails, try parsing as Python literal (handles single quotes, None, etc.)
                return ast.literal_eval(request_json_str)
            except (ValueError, SyntaxError):
                # If both fail, return empty dict
                return {}
    
    def safe_get(self, data: Any, *keys, default=None) -> Any:
        """
        Safely get nested dictionary values.
        
        Args:
            data: Dictionary or nested structure
            *keys: Keys to traverse
            default: Default value if key not found
            
        Returns:
            Value at the nested key path or default
        """
        for key in keys:
            if isinstance(data, dict):
                data = data.get(key, default)
            else:
                return default
        return data if data is not None else default
    
    def format_list(self, value: Any) -> str:
        """
        Format list values for CSV output.
        
        Args:
            value: Value to format
            
        Returns:
            Formatted string representation
        """
        if value is None:
            return ''
        if isinstance(value, list):
            return '|'.join(str(item) for item in value)
        return str(value)

    def format_dict(self, value: Any) -> str:
        """
        Format dict values for CSV output.

        Args:
            value: Value to format

        Returns:
            Formatted string representation
        """
        if value is None:
            return ''
        if isinstance(value, dict):
            return json.dumps(value)
        return str(value)

    def extract_record_data(self, row: Dict[str, str], row_number: int = 0) -> Dict[str, Any]:
        """
        Extract all relevant fields from a CSV row.

        Args:
            row: CSV row as dictionary
            row_number: Current row number for logging

        Returns:
            Dictionary with extracted fields
        """
        try:
            request_json_str = row.get('request_json', '{}')
            request_data = self.parse_request_json(request_json_str)
        except Exception as e:
            print(f"ERROR at row {row_number}: Failed to parse request_json - {e}")
            print(f"  request_id: {row.get('request_id', 'N/A')}")
            raise

        try:
            # Extract wallet provider decisioning info
            decisioning_info = request_data.get('walletProviderDecisioningInfo', {}) or {}

            # Extract funding account info
            funding_info = request_data.get('fundingAccountInfo', {}) or {}
            funding_account_data = funding_info.get('fundingAccountData', {}) or {}
            account_holder_data = funding_account_data.get('accountHolderData', {}) or {}

            # Extract device info
            device_info = request_data.get('deviceInfo', {})

            # Extract token data and card account data
            token_data = funding_account_data.get('tokenData', {}) or {}
            card_account_data = funding_account_data.get('cardAccountData', {}) or {}
            financial_account_data = funding_account_data.get('financialAccountData', {}) or {}
        except Exception as e:
            print(f"ERROR at row {row_number}: Failed to extract nested data - {e}")
            print(f"  request_id: {row.get('request_id', 'N/A')}")
            raise

        # Build the record
        try:
            record = {
            'request_id': row.get('request_id', ''),
            'api_name': row.get('api_name', ''),
            'consumerFacingEntityName': request_data.get('consumerFacingEntityName', ''),
            'tokenUniqueReference': funding_info.get('tokenUniqueReference', ''),
            'recommended_decision': decisioning_info.get('recommendedDecision', ''),
            'recommendation_reasons': self.format_list(decisioning_info.get('recommendationReasons')),
            'device_score': decisioning_info.get('deviceScore', ''),
            'account_score': decisioning_info.get('accountScore', ''),
            'phone_number_score': decisioning_info.get('phoneNumberScore', ''),
            'tokenAssuranceLevel': request_data.get('tokenAssuranceLevel', ''),
            'account_life_time': decisioning_info.get('accountLifeTime', ''),
            'accountHolderAddress': account_holder_data.get('accountHolderAddress', ''),
            'accountHolderEmailAddress': account_holder_data.get('accountHolderEmailAddress', ''),
            'accountHolderMobilePhoneNumber': account_holder_data.get('accountHolderMobilePhoneNumber', ''),
            'accountHolderName': account_holder_data.get('accountHolderName', ''),
            'sourceIp': account_holder_data.get('sourceIp', ''),
            'activeTokenCount': request_data.get('activeTokenCount', ''),
            'source': funding_account_data.get('source', ''),
            'dataValidUntilTimestamp': funding_account_data.get('dataValidUntilTimestamp', ''),
            'deviceName': device_info.get('deviceName', ''),
            'tokenType': request_data.get('tokenType', ''),
            # Additional top-level fields
            'decision': request_data.get('decision', ''),
            'services': self.format_list(request_data.get('services')),
            'walletId': request_data.get('walletId', ''),
            'correlationId': request_data.get('correlationId', ''),
            'decisionMadeBy': request_data.get('decisionMadeBy', ''),
            'secureElementId': request_data.get('secureElementId', ''),
            'accountPanSuffix': request_data.get('accountPanSuffix', ''),
            'consumerLanguage': request_data.get('consumerLanguage', ''),
            'tokenRequestorId': request_data.get('tokenRequestorId', ''),
            'numberOfActiveTokens': request_data.get('numberOfActiveTokens', ''),
            'paymentAppInstanceId': request_data.get('paymentAppInstanceId', ''),
            'productConfigurationId': request_data.get('productConfigurationId', ''),
            'serviceRequestDateTime': request_data.get('serviceRequestDateTime', ''),
            'tokenActivatedDateTime': request_data.get('tokenActivatedDateTime', ''),
            'termsAndConditionsAssetId': request_data.get('termsAndConditionsAssetId', ''),
            'numberOfActivationAttempts': request_data.get('numberOfActivationAttempts', ''),
            'termsAndConditionsAcceptedTimestamp': request_data.get('termsAndConditionsAcceptedTimestamp', ''),
            'accountIdHash': request_data.get('accountIdHash', ''),
            'mobileNumberSuffix': request_data.get('mobileNumberSuffix', ''),
            # fundingAccountInfo fields
            'panUniqueReference': funding_info.get('panUniqueReference', ''),
            'encryptedPayload': funding_info.get('encryptedPayload', ''),
            'paymentAccountReference': funding_account_data.get('paymentAccountReference', ''),
            # tokenData fields
            'token': token_data.get('token', ''),
            'token_expiryYear': token_data.get('expiryYear', ''),
            'token_expiryMonth': token_data.get('expiryMonth', ''),
            'token_sequenceNumber': token_data.get('sequenceNumber', ''),
            # cardAccountData fields
            'accountNumber': card_account_data.get('accountNumber', ''),
            'card_expiryYear': card_account_data.get('expiryYear', ''),
            'card_expiryMonth': card_account_data.get('expiryMonth', ''),
            'securityCode': card_account_data.get('securityCode', ''),
            # accountHolderData fields
            'deviceLocation': account_holder_data.get('deviceLocation', ''),
            'consumerIdentifier': account_holder_data.get('consumerIdentifier', ''),
            # financialAccountData (if exists)
            'financialAccountData': self.format_dict(financial_account_data) if financial_account_data else '',
            # deviceInfo flattened fields
            'osName': device_info.get('osName', ''),
            'osVersion': device_info.get('osVersion', ''),
            'formFactor': device_info.get('formFactor', ''),
            'paymentTypes': self.format_list(device_info.get('paymentTypes')),
            'serialNumber': device_info.get('serialNumber', ''),
            'isoDeviceType': device_info.get('isoDeviceType', ''),
            'storageTechnology': device_info.get('storageTechnology', ''),
            'cardCaptureTechnology': device_info.get('cardCaptureTechnology', ''),
            'imei': device_info.get('imei', ''),
            'msisdn': device_info.get('msisdn', ''),
            'deviceInfo': self.format_dict(device_info),
            }
        except Exception as e:
            print(f"ERROR at row {row_number}: Failed to build record dictionary - {e}")
            print(f"  request_id: {row.get('request_id', 'N/A')}")
            raise

        return record

    def analyze_csv(self):
        """Analyze the CSV file and extract all records."""
        print(f"Analyzing CSV file: {self.csv_file_path}")
        print("-" * 80)

        record_count = 0

        try:
            with open(self.csv_file_path, 'r', encoding='utf-8') as csvfile:
                reader = csv.DictReader(csvfile)

                for row_number, row in enumerate(reader, start=2):  # start=2 because row 1 is header
                    try:
                        record = self.extract_record_data(row, row_number)
                        self.records.append(record)
                        record_count += 1

                        if record_count % 100 == 0:
                            print(f"Processed {record_count} records (row {row_number})...")
                    except KeyboardInterrupt:
                        print(f"\n⚠️  Processing interrupted by user at row {row_number}")
                        print(f"   request_id: {row.get('request_id', 'N/A')}")
                        print(f"   Records processed so far: {record_count}")
                        raise
                    except Exception as e:
                        print(f"\n⚠️  ERROR at row {row_number}: {e}")
                        print(f"   request_id: {row.get('request_id', 'N/A')}")
                        print(f"   api_name: {row.get('api_name', 'N/A')}")
                        print(f"   Skipping this row and continuing...\n")
                        raise
        except KeyboardInterrupt:
            print(f"\n⚠️  Processing interrupted. Total records processed: {record_count}")
            raise
        except Exception as e:
            print(f"\n⚠️  FATAL ERROR: {e}")
            raise
        
        print(f"✓ Total records processed: {record_count}")
    
    def export_to_csv(self, output_file: str):
        """
        Export all records to CSV.
        
        Args:
            output_file: Path to output CSV file
        """
        if not self.records:
            print("No records to export.")
            return
        
        # Define field names in the requested order
        fieldnames = [
            'request_id',
            'api_name',
            'consumerFacingEntityName',
            'tokenUniqueReference',
            'recommended_decision',
            'recommendation_reasons',
            'device_score',
            'account_score',
            'phone_number_score',
            'tokenAssuranceLevel',
            'account_life_time',
            'accountHolderAddress',
            'accountHolderEmailAddress',
            'accountHolderMobilePhoneNumber',
            'accountHolderName',
            'sourceIp',
            'activeTokenCount',
            'source',
            'dataValidUntilTimestamp',
            'deviceName',
            'tokenType',
            # Additional top-level fields
            'decision',
            'services',
            'walletId',
            'correlationId',
            'decisionMadeBy',
            'secureElementId',
            'accountPanSuffix',
            'consumerLanguage',
            'tokenRequestorId',
            'numberOfActiveTokens',
            'paymentAppInstanceId',
            'productConfigurationId',
            'serviceRequestDateTime',
            'tokenActivatedDateTime',
            'termsAndConditionsAssetId',
            'numberOfActivationAttempts',
            'termsAndConditionsAcceptedTimestamp',
            'accountIdHash',
            'mobileNumberSuffix',
            # fundingAccountInfo fields
            'panUniqueReference',
            'encryptedPayload',
            'paymentAccountReference',
            # tokenData fields
            'token',
            'token_expiryYear',
            'token_expiryMonth',
            'token_sequenceNumber',
            # cardAccountData fields
            'accountNumber',
            'card_expiryYear',
            'card_expiryMonth',
            'securityCode',
            # accountHolderData fields
            'deviceLocation',
            'consumerIdentifier',
            # financialAccountData
            'financialAccountData',
            # deviceInfo flattened fields
            'osName',
            'osVersion',
            'formFactor',
            'paymentTypes',
            'serialNumber',
            'isoDeviceType',
            'storageTechnology',
            'cardCaptureTechnology',
            'imei',
            'msisdn',
            'deviceInfo',
        ]
        
        with open(output_file, 'w', newline='', encoding='utf-8') as csvfile:
            writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
            writer.writeheader()
            writer.writerows(self.records)
        
        print(f"✓ Exported {len(self.records)} records to: {output_file}")
    
    def print_summary(self):
        """Print summary statistics."""
        if not self.records:
            print("No records to summarize.")
            return
        
        print("\n" + "=" * 80)
        print("SUMMARY STATISTICS")
        print("=" * 80)
        
        # Count providers
        providers = {}
        for record in self.records:
            provider = record['consumerFacingEntityName']
            providers[provider] = providers.get(provider, 0) + 1
        
        print(f"\nTotal Records: {len(self.records)}")
        print(f"Unique Providers: {len(providers)}")
        
        # Count records with identifiable data
        with_name = sum(1 for r in self.records if r['accountHolderName'])
        with_address = sum(1 for r in self.records if r['accountHolderAddress'])
        with_email = sum(1 for r in self.records if r['accountHolderEmailAddress'])
        with_phone = sum(1 for r in self.records if r['accountHolderMobilePhoneNumber'])
        
        print(f"\nRecords with Identifiable Data:")
        print(f"  - accountHolderName: {with_name}")
        print(f"  - accountHolderAddress: {with_address}")
        print(f"  - accountHolderEmailAddress: {with_email}")
        print(f"  - accountHolderMobilePhoneNumber: {with_phone}")
        
        # Count decisions
        decisions = {}
        for record in self.records:
            decision = record['recommended_decision'] if record['recommended_decision'] else 'NULL/None'
            decisions[decision] = decisions.get(decision, 0) + 1
        
        print(f"\nRecommended Decisions:")
        for decision, count in sorted(decisions.items(), key=lambda x: x[1], reverse=True):
            print(f"  - {decision}: {count}")
        
        # Top providers
        print(f"\nTop 10 Providers:")
        for provider, count in sorted(providers.items(), key=lambda x: x[1], reverse=True)[:10]:
            print(f"  - {provider}: {count}")


def main():
    """Main execution function."""
    csv_file_path = '/Users/ynaik1/workspace/personal/programming-problems/python/resources/mdes-log.csv'
    output_csv_path = '/Users/ynaik1/workspace/personal/programming-problems/python/resources/mdes-comprehensive-analysis.csv'
    
    # Create analyzer
    analyzer = ComprehensiveAnalyser(csv_file_path)
    
    # Analyze the CSV
    analyzer.analyze_csv()
    
    # Print summary
    analyzer.print_summary()
    
    # Export to CSV
    analyzer.export_to_csv(output_csv_path)
    
    print("\n" + "=" * 80)
    print("ANALYSIS COMPLETE")
    print("=" * 80)


if __name__ == '__main__':
    main()
