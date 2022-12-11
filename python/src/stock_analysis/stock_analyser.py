import csv
import json

from nsetools import nse, Nse
from bsedata.bse import BSE

INSTRUMENT = "Instrument"
COMPANY_NAME = "CompanyName"
QUANTITY = "Qty."
AVG_COST = "Avg. cost"
CURR_VALUE = "Cur. val"
INVESTED = "INVESTED"
PROFIT_LOSS = "P&L"
LTP = "LTP"  # Last Traded Price

STOCK_CATEGORY = {
    "CORE": ["HDFCBANK", "HINDUNILVR", "ITC", "ITC1", "RELIANCE", "TCS", "SBIN", "INFY"],
    "STRONG-NON-CORE": ["EUREKAFORBE", "IRFC", "MAXHEALTH", "MAXVIL", "POONAWALLA"],
    "OTHER-NON-CORE": ["HCC", "HEMIPROP", "IDEA", "ISMTLTD", "MADHAVBAUG-SM", "MAFANG", "RENUKA", "SHREERAMA", "TTML"],
    "PASSIVE": ["GOLDBEES", "JUNIORBEES", "LIQUIDBEES", "NIFTYBEES", "GOLD BEES", "JUNIOR BEES", "LIQUID BEES",
                "NIFTY BEES"],
}

BSE_CODES = {
    "EUREKAFORBE": "543482",
    "GOLDBEES": "590095",
    "JUNIORBEES": "590104",
    "LIQUIDBEES": "590096",
    "NIFTYBEES": "590103",
}

nse = Nse()
bse = BSE(update_codes=True)


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
        h["category"] = categorise(h[INSTRUMENT])
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
        stat[CURR_VALUE] = category_stats.get(f"{category}-{CURR_VALUE}", 0) \
                           + sum(float(h[CURR_VALUE]) for h in category_holdings)
        stat[INVESTED] = category_stats.get(f"{category}-{INVESTED}", 0) \
                         + sum(float(h[QUANTITY]) * float(h[AVG_COST]) for h in category_holdings)
        stat[PROFIT_LOSS] = category_stats.get(f"{category}-P&L", 0) \
                            + sum(float(h[PROFIT_LOSS]) for h in category_holdings)
        category_stats[category] = stat

    for category, stat in category_stats.items():
        stat["% of 50L"] = 100 * stat.get(INVESTED) / 5000000
    return category_stats


def write_analysed_stock_holdings(holdings_by_category, category_stats):
    global_total = {}

    for category, holdings in holdings_by_category.items():
        for h in holdings:
            invested = float(h[QUANTITY]) * float(h[AVG_COST])
            percentage_of_50lacs = 100 * invested / 5000000
            company_name = h.get(COMPANY_NAME, h[INSTRUMENT])
            print(
                f"{category},{h[INSTRUMENT]},{company_name},{h[QUANTITY]},{h[AVG_COST]},{invested},{h[LTP]},{h[CURR_VALUE]},{h[PROFIT_LOSS]},{percentage_of_50lacs}%")

        # print sub total of category
        category_stat = category_stats[category]
        print(
            f"Sub-Total,,,,,{category_stat[INVESTED]},,{category_stat[CURR_VALUE]},{category_stat[PROFIT_LOSS]},{category_stat['% of 50L']}%")
        print(f",,,,,,,,")

        # calculate global total
        global_total[INVESTED] = global_total.get(INVESTED, 0) + category_stat[INVESTED]
        global_total[CURR_VALUE] = global_total.get(CURR_VALUE, 0) + category_stat[CURR_VALUE]
        global_total[PROFIT_LOSS] = global_total.get(PROFIT_LOSS, 0) + category_stat[PROFIT_LOSS]

    print(f"Grand-Total,,,,,{global_total[INVESTED]},,{global_total[CURR_VALUE]},{global_total[PROFIT_LOSS]},")


