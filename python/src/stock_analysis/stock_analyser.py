import csv
import json

STOCK_CATEGORY = {
    "CORE": ["HDFCBANK", "HINDUNILVR", "ITC", "RELIANCE", "TCS", "SBIN"],
    "STRONG-NON-CORE": ["EUREKAFORBE", "IRFC", "MAXHEALTH", "MAXVIL", "POONAWALLA"],
    "OTHER-NON-CORE": ["HCC", "HEMIPROP", "IDEA", "ISMTLTD", "MADHAVBAUG-SM", "MAFANG", "RENUKA", "SHREERAMA", "TTML"]
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


def stats_by_category(holdings_by_category):
    category_stats = {}
    for category, category_holdings in holdings_by_category.items():
        stat = category_stats.get(category, {})
        stat[f"Cur. val"] = category_stats.get(f"{category}-Cur. val", 0) \
                            + sum(float(h["Cur. val"]) for h in category_holdings)
        stat[f"INVESTED"] = category_stats.get(f"{category}-INVESTED", 0) \
                            + sum(float(h["Qty."]) * float(h["Avg. cost"]) for h in category_holdings)
        category_stats[category] = stat

    for category, stat in category_stats.items():
        stat["% of 50L"] = 100 * stat.get("INVESTED") / 5000000
    return category_stats


def write_analysed_stock_holdings(holdings_by_category, category_stats):
    for category, holdings in holdings_by_category.items():
        for h in holdings:
            invested = float(h['Qty.']) * float(h['Avg. cost'])
            percentage_of_50lacs = 100 * invested / 5000000
            print(
                f"{category},{h['Instrument']},{h['Qty.']},{h['Avg. cost']},{invested},{h['Cur. val']},{h['P&L']},{percentage_of_50lacs}")

        # print sub total of category
        print(f"{category},,,,{category_stats[category]['INVESTED']},,,{category_stats[category]['% of 50L']}")


def analyse_stock_holdings(holdings):
    categorise_holdings(holdings)
    holdings.sort(key=lambda h: h["category"])
    holdings_by_category = group_by_category(holdings)
    print(json.dumps(holdings_by_category, sort_keys=True, indent=2))
    category_stats = stats_by_category(holdings_by_category)
    print(json.dumps(category_stats, sort_keys=True, indent=2))
    write_analysed_stock_holdings(holdings_by_category, category_stats)


def print_holdings(holdings):
    for h in holdings:
        print(h)


def main():
    holdings = read_stock_holdings(
        "/Users/apple/yogesh/workspace/programming-problems/python/src/stock_analysis/holdings.csv")
    analyse_stock_holdings(holdings)


main()
