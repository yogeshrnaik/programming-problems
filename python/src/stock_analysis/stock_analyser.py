import csv
import json

from nsetools import nse, Nse
from bsedata.bse import BSE

PERCENTAGE_OF_INVESTED = "PERCENTAGE_OF_INVESTED"
PERCENTAGE_OF_CURR_VALUE = "PERCENTAGE_OF_CURR_VALUE"
ALL_CATEGORIES = "ALL_CATEGORIES"

SYMBOLS_TO_FILTER = [
    "MAFANG", "HDFCBANK",
    "SGBDE30III-GB", "SGBDEC30",
    "GOLDBEES", "JUNIORBEES", "LIQUIDBEES", "NIFTYBEES",
    "GOLD BEES", "JUNIOR BEES", "LIQUID BEES", "NIFTY BEES"
]

INSTRUMENT = "Instrument"
COMPANY_NAME = "CompanyName"
QUANTITY = "Qty."
AVG_COST = "Avg. cost"
CURR_VALUE = "Cur. val"
NET_CHANGE = "Net chg."
INVESTED = "INVESTED"
PROFIT_LOSS = "P&L"
LTP = "LTP"  # Last Traded Price
PERCENTAGE_OF_50L = "% of 50L"
PERCENTAGE_OF_75L = "% of 75L"

STOCK_CATEGORY = {
    "CORE": ["HDFCBANK", "HINDUNILVR", "ITC", "ITC1", "NESTLEIND", "RELIANCE", "SIEMENS", "TATAELXSI", "TCS", "SBIN",
             "INFY"],
    "STRONG-NON-CORE": ["EUREKAFORBE", "IRCON", "IRFC", "MAXHEALTH", "MAXVIL", "POONAWALLA"],
    "OTHER-NON-CORE": ["DUCOL-ST", "DUCOL-SM", "JYOTISTRUC", "JYOTISTRUC-BE", "JYOTISTRUC-BZ", "HCC", "HEMIPROP",
                       "IDEA", "ISMTLTD",
                       "MADHAVBAUG-SM", "MAFANG", "RENUKA", "SHREERAMA", "SHRGLTR", "TTML"],
    "PASSIVE": ["GOLDBEES", "JUNIORBEES", "LIQUIDBEES", "NIFTYBEES",
                "GOLD BEES", "JUNIOR BEES", "LIQUID BEES", "NIFTY BEES", "SGBDEC30"],
}

