import csv
import json

from nsetools import nse, Nse
from bsedata.bse import BSE

STOCK_CATEGORY = {
    "CORE": ["HDFCBANK", "HINDUNILVR", "ITC", "ITC1", "RELIANCE", "TCS", "SBIN", "INFY"],
    "STRONG-NON-CORE": ["EUREKAFORBE", "IRFC", "MAXHEALTH", "MAXVIL", "POONAWALLA"],
    "OTHER-NON-CORE": ["HCC", "HEMIPROP", "IDEA", "ISMTLTD", "MADHAVBAUG-SM", "MAFANG", "RENUKA", "SHREERAMA", "TTML"],
    "PASSIVE": ["GOLDBEES", "JUNIORBEES", "LIQUIDBEES", "NIFTYBEES", "GOLD BEES", "JUNIOR BEES", "LIQUID BEES", "NIFTY BEES"],
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
        stat["Cur. val"] = category_stats.get(f"{category}-Cur. val", 0) \
                           + sum(float(h["Cur. val"]) for h in category_holdings)
        stat["INVESTED"] = category_stats.get(f"{category}-INVESTED", 0) \
                           + sum(float(h["Qty."]) * float(h["Avg. cost"]) for h in category_holdings)
        stat["P&L"] = category_stats.get(f"{category}-P&L", 0) \
                      + sum(float(h["P&L"]) for h in category_holdings)
        category_stats[category] = stat

    for category, stat in category_stats.items():
        stat["% of 50L"] = 100 * stat.get("INVESTED") / 5000000
    return category_stats


def write_analysed_stock_holdings(holdings_by_category, category_stats):
    global_total = {}

    for category, holdings in holdings_by_category.items():
        for h in holdings:
            invested = float(h['Qty.']) * float(h['Avg. cost'])
            percentage_of_50lacs = 100 * invested / 5000000
            print(f"{category},{h['Instrument']},{h['CompanyName']},{h['Qty.']},{h['Avg. cost']},{invested},{h['LTP']},{h['Cur. val']},{h['P&L']},{percentage_of_50lacs}%")

        # print sub total of category
        category_stat = category_stats[category]
        print(
            f"Sub-Total,,,,,{category_stat['INVESTED']},,{category_stat['Cur. val']},{category_stat['P&L']},{category_stat['% of 50L']}%")
        print(f",,,,,,,,")

        # calculate global total
        global_total["INVESTED"] = global_total.get("INVESTED", 0) + category_stat['INVESTED']
        global_total["Cur. val"] = global_total.get("Cur. val", 0) + category_stat['Cur. val']
        global_total["P&L"] = global_total.get("P&L", 0) + category_stat['P&L']

    print(f"Grand-Total,,,,,{global_total['INVESTED']},,{global_total['Cur. val']},{global_total['P&L']},")


def analyse_stock_holdings(holdings):
    categorise_holdings(holdings)
    holdings.sort(key=lambda h: h["category"] + h['Instrument'])
    holdings_by_category = group_by_category(holdings)
    print(json.dumps(holdings_by_category, sort_keys=True, indent=2))
    category_stats = stats_by_category(holdings_by_category)
    print(json.dumps(category_stats, sort_keys=True, indent=2))
    write_analysed_stock_holdings(holdings_by_category, category_stats)


def print_holdings(holdings):
    for h in holdings:
        print(h)


def add_hdfc_securities(holdings):
    holdings.append(update_by_curr_market_price({"Instrument": "INFY", "Avg. cost": "551.86", "Qty.": "50"}))
    holdings.append(update_by_curr_market_price({"Instrument": "SBIN", "Avg. cost": "182.67", "Qty.": "500"}))
    holdings.append(update_by_curr_market_price({"Instrument": "ITC1", "Avg. cost": "159.11", "Qty.": "100", }, "ITC"))
    # ["GOLDBEES", "JUNIORBEES", "LIQUIDBEES", "NIFTYBEES"],
    holdings.append(update_by_curr_market_price({"Instrument": "GOLD BEES", "Avg. cost": "41.90", "Qty.": "666"}, "GOLDBEES"))
    holdings.append(update_by_curr_market_price({"Instrument": "JUNIOR BEES", "Avg. cost": "452.92", "Qty.": "6"}, "JUNIORBEES"))
    holdings.append(update_by_curr_market_price({"Instrument": "LIQUID BEES", "Avg. cost": "1012.71", "Qty.": "11"}, "LIQUIDBEES"))
    holdings.append(update_by_curr_market_price({"Instrument": "NIFTY BEES", "Avg. cost": "172.89", "Qty.": "229"}, "NIFTYBEES"))


def update_by_curr_market_price(holding, instrument=""):
    instrument = instrument if instrument else holding["Instrument"]
    print(f"Getting market price of: {instrument}")
    details = nse.get_quote(instrument if instrument else holding["Instrument"])
    if not details:
        print(f"Market price of: {instrument} not found on NSE")
        return update_by_bse_curr_market_price(holding, instrument)
        return holding

    return update_by_nse_curr_market_price(details, holding, instrument)


def update_by_nse_curr_market_price(details, holding, instrument):
    print(f"Market price of: {instrument} on NSE: {details['closePrice']}")
    curr_val = float(holding["Qty."]) * float(details["closePrice"])
    invested = float(holding["Qty."]) * float(holding["Avg. cost"])
    holding["LTP"] = details["closePrice"]
    holding["Cur. val"] = curr_val
    holding["P&L"] = curr_val - invested
    holding["CompanyName"] = details['companyName']
    return holding


def update_by_bse_curr_market_price(holding, instrument):
    bse_instrument = BSE_CODES.get(instrument)
    if not bse_instrument:
        print(f"BSE code not found for: {instrument}")
        return holding
    print(f"Getting market price of: {instrument} on BSE with code: {bse_instrument}")
    details = bse.getQuote(bse_instrument)
    if not details:
        print(f"Market price of: {instrument} not found on BSE with code: {bse_instrument}")
        return holding

    print(f"bse: {details}")
    if details['securityID'] != instrument:
        raise Exception(f"BSE securityID: {details['securityID']} not matching with {instrument}")
    curr_price = details['currentValue']
    print(f"Market price of: {instrument} on BSE: {curr_price}")
    curr_val = float(holding["Qty."]) * float(curr_price)
    invested = float(holding["Qty."]) * float(holding["Avg. cost"])
    holding["LTP"] = details["currentValue"]
    holding["Cur. val"] = curr_val
    holding["P&L"] = curr_val - invested
    holding["CompanyName"] = details['companyName']

    return holding


def main():
    holdings = read_stock_holdings(
        "/Users/apple/yogesh/workspace/programming-problems/python/src/stock_analysis/holdings.csv")
    add_hdfc_securities(holdings)
    for h in holdings:
        h["CompanyName"] = h['Instrument']
        update_by_curr_market_price(h)

    analyse_stock_holdings(holdings)


main()