def analyse_stock_holdings(holdings):
    categorise_holdings(holdings)
    holdings.sort(key=lambda h: h["category"] + h[INSTRUMENT])
    holdings_by_category = group_by_category(holdings)
    print(json.dumps(holdings_by_category, sort_keys=True, indent=2))
    category_stats = stats_by_category(holdings_by_category)
    print(json.dumps(category_stats, sort_keys=True, indent=2))
    write_analysed_stock_holdings(holdings_by_category, category_stats)


def print_holdings(holdings):
    for h in holdings:
        print(h)


def add_hdfc_securities(holdings):
    holdings.append(update_by_market_price({INSTRUMENT: "INFY", AVG_COST: "551.86", QUANTITY: "50"}))
    holdings.append(update_by_market_price({INSTRUMENT: "SBIN", AVG_COST: "182.67", QUANTITY: "500"}))
    holdings.append(update_by_market_price({INSTRUMENT: "ITC1", AVG_COST: "159.11", QUANTITY: "100", }, "ITC"))
    holdings.append(update_by_market_price({INSTRUMENT: "GOLD BEES", AVG_COST: "41.90", QUANTITY: "666"}, "GOLDBEES"))
    holdings.append(
        update_by_market_price({INSTRUMENT: "JUNIOR BEES", AVG_COST: "452.92", QUANTITY: "6"}, "JUNIORBEES"))
    holdings.append(
        update_by_market_price({INSTRUMENT: "LIQUID BEES", AVG_COST: "1012.71", QUANTITY: "11"}, "LIQUIDBEES"))
    holdings.append(
        update_by_market_price({INSTRUMENT: "NIFTY BEES", AVG_COST: "172.89", QUANTITY: "229"}, "NIFTYBEES"))


def update_by_market_price(holding, instrument=""):
    print("------------------------------------------------")
    instrument = instrument if instrument else holding[INSTRUMENT]
    print(f"Getting market price of: {instrument}")
    details = nse.get_quote(instrument if instrument else holding[INSTRUMENT])
    if not details:
        print(f"Market price of: {instrument} not found on NSE")
        holding = update_by_market_price_on_bse(holding, instrument)
        return holding

    holding = update_by_market_price_on_nse(details, holding, instrument)
    return holding


def update_by_market_price_on_nse(details, holding, instrument):
    print(f"Market price of: {instrument} on NSE: {details['closePrice']}")
    curr_val = float(holding[QUANTITY]) * float(details["closePrice"])
    invested = float(holding[QUANTITY]) * float(holding[AVG_COST])
    holding[LTP] = details["closePrice"]
    holding[CURR_VALUE] = curr_val
    holding[PROFIT_LOSS] = curr_val - invested
    holding[COMPANY_NAME] = details['companyName']
    return holding


def update_by_market_price_on_bse(holding, instrument):
    bse_instrument = BSE_CODES.get(instrument)
    if not bse_instrument:
        print(f"BSE code not found for: {instrument}")
        return holding
    print(f"Getting market price of: {instrument} on BSE with code: {bse_instrument}")
    details = bse.getQuote(bse_instrument)
    if not details:
        print(f"Market price of: {instrument} not found on BSE with code: {bse_instrument}")
        return holding

    print(f"BSE: {details}")
    if details['securityID'] != instrument:
        raise Exception(f"BSE securityID: {details['securityID']} not matching with {instrument}")
    curr_price = details['currentValue']
    print(f"Market price of: {instrument} on BSE: {curr_price}")
    curr_val = float(holding[QUANTITY]) * float(curr_price)
    invested = float(holding[QUANTITY]) * float(holding[AVG_COST])
    holding[LTP] = details["currentValue"]
    holding[CURR_VALUE] = curr_val
    holding[PROFIT_LOSS] = curr_val - invested
    holding[COMPANY_NAME] = details['companyName']

    return holding


def update_all_holdings_by_market_price(holdings):
    for h in holdings:
        update_by_market_price(h)


def generate_stock_report():
    holdings = read_stock_holdings(
        "/Users/apple/yogesh/workspace/programming-problems/python/src/stock_analysis/holdings.csv"
    )
    add_hdfc_securities(holdings)
    update_all_holdings_by_market_price(holdings)
    analyse_stock_holdings(holdings)


def main():
    generate_stock_report()


main()