BSE_CODES = {
    "EUREKAFORBE": "543482",
    "GOLDBEES": "590095",
    "JUNIORBEES": "590104",
    "LIQUIDBEES": "590096",
    "NIFTYBEES": "590103",
    "INFY": "500209",
    "ITC": "500875",
    "SBIN": "500112",
    "HINDUNILVR": "500696",
    "RELIANCE": "500325",
    "TCS": "532540",
    "HCC": "500185",
    "HEMIPROP": "543242",
    "IDEA": "532822",
    "ISMTLTD": "532479",
    "JYOTISTRUC": "513250",
    "JYOTISTRUC-BE": "513250",
    "RENUKA": "532670",
    "SHREERAMA": "532310",
    "SHRGLTR": "512463",
    "TTML": "532371",
    "IRCON": "541956",
    "IRFC": "543257",
    "MAXHEALTH": "543220",
    "MAXVIL": "539940",
    "POONAWALLA": "524000",
    "NESTLEIND": "500790",
    "SIEMENS": "500550",
    "TATAELXSI": "500408"
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
    total_invested = 0
    total_curr_value = 0
    total_profit = 0
    for category, category_holdings in holdings_by_category.items():
        stat = category_stats.get(category, {})
        stat[CURR_VALUE] = category_stats.get(f"{category}-{CURR_VALUE}", 0) \
                           + sum(float(h[CURR_VALUE]) for h in category_holdings)
        total_curr_value += stat[CURR_VALUE]

        stat[INVESTED] = category_stats.get(f"{category}-{INVESTED}", 0) \
                         + sum(float(h[QUANTITY]) * float(h[AVG_COST]) for h in category_holdings)
        total_invested += stat[INVESTED]

        stat[PROFIT_LOSS] = category_stats.get(f"{category}-{PROFIT_LOSS}", 0) \
                            + sum(float(h[PROFIT_LOSS]) for h in category_holdings)
        total_profit += stat[PROFIT_LOSS]
        category_stats[category] = stat

    for category, stat in category_stats.items():
        stat[PERCENTAGE_OF_INVESTED] = 100 * stat.get(INVESTED) / total_invested
        stat[PERCENTAGE_OF_CURR_VALUE] = 100 * stat.get(CURR_VALUE) / total_curr_value

    category_stats[ALL_CATEGORIES] = {
        CURR_VALUE: total_curr_value,
        INVESTED: total_invested,
        PROFIT_LOSS: total_profit,
    }

    return category_stats


def write_analysed_stock_holdings(holdings_by_category, category_stats):
    global_total = {}
    output = open("stock_output.csv", "w")
    # write_line(output, "Category,Instrument,Company Name,Quantity,Avg Cost,Invested Amount,Curr Market Price,Curr Value,P&L,% Net Change,% of 50Lacs,% of 75Lacs")
    write_line(output,
               f"Category,Instrument,Company Name,Quantity,Avg Cost,Invested Amount,Curr Market Price,Curr Value,P&L,% Net Change,% of Invested Amt Rs. {category_stats[ALL_CATEGORIES][INVESTED]},% of Curr Value Rs. {category_stats[ALL_CATEGORIES][CURR_VALUE]}")

    for category, holdings in holdings_by_category.items():
        for h in holdings:
            invested = float(h[QUANTITY]) * float(h[AVG_COST])
            curr_value = float(h[QUANTITY]) * float(h[LTP])
            # percentage_of_50lacs = 100 * invested / 5000000
            percentage_of_invested = 100 * invested / category_stats[ALL_CATEGORIES][INVESTED]
            percentage_of_curr_value = 100 * curr_value / category_stats[ALL_CATEGORIES][CURR_VALUE]
            net_change = 100 * float(h[PROFIT_LOSS]) / invested
            company_name = h.get(COMPANY_NAME, h[INSTRUMENT])
            # line = f"{category},{h[INSTRUMENT]},{company_name},{h[QUANTITY]},{h[AVG_COST]},{invested},{h[LTP]}," \
            #        f"{h[CURR_VALUE]},{h[PROFIT_LOSS]},{net_change}%,{percentage_of_50lacs}%,{percentage_of_invested}%"
            line = f"{category},{h[INSTRUMENT]},{company_name},{h[QUANTITY]},{h[AVG_COST]},{invested},{h[LTP]}," \
                   f"{h[CURR_VALUE]},{h[PROFIT_LOSS]},{net_change}%,{percentage_of_invested}%,{percentage_of_curr_value}%"
            write_line(output, line)

        category_stat = print_sub_total(category, category_stats, output)
        calc_global_total(category_stat, global_total)

    print_global_total(global_total, output)
    output.close()


def print_global_total(global_total, output):
    # line = f"Grand-Total,,,,,{global_total[INVESTED]},,{global_total[CURR_VALUE]},{global_total[PROFIT_LOSS]},,{global_total[PERCENTAGE_OF_50L]},{global_total[PERCENTAGE_OF_75L]}"
    line = f"Grand-Total,,,,,{global_total[INVESTED]},,{global_total[CURR_VALUE]},{global_total[PROFIT_LOSS]},,{global_total[PERCENTAGE_OF_INVESTED]},{global_total[PERCENTAGE_OF_CURR_VALUE]}"
    write_line(output, line)


def write_line(output, line):
    print(line)
    output.write(line + "\n")


def calc_global_total(category_stat, global_total):
    global_total[INVESTED] = global_total.get(INVESTED, 0) + category_stat[INVESTED]
    global_total[CURR_VALUE] = global_total.get(CURR_VALUE, 0) + category_stat[CURR_VALUE]
    global_total[PROFIT_LOSS] = global_total.get(PROFIT_LOSS, 0) + category_stat[PROFIT_LOSS]
    # global_total[PERCENTAGE_OF_50L] = global_total.get(PERCENTAGE_OF_50L, 0) + category_stat[PERCENTAGE_OF_50L]
    global_total[PERCENTAGE_OF_INVESTED] = global_total.get(PERCENTAGE_OF_INVESTED, 0) + category_stat[PERCENTAGE_OF_INVESTED]
    global_total[PERCENTAGE_OF_CURR_VALUE] = global_total.get(PERCENTAGE_OF_CURR_VALUE, 0) + category_stat[PERCENTAGE_OF_CURR_VALUE]


def print_sub_total(category, category_stats, output):
    category_stat = category_stats[category]
    # line = f"Sub-Total,,,,,{category_stat[INVESTED]},,{category_stat[CURR_VALUE]},{category_stat[PROFIT_LOSS]},,{category_stat[PERCENTAGE_OF_50L]}%,{category_stat[PERCENTAGE_OF_75L]}%"
    line = f"Sub-Total,,,,,{category_stat[INVESTED]},,{category_stat[CURR_VALUE]},{category_stat[PROFIT_LOSS]},,{category_stat[PERCENTAGE_OF_INVESTED]}%,{category_stat[PERCENTAGE_OF_CURR_VALUE]}%"
    write_line(output, line)
    # write_line(output, ",,,,,,,,,")
    return category_stat


def analyse_stock_holdings(holdings):
    categorise_holdings(holdings)
    holdings.sort(key=lambda h: h["category"] + h[INSTRUMENT])
    holdings_by_category = group_by_category(holdings)
    # print(json.dumps(holdings_by_category, sort_keys=True, indent=2))
    category_stats = stats_by_category(holdings_by_category)
    print(json.dumps(category_stats, sort_keys=True, indent=2))
    return holdings_by_category, category_stats


def print_holdings(holdings):
    for h in holdings:
        print(h)


def add_hdfc_securities(holdings):
    holdings.append(update_by_market_price({INSTRUMENT: "INFY", AVG_COST: "551.86", QUANTITY: "50"}))
    holdings.append(update_by_market_price({INSTRUMENT: "SBIN", AVG_COST: "182.67", QUANTITY: "500"}))
    holdings.append(update_by_market_price({INSTRUMENT: "ITC", AVG_COST: "159.11", QUANTITY: "100", }, "ITC"))
    holdings.append(update_by_market_price({INSTRUMENT: "GOLD BEES", AVG_COST: "41.90", QUANTITY: "666"}, "GOLDBEES"))
    holdings.append(update_by_market_price(
        {INSTRUMENT: "JUNIOR BEES", AVG_COST: "452.92", QUANTITY: "6"}, "JUNIORBEES"
    ))
    holdings.append(update_by_market_price(
        {INSTRUMENT: "LIQUID BEES", AVG_COST: "1012.71", QUANTITY: "11"}, "LIQUIDBEES"
    ))
    holdings.append(update_by_market_price(
        {INSTRUMENT: "NIFTY BEES", AVG_COST: "172.89", QUANTITY: "229"}, "NIFTYBEES"
    ))


def update_by_market_price(holding, instrument=""):
    print("------------------------------------------------")
    instrument = instrument if instrument else holding[INSTRUMENT]
    holding = update_by_market_price_on_bse(holding, instrument)
    return holding

    # print(f"Getting market price of: {instrument} from NSE")
    # nse_quote = None
    # try:
    #     nse_quote = nse.get_quote(instrument)
    # except Exception as e:
    #     print(f"Error getting market price of: {instrument} from NSE, error: {e}")
    # if not nse_quote or not nse_quote["closePrice"]:
    #     print(f"Market price of: {instrument} not found on NSE")
    #     holding = update_by_market_price_on_bse(holding, instrument)
    #     return holding
    #
    # holding = update_by_market_price_on_nse(nse_quote, holding, instrument)
    # return holding


def update_by_market_price_on_nse(nse_quote, holding, instrument):
    try:
        print(f"Market price of: {instrument} on NSE: {nse_quote['closePrice']}")
        curr_price = float(nse_quote["closePrice"])
        if not nse_quote["closePrice"] and LTP in holding:
            print(
                f"Market price of: {instrument} on NSE: {nse_quote['closePrice']} is zero so using LTP: {float(holding[LTP])}")
            curr_price = float(holding[LTP])
        curr_val = float(holding[QUANTITY]) * curr_price
        invested = float(holding[QUANTITY]) * float(holding[AVG_COST])
        holding[LTP] = curr_price
        holding[CURR_VALUE] = curr_val
        holding[PROFIT_LOSS] = curr_val - invested
        holding[COMPANY_NAME] = nse_quote['companyName']
    except Exception as e:
        print(f"Error when updating market price from NSE for: {instrument}. Error:", e)
    return holding


def update_by_market_price_on_bse(holding, instrument):
    bse_instrument = BSE_CODES.get(instrument)
    if not bse_instrument:
        print(f"BSE code not found for: {instrument}")
        return holding
    print(f"Getting market price of: {instrument} on BSE with code: {bse_instrument}")
    bse_quote = bse.getQuote(bse_instrument)
    if not bse_quote:
        print(f"Market price of: {instrument} not found on BSE with code: {bse_instrument}")
        return holding

    print(f"BSE: {bse_quote}")
    if not instrument in bse_quote['securityID'] and not bse_quote['securityID'] in instrument:
        raise Exception(f"BSE securityID: {bse_quote['securityID']} not matching with {instrument}")
    curr_price = bse_quote['currentValue']
    print(f"Market price of: {instrument} on BSE: {curr_price}")
    curr_val = float(holding[QUANTITY]) * float(curr_price)
    invested = float(holding[QUANTITY]) * float(holding[AVG_COST])
    holding[LTP] = bse_quote["currentValue"]
    holding[CURR_VALUE] = curr_val
    holding[PROFIT_LOSS] = curr_val - invested
    holding[COMPANY_NAME] = bse_quote['companyName']

    return holding


def update_all_holdings_by_market_price(holdings):
    for h in holdings:
        update_by_market_price(h)


def filter(holdings):
    symbols_to_filter = SYMBOLS_TO_FILTER
    return [h for h in holdings if h[INSTRUMENT] not in symbols_to_filter]


def generate_stock_report():
    holdings = read_stock_holdings(
        "/Users/yogeshnaik/Yogesh/workspace/personal/programming-problems/python/src/stock_analysis/holdings.csv"
        # "/Users/yogeshrnaik/Yogesh/workspace/programming-problems/python/src/stock_analysis/holdings.csv"
    )
    add_hdfc_securities(holdings)
    holdings = filter(holdings)
    update_all_holdings_by_market_price(holdings)
    holdings_by_category, category_stats = analyse_stock_holdings(holdings)
    write_analysed_stock_holdings(holdings_by_category, category_stats)


def main():
    generate_stock_report()


main()
