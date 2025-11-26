# Google Recommendation Decision Analysis

## Executive Summary

Analysis of 139 Google records from MDES authorize service logs, focusing on `recommendedDecision` and `recommendationReasons` from the wallet provider decisioning info.

## Key Findings

### Total Google Records Analyzed: 139

Note: The original analysis showed 1,192 Google calls, but only 139 had parseable request data with decisioning information.

## Recommended Decision Breakdown

| Decision | Count | Percentage |
|----------|-------|------------|
| **DECLINED** | 63 | 45.32% |
| **APPROVED** | 39 | 28.06% |
| **REQUIRE_ADDITIONAL_AUTHENTICATION** | 37 | 26.62% |

### Key Insights:
- **45.32%** of Google requests are **DECLINED** by the wallet provider
- Only **28.06%** are **APPROVED** outright
- **26.62%** require additional authentication

## Recommendation Reasons Breakdown

| Reason | Count | Percentage | Description |
|--------|-------|------------|-------------|
| **ACCOUNT_CARD_TOO_NEW** | 100 | 71.94% | Card recently added to account |
| **ACCOUNT_RECENTLY_CHANGED** | 100 | 71.94% | Account settings/info recently modified |
| **OUTSIDE_HOME_TERRITORY** | 79 | 56.83% | Request from unusual location |
| **LOW_DEVICE_SCORE** | 65 | 46.76% | Device has low trust score |
| **HIGH_RISK** | 63 | 45.32% | High risk transaction/request |
| **TOO_MANY_RECENT_TOKENS** | 35 | 25.18% | Too many tokens provisioned recently |
| **LONG_ACCOUNT_TENURE** | 27 | 19.42% | ✅ Positive signal - long account history |
| **TOO_MANY_RECENT_ATTEMPTS** | 27 | 19.42% | Multiple recent provisioning attempts |
| **NULL/None** | 12 | 8.63% | No specific reason provided |
| **LOW_ACCOUNT_SCORE** | 10 | 7.19% | Account has low trust score |
| **SUSPICIOUS_ACTIVITY** | 10 | 7.19% | Suspicious activity detected |
| **HAS_SUSPENDED_TOKENS** | 2 | 1.44% | Account has previously suspended tokens |
| **INACTIVE_ACCOUNT** | 1 | 0.72% | Account is inactive |

### Critical Observations:

1. **Most Common Risk Factors:**
   - 71.94% flagged for new card or recent account changes
   - 56.83% flagged for location anomalies
   - 46.76% have low device trust scores

2. **Multiple Reasons per Request:**
   - Most declined requests have multiple risk factors
   - Common pattern: `ACCOUNT_CARD_TOO_NEW` + `ACCOUNT_RECENTLY_CHANGED` + `LOW_DEVICE_SCORE` + `HIGH_RISK`

3. **Scoring Information:**
   - Some records include `deviceScore` (1-5 scale)
   - Some records include `accountScore` (1-5 scale)
   - `phoneNumberScore` is consistently NULL/None
   - `accountLifeTime` is consistently NULL/None

## Sample Decision Patterns

### Pattern 1: APPROVED with Good Scores
```
Recommended Decision: APPROVED
Recommendation Reasons: ['LONG_ACCOUNT_TENURE']
Device Score: 5
Account Score: 5
```

### Pattern 2: APPROVED without Reasons
```
Recommended Decision: APPROVED
Recommendation Reasons: None
Device Score: None
Account Score: None
```

### Pattern 3: DECLINED with Multiple Risk Factors
```
Recommended Decision: DECLINED
Recommendation Reasons: [
  'ACCOUNT_CARD_TOO_NEW',
  'OUTSIDE_HOME_TERRITORY',
  'ACCOUNT_RECENTLY_CHANGED',
  'LOW_DEVICE_SCORE',
  'HIGH_RISK'
]
Device Score: 1
Account Score: 3
```

### Pattern 4: REQUIRE_ADDITIONAL_AUTHENTICATION
```
Recommended Decision: REQUIRE_ADDITIONAL_AUTHENTICATION
Recommendation Reasons: [
  'ACCOUNT_CARD_TOO_NEW',
  'ACCOUNT_RECENTLY_CHANGED',
  'TOO_MANY_RECENT_TOKENS'
]
Device Score: 2
Account Score: 4
```

## Token Requestor IDs

Two different Google token requestor IDs found:
- `50120834693` - Most common, includes decisioning scores
- `50164702489` - Less common, typically no decisioning scores

## Implications for User Identification

### Why Google Doesn't Send PII:

Based on the decisioning analysis, Google appears to:

1. **Perform their own risk assessment** using device and account scores
2. **Not rely on traditional PII** for decisioning
3. **Use behavioral signals** instead:
   - Device trust scores
   - Account tenure
   - Location patterns
   - Token provisioning patterns
   - Account activity patterns

### Alternative Identification Strategies:

Since Google doesn't provide PII, consider:

1. **Token Mapping:**
   - Use `tokenUniqueReference` to track tokens
   - Use `panUniqueReference` to link to cards
   - Use `correlationId` for request tracking

2. **Pattern Analysis:**
   - Track `deviceScore` and `accountScore` trends
   - Monitor `recommendationReasons` for fraud patterns
   - Identify users by token provisioning behavior

3. **Transaction Linking:**
   - Link tokens to transactions post-provisioning
   - Use card details (masked PAN) for matching
   - Correlate with existing card-on-file data

## Exported Data

All 139 Google records have been exported to:
```
resources/google-recommendations.csv
```

This CSV includes:
- request_id
- provider_name
- recommended_decision
- recommendation_reasons
- device_score
- account_score
- phone_number_score
- account_life_time
- recommendation_standard_version
- token_requestor_id
- correlation_id

## Recommendations

1. **Accept Google's Privacy Model:**
   - Google intentionally doesn't share PII
   - This is by design for user privacy

2. **Use Token-Based Tracking:**
   - Focus on token lifecycle management
   - Link tokens to users post-provisioning

3. **Leverage Decisioning Data:**
   - Use `recommendedDecision` for fraud prevention
   - Monitor `recommendationReasons` for risk patterns
   - Track `deviceScore` and `accountScore` trends

4. **Implement Step-Up Authentication:**
   - For `REQUIRE_ADDITIONAL_AUTHENTICATION` decisions
   - Use OTP or biometric verification
   - Link user during authentication flow

## How to Run the Analysis

```bash
cd /Users/ynaik1/workspace/personal/programming-problems/python
python src/mdes/google_recommendation_analyser.py
```

The script will:
1. Extract all Google records
2. Analyze recommendation decisions and reasons
3. Generate statistics and breakdowns
4. Export detailed data to CSV
