# MDES Authorize Service Analysis Summary

## Executive Summary

This analysis examines MDES authorize service logs to identify user-identifiable data that can be used to match users in our User DB (which stores: name, email, address, phone number).

## Key Findings

### Google-Specific Analysis

**Total Google Calls: 1,192**

- **Calls WITH Identifiable Data: 0 (0.00%)**
- **Calls WITHOUT Identifiable Data: 1,192 (100.00%)**

### Critical Finding

**❌ Google does NOT provide any user-identifiable information in their authorize service requests.**

None of the following fields are populated in Google requests:
- `accountHolderName` - 0 occurrences
- `accountHolderAddress` - 0 occurrences  
- `accountHolderEmailAddress` - 0 occurrences
- `accountHolderMobilePhoneNumber` - 0 occurrences
- `mobileNumberSuffix` - 0 occurrences
- `sourceIp` - 0 occurrences
- `deviceLocation` - 0 occurrences
- `consumerIdentifier` - 0 occurrences

## Overall Statistics

- **Total Providers Analyzed: 39**
- **Total Calls: 1,362**
- **Calls with Identifiable Data: 3 (0.22%)**
- **Calls without Identifiable Data: 1,359 (99.78%)**

## Providers with Identifiable Data

Only 2 providers out of 39 provide any user-identifiable information:

### 1. Walmart Inc
- Total Calls: 2
- Calls with Data: 2 (100%)
- Fields Available: `accountHolderAddress` (2 occurrences)

### 2. Starbucks
- Total Calls: 3
- Calls with Data: 1 (33.33%)
- Fields Available: `accountHolderAddress` (1 occurrence)

## Matchable Fields for User DB

The following fields from MDES logs can potentially match with our User DB:

| MDES Field | User DB Field | Match Type |
|------------|---------------|------------|
| `accountHolderName` | `User.name` | Direct |
| `accountHolderAddress` | `User.address` | Direct |
| `accountHolderEmailAddress` | `User.email` | Direct |
| `accountHolderMobilePhoneNumber` | `User.phone_number` | Direct |
| `mobileNumberSuffix` | `User.phone_number` | Partial |
| `sourceIp` | N/A | Indirect (location-based) |
| `deviceLocation` | N/A | Indirect (location-based) |
| `consumerIdentifier` | N/A | Requires mapping |

## Conclusion

**For Google specifically:**
- ❌ **Cannot identify users in our system** based on authorize service logs
- Google does not send any PII (Personally Identifiable Information) in their authorize service requests
- All 1,192 Google requests contain NULL/None values for all user-identifiable fields

**Recommendation:**
To identify users for Google Pay transactions, you would need to:
1. Use alternative data sources (e.g., transaction history, card details)
2. Implement user linking during card provisioning flow
3. Use tokenization mapping if available
4. Consider using `tokenUniqueReference` or `panUniqueReference` to link with existing tokens in your system

## Technical Details

The analysis was performed on:
- **File:** `mdes-log-authorizeService.csv`
- **Total Records:** 1,362
- **Analysis Script:** `src/mdes/authorize_service_analyser.py`
- **Date:** November 2025

## How to Run the Analysis

```bash
cd /Users/ynaik1/workspace/personal/programming-problems/python
python src/mdes/authorize_service_analyser.py
```

The script will:
1. Parse the CSV file
2. Extract user-identifiable fields from each request
3. Generate statistics per wallet provider
4. Provide detailed breakdown for Google and all other providers
