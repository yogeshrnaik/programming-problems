# Comprehensive MDES Analysis - README

## Overview

This document describes the comprehensive CSV export containing all relevant fields from the MDES authorize service logs.

## Generated File

**File:** `mdes-comprehensive-analysis.csv`  
**Total Records:** 415  
**Unique Providers:** 47

## CSV Columns

The CSV contains the following 20 columns in this exact order:

| Column | Description | Source |
|--------|-------------|--------|
| `request_id` | Unique request identifier | Direct from CSV |
| `provider_name` | Wallet provider name | `consumerFacingEntityName` |
| `recommended_decision` | Wallet provider's decision | `walletProviderDecisioningInfo.recommendedDecision` |
| `recommendation_reasons` | Reasons for the decision (pipe-separated) | `walletProviderDecisioningInfo.recommendationReasons` |
| `device_score` | Device trust score (1-5) | `walletProviderDecisioningInfo.deviceScore` |
| `account_score` | Account trust score (1-5) | `walletProviderDecisioningInfo.accountScore` |
| `phone_number_score` | Phone number trust score | `walletProviderDecisioningInfo.phoneNumberScore` |
| `account_life_time` | Account lifetime | `walletProviderDecisioningInfo.accountLifeTime` |
| `accountHolderAddress` | User's address | `fundingAccountInfo.fundingAccountData.accountHolderData.accountHolderAddress` |
| `accountHolderEmailAddress` | User's email | `fundingAccountInfo.fundingAccountData.accountHolderData.accountHolderEmailAddress` |
| `accountHolderMobilePhoneNumber` | User's mobile phone | `fundingAccountInfo.fundingAccountData.accountHolderData.accountHolderMobilePhoneNumber` |
| `accountHolderName` | User's name | `fundingAccountInfo.fundingAccountData.accountHolderData.accountHolderName` |
| `sourceIp` | Source IP address | `fundingAccountInfo.fundingAccountData.accountHolderData.sourceIp` |
| `activeTokenCount` | Number of active tokens | `activeTokenCount` |
| `source` | Card source | `fundingAccountInfo.fundingAccountData.source` |
| `deviceName` | Device name | `deviceInfo.deviceName` |
| `tokenType` | Token type (CLOUD, EMBEDDED_SE, etc.) | `tokenType` |
| `deviceInfo` | Complete device info (JSON) | `deviceInfo` (full object) |
| `consumerFacingEntityName` | Provider name (duplicate) | `consumerFacingEntityName` |
| `tokenUniqueReference` | Unique token reference identifier | `fundingAccountInfo.tokenUniqueReference` |

## Data Statistics

### User Identifiable Data

| Field | Records with Data | Percentage |
|-------|-------------------|------------|
| `accountHolderName` | 208 | 50.12% |
| `accountHolderAddress` | 185 | 44.58% |
| `accountHolderEmailAddress` | 0 | 0.00% |
| `accountHolderMobilePhoneNumber` | 160 | 38.55% |

**Key Finding:** No records contain email addresses.

### Token Reference Data

| Field | Records with Data | Percentage |
|-------|-------------------|------------|
| `tokenUniqueReference` | 408 | 98.31% |

**Key Finding:** Almost all records (98.31%) have a tokenUniqueReference, making it an excellent field for tracking and linking tokens.

### Recommended Decisions

| Decision | Count | Percentage |
|----------|-------|------------|
| REQUIRE_ADDITIONAL_AUTHENTICATION | 204 | 49.16% |
| APPROVED | 89 | 21.45% |
| DECLINED | 63 | 15.18% |
| NULL/None | 59 | 14.22% |

### Top 10 Providers

| Provider | Count | Percentage |
|----------|-------|------------|
| APPLE PAY | 171 | 41.20% |
| GOOGLE | 127 | 30.60% |
| Amazon | 14 | 3.37% |
| Google | 12 | 2.89% |
| (empty) | 7 | 1.69% |
| LinkedIn | 5 | 1.20% |
| SH_Stripe | 4 | 0.96% |
| AT&T | 4 | 0.96% |
| 2104 Amazon US Retail | 4 | 0.96% |
| 2111 US Amazon Marketplace | 4 | 0.96% |

## Special Formatting

### Lists (recommendation_reasons)
Multiple reasons are separated by pipe (`|`) character.

**Example:**
```
ACCOUNT_CARD_TOO_NEW|ACCOUNT_RECENTLY_CHANGED|LOW_DEVICE_SCORE|HIGH_RISK
```

### JSON Objects (deviceInfo)
Device information is stored as JSON string.

**Example:**
```json
{"imei": "000000000000028", "msisdn": "6031", "osName": "ANDROID", "osVersion": "16", "deviceName": "|samsung|samsung|SM-S938U", "formFactor": "PHONE", "paymentTypes": ["NFC", "DSRP"], "serialNumber": "PL", "isoDeviceType": "21", "storageTechnology": "DEVICE_MEMORY", "cardCaptureTechnology": null}
```

