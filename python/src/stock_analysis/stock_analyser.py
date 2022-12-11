import csv
import json

STOCK_CATEGORY = {
    "CORE": ["HINDUNILVR", "ITC", "RELIANCE", "TCS", "SBIN"],
    "STRONG-NON-CORE": ["EUREKAFORBE", "IRFC", "MAXHEALTH", "MAXVIL", "POONAWALLA"],
    "OTHER-NON-CORE": ["IDEA", "ISMTLTD", "MADHAVBAUG-SM", "MAFANG", "RENUKA", "SHREERAMA", "TTML"]
}


def read_stock_holdings(csv_filepath):
    holdings = []
    with open(csv_filepath) as f:
        reader = csv.DictReader(f, delimiter=",")
        for row in reader:
            holdings.append(row)

    return holdings


def categorise(instrument):
    for category, instruments in STOCK_CATEGORY.items():
        if instrument in instruments:
            return category
    return "UNKNOWN"


def categorise_holdings(holdings):
    for h in holdings:
        h["category"] = categorise(h["Instrument"])
    return holdings


def group_by_category(holdings):
    holdings_by_category = {}
    from itertools import groupby
    for k, v in groupby(holdings, key=lambda x: x['category']):
        holdings_by_category[k] = list(v)
    return holdings_by_category


def analyse_stock_holdings(holdings):
    categorise_holdings(holdings)
    holdings.sort(key=lambda h: h["category"])
    holdings_by_category = group_by_category(holdings)
    print(json.dumps(holdings_by_category, sort_keys=True, indent=2))


def print_holdings(holdings):
    for h in holdings:
        print(h)


def main():
    holdings = read_stock_holdings(
        "/Users/apple/yogesh/workspace/programming-problems/python/src/stock_analysis/holdings.csv")
    analyse_stock_holdings(holdings)


main()
