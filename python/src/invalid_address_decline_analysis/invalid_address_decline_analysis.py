import pandas as pd

BASE_PATH = "/Users/ynaik1/workspace/personal/programming-problems/python/resources/invalid-address-declines"


def _normalize_zip5(series: pd.Series) -> pd.Series:
    return (
        series
        .astype("string")
        .str.extract(r"(\d{5})", expand=False)
        .astype("string")
    )

def analyze_declines():
    # 1. Load the datasets
    declines_df = pd.read_csv(f'{BASE_PATH}/invalid-address-declines.csv')
    user_addresses_df = pd.read_csv(f'{BASE_PATH}/user-addresses.csv')
    business_addresses_df = pd.read_csv(f'{BASE_PATH}/business-addresses.csv')

    # 2. Construct the complete address string
    # We use .fillna('') to ensure concatenation doesn't result in 'NaN' strings
    cols_to_concat = ['address_line_1', 'address_line_2', 'city', 'zip', 'state', 'country']

    # Fill missing values with empty string for clean concatenation
    temp_addr = user_addresses_df[cols_to_concat].fillna('').astype(str)

    user_addresses_df['complete_address'] = temp_addr.agg(' '.join, axis=1).str.strip().str.replace(r'\s+', ' ', regex=True)

    # 3. Pivot the addresses
    # This transforms the data so each 'type' (Home, Work, etc.) becomes a column
    # We keep 'user_id' as the index to join back to declines
    address_pivot = user_addresses_df.pivot_table(
        index='user_id',
        columns='type',
        values='complete_address',
        aggfunc='first' # If a user has multiple of the same type, take the first
    ).reset_index()

    zip_pivot = user_addresses_df.pivot_table(
        index='user_id',
        columns='type',
        values='zip',
        aggfunc='first'
    ).reset_index()

    # Rename columns to be descriptive (e.g., 'home' becomes 'address_type_home')
    address_pivot = address_pivot.rename(columns={
        col: f"address_type_{col.lower()}"
        for col in address_pivot.columns if col != 'user_id'
    })

    zip_pivot = zip_pivot.rename(columns={
        col: f"zip_type_{col.lower()}"
        for col in zip_pivot.columns if col != 'user_id'
    })

    # 4. Merge with the declines data
    # We perform a 'left' join to keep all decline records even if address is missing
    final_df = pd.merge(
        declines_df,
        address_pivot,
        on='user_id',
        how='left'
    )

    final_df = pd.merge(
        final_df,
        zip_pivot,
        on='user_id',
        how='left'
    )

    # 4b. Add business address (joined by account_id)
    business_cols_to_concat = ['line_1', 'line_2', 'city', 'zip', 'state', 'country']
    temp_business_addr = business_addresses_df[business_cols_to_concat].fillna('').astype(str)
    business_addresses_df['business_address'] = (
        temp_business_addr.agg(' '.join, axis=1)
        .str.strip()
        .str.replace(r'\s+', ' ', regex=True)
    )
    business_address_by_account = business_addresses_df[['account_id', 'business_address']].drop_duplicates(subset=['account_id'])
    final_df = pd.merge(
        final_df,
        business_address_by_account,
        on='account_id',
        how='left'
    )

    business_zip_by_account = business_addresses_df[['account_id', 'zip']].drop_duplicates(subset=['account_id'])
    final_df = pd.merge(
        final_df,
        business_zip_by_account.rename(columns={"zip": "business_zip"}),
        on='account_id',
        how='left'
    )

    zip_received_5 = _normalize_zip5(final_df["zip_received"])
    residence_zip_5 = _normalize_zip5(final_df.get("zip_type_residence"))
    shipping_zip_5 = _normalize_zip5(final_df.get("zip_type_shipping"))
    business_zip_5 = _normalize_zip5(final_df.get("business_zip"))

    final_df["is_residence_zip_match"] = (
        zip_received_5.notna() & residence_zip_5.notna() & (zip_received_5 == residence_zip_5)
    )
    final_df["is_shipping_zip_match"] = (
        zip_received_5.notna() & shipping_zip_5.notna() & (zip_received_5 == shipping_zip_5)
    )
    final_df["is_business_zip_match"] = (
        zip_received_5.notna() & business_zip_5.notna() & (zip_received_5 == business_zip_5)
    )

    # 5. Save the aggregated result
    output_columns = [
        "_time",
        "mti",
        "last4",
        "correlation_id",
        "user_id",
        "account_id",
        "spend_account_id",
        "token_provider",
        "pan_entry_mode",
        "pin_entry_mode",
        "cardholder_present_code",
        "card_present_code",
        "fraud_score",
        "mcc",
        "card_acceptor_id",
        "address_received",
        "zip_received",
        "address_type_residence",
        "address_type_shipping",
        "business_address",
        "is_residence_zip_match",
        "is_shipping_zip_match",
        "is_business_zip_match",
        "txn_amount",
        "txn_currency",
    ]
    final_df = final_df.reindex(columns=output_columns)
    final_df.to_csv(f'{BASE_PATH}/aggregate.csv', index=False)
    print(f"Analysis complete. Processed {len(final_df)} rows into aggregate.csv")

if __name__ == "__main__":
    analyze_declines()