### Nested Dictionaries (accountHolderAddress, accountHolderMobilePhoneNumber)
Some fields contain Python dictionary strings.

**Example Address:**
```python
{'city': None, 'line1': '20350 Stevens Creek Blvd', 'line2': None, 'country': None, 'postalCode': '95014-2234', 'countrySubdivision': None}
```

**Example Phone:**
```python
{'phoneNumber': '14126260879', 'countryDialInCode': None}
```

## Sample Records

### Sample 1: GOOGLE with User Data
```csv
2d146f19-4635-45eb-b1e4-d3fbf7355ade,GOOGLE,DECLINED,ACCOUNT_CARD_TOO_NEW|ACCOUNT_RECENTLY_CHANGED|LOW_DEVICE_SCORE|HIGH_RISK,1,3,,,"{'city': 'Trichy', 'line1': 'Kambarasampettai', 'line2': 'No.11', 'country': 'IND', 'postalCode': '620101', 'countrySubdivision': 'TamilNadu'}",,,mani kandan,27.107.82.150,0,ACCOUNT_ADDED_MANUALLY,|samsung|samsung|SM-S938B,CLOUD,...
```

### Sample 2: APPLE PAY with Full User Data
```csv
3a81b302-3279-4d3b-8ea4-ed1118c84071,APPLE PAY,REQUIRE_ADDITIONAL_AUTHENTICATION,ACCOUNT_CARD_TOO_NEW,3,5,,,"{'city': None, 'line1': '20350 Stevens Creek Blvd', 'line2': None, 'country': None, 'postalCode': '95014-2234', 'countrySubdivision': None}",,"{'phoneNumber': '14126260879', 'countryDialInCode': None}",Akash Jatangi,73.162.134.8,0,ACCOUNT_ADDED_VIA_APPLICATION,Akash's iPhone,EMBEDDED_SE,...
```

## Provider-Specific Observations

### GOOGLE (127 records)
- **User Data Availability:** Some records have name and address
- **Email:** Never provided
- **Phone:** Rarely provided
- **Decision Distribution:**
  - DECLINED: ~50%
  - APPROVED: ~30%
  - REQUIRE_ADDITIONAL_AUTHENTICATION: ~20%

### APPLE PAY (171 records)
- **User Data Availability:** High - most records have name, address, and phone
- **Email:** Never provided
- **Decision Distribution:**
  - REQUIRE_ADDITIONAL_AUTHENTICATION: ~60%
  - APPROVED: ~30%
  - DECLINED: ~10%

### Other Providers (117 records)
- **User Data Availability:** Very low
- Most have NULL/empty decisioning info

## Usage Examples

### Python - Read and Filter
```python
import pandas as pd

# Read the CSV
df = pd.read_csv('mdes-comprehensive-analysis.csv')

# Filter for Google records with user data
google_with_data = df[
    (df['provider_name'].str.contains('GOOGLE', case=False, na=False)) &
    (df['accountHolderName'].notna())
]

# Filter for declined requests
declined = df[df['recommended_decision'] == 'DECLINED']

# Get records with phone numbers
with_phone = df[df['accountHolderMobilePhoneNumber'].notna()]
```

### SQL - Import and Query
```sql
-- Import CSV into table
CREATE TABLE mdes_analysis (
    request_id VARCHAR(255),
    provider_name VARCHAR(255),
    recommended_decision VARCHAR(100),
    recommendation_reasons TEXT,
    device_score INT,
    account_score INT,
    -- ... other columns
);

-- Query for user-identifiable records
SELECT 
    provider_name,
    COUNT(*) as total,
    SUM(CASE WHEN accountHolderName IS NOT NULL THEN 1 ELSE 0 END) as with_name,
    SUM(CASE WHEN accountHolderAddress IS NOT NULL THEN 1 ELSE 0 END) as with_address
FROM mdes_analysis
GROUP BY provider_name;
```

## How to Regenerate

Run the comprehensive analyzer:

```bash
cd /Users/ynaik1/workspace/personal/programming-problems/python
python src/mdes/comprehensive_analyser.py
```

The script will:
1. Read `resources/mdes-log-authorizeService.csv`
2. Extract all 19 fields from each record
3. Generate `resources/mdes-comprehensive-analysis.csv`
4. Print summary statistics

## Notes

1. **Empty Values:** Empty strings represent NULL/None values from the original data
2. **Encoding:** UTF-8 encoding is used throughout
3. **JSON Parsing:** Some records may have parsing errors (silently handled)
4. **Case Sensitivity:** Provider names may vary in case (e.g., "GOOGLE" vs "Google")
5. **Data Quality:** Not all fields are populated for all providers

## Related Files

- **Source CSV:** `resources/mdes-log-authorizeService.csv`
- **Output CSV:** `resources/mdes-comprehensive-analysis.csv`
- **Analyzer Script:** `src/mdes/comprehensive_analyser.py`
- **Google-Specific Analysis:** `resources/google-recommendations.csv`
- **Analysis Summary:** `resources/mdes-analysis-summary.md`
