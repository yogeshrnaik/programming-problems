import csv
import json
import concurrent.futures
import time
from functools import wraps

from nsetools import nse, Nse
from bsedata.bse import BSE

PERCENTAGE_OF_INVESTED = "PERCENTAGE_OF_INVESTED"
PERCENTAGE_OF_CURR_VALUE = "PERCENTAGE_OF_CURR_VALUE"
PERCENTAGE_CHANGE = "PERCENTAGE_CHANGE"
ALL_CATEGORIES = "ALL_CATEGORIES"

SYMBOLS_TO_FILTER = [
    "SBICARD", "HDFCBANK",
    "MAFANG", "ITCHOTELS",
    "SGBDE30III-GB", "SGBDEC30", "SGBJUN31I-GB", "SGBJUNE31", "SGBDEC31", "SGBDE31III-GB", "SGBDE31III",
    "SGBDE30III", "SGBJUN31I",
    "GOLDBEES", "JUNIORBEES", "LIQUIDBEES", "NIFTYBEES",
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
    "1-CORE": ["HDFCBANK", "HINDUNILVR", "ITC", "ITC1", "NESTLEIND", "RELIANCE", "SIEMENS", "TCS", "SBIN", "INFY"],
    "2-STRONG-NON-CORE": ["BSE", "EUREKAFORBE", "JSWINFRA", "IRCON", "IRFC", "JIOFIN", "MAXHEALTH", "MAXVIL", "POONAWALLA", "TATAELXSI", "SBICARD"],
    "3-OTHER-NON-CORE": [
        "ACCENTMIC-SM", "BEWLTD-SM", "BEWLTD-ST", "CHOICEIN", "DUCOL-ST", "DUCOL-SM", "EFCIL", "JYOTISTRUC", "JYOTISTRUC-BE", "JYOTISTRUC-BZ", "HCC",
        "HEMIPROP", "IDEA", "ISMTLTD", "LSIL", "LSIL-BE", "LLOYDSTEEL", "LLOYDSENGG", "LLOYDSENGG-BE", "LLOYDSENT", "MADHAVBAUG-SM", "MADHAVBAUG-ST", "MAFANG", "PVP", "PYRAMID", "PYRAMID-BE", "RENUKA",
        "SANGHIIND", "SANGHIIND-BE", "SHREERAMA", "SHREERAMA-BE", "SHRGLTR", "SWSOLAR", "SWSOLAR-BE", "TANAA", "TTML", "TCC"
    ],
    "4-PASSIVE": ["GOLDBEES", "JUNIORBEES", "LIQUIDBEES", "NIFTYBEES", "SGBDEC30", "SGBDE30III-GB", "SGBJUNE31"],
}

BSE_CODES = {
    "HDFCBANK": "500180",
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
    "SHREERAMA-BE": "532310",
    "SHREERAMA": "532310",
    "SHRGLTR": "512463",
    "LLOYDSENT": "512463",
    "TTML": "532371",
    "IRCON": "541956",
    "IRFC": "543257",
    "MAXHEALTH": "543220",
    "MAXVIL": "539940",
    "POONAWALLA": "524000",
    "NESTLEIND": "500790",
    "SIEMENS": "500550",
    "TATAELXSI": "500408",
    "SWSOLAR": "542760",
    "SWSOLAR-BE": "542760",
    "SANGHIIND": "526521",
    "SANGHIIND-BE": "526521",
    "LLOYDSTEEL": "539992",
    "LLOYDSENGG": "539992",
    "LLOYDSENGG-BE": "539992",
    "LSIL-BE": "539992",
    "JIOFIN": "543940",
    "CHOICEIN": "531358",
    "PYRAMID": "543969",
    "PYRAMID-BE": "543969",
    "TANAA": "522229",
    "JSWINFRA": "543994",
    "EFCIL": "512008",
    "TCC": "512038",
    "PVP": "517556",
}

UPDATED_QUANTITIES = {
    "BEWLTD-SM": 375 * 3,
    "BEWLTD-ST": 375 * 3,
    "BSE": 300*3,
    "EFCIL": 2000,
    "HCC": 17500,
    "HEMIPROP": 4000,
    "HINDUNILVR": 75,
    "INFY": 50,
    "IRFC": 7500,
    "JYOTISTRUC": 17500,
    "LLOYDSENGG": 15000,
    "NESTLEIND": 200,
    "PVP": 20000,
    "SHREERAMA-BE": 17500,
    "SHREERAMA": 17500,
    "SIEMENS": 75,
    "SWSOLAR": 1800,
    "TANAA": 1800,
    "TATAELXSI": 50,
    "TCC": 750,
    "TCS": 50
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
        stat[PERCENTAGE_OF_INVESTED] = round(100 * stat.get(INVESTED) / total_invested, 2)
        stat[PERCENTAGE_OF_CURR_VALUE] = round(100 * stat.get(CURR_VALUE) / total_curr_value, 2)
        stat[PERCENTAGE_CHANGE] = round(100 * stat.get(PROFIT_LOSS) / stat.get(INVESTED), 2)

    category_stats[ALL_CATEGORIES] = {
        CURR_VALUE: total_curr_value,
        INVESTED: total_invested,
        PROFIT_LOSS: total_profit,
    }

    return category_stats


def write_analysed_stock_holdings(holdings_by_category, category_stats):
    global_total = {}
    output = open("stock_output.csv", "w")
    write_line(output,
               f"Category,Instrument,Company Name,Quantity,Avg Cost,Invested,Market Price,Curr Value,P&L,% Net Change,% Invested Amt,% Curr Value")

    for category, holdings in holdings_by_category.items():
        for h in holdings:
            invested = float(h[QUANTITY]) * float(h[AVG_COST])
            curr_value = float(h[QUANTITY]) * float(h[LTP])
            h[PROFIT_LOSS] = curr_value - invested
            percentage_of_invested = round(100 * invested / category_stats[ALL_CATEGORIES][INVESTED], 2)
            percentage_of_curr_value = round(100 * curr_value / category_stats[ALL_CATEGORIES][CURR_VALUE], 2)
            print(f"{category} - {h[INSTRUMENT]} -{h[QUANTITY]} - {h[AVG_COST]} - {invested} - {h[LTP]}")
            net_change = round(100 * float(h[PROFIT_LOSS]) / invested, 2)
            company_name = h.get(COMPANY_NAME, h[INSTRUMENT])
            line = f'{category[2:]},{h[INSTRUMENT]},{company_name},{h[QUANTITY]},{h[AVG_COST]},"{format_indian_number(invested)}",{h[LTP]},"{format_indian_number(curr_value)}","{format_indian_number(h[PROFIT_LOSS])}",{net_change}%,{percentage_of_invested}%,{percentage_of_curr_value}%'
            write_line(output, line)

        category_stat = print_sub_total(category, category_stats, output)
        calc_global_total(category_stat, global_total)

    print_global_total(global_total, output)
    output.close()


def print_global_total(global_total, output):
    line = f'Grand-Total,,,,,"{format_indian_number(global_total[INVESTED])}",,"{format_indian_number(global_total[CURR_VALUE])}","{format_indian_number(global_total[PROFIT_LOSS])}",{global_total[PERCENTAGE_CHANGE]},{global_total[PERCENTAGE_OF_INVESTED]},{global_total[PERCENTAGE_OF_CURR_VALUE]}'
    write_line(output, line)


def format_indian_number(number, decimal_places=0):
    """Format a number in Indian style with commas (e.g., 1,00,000 for 1 lakh)"""
    number = float(number)
    is_negative = number < 0
    number = abs(number)
    str_number = "{}".format(number)
    parts = str_number.split('.')
    integer_part = parts[0]
    decimal_part = parts[1]
    
    # Handle numbers less than 1000
    if len(integer_part) <= 3:
        result = integer_part
    else:
        # Split the integer part into groups of 2 from right, except the last 3 digits
        s = integer_part[-3:]
        integer_part = integer_part[:-3]
        
        while integer_part:
            s = integer_part[-2:] + ',' + s if len(integer_part) >= 2 else integer_part + ',' + s
            integer_part = integer_part[:-2]
        
        result = s

    # Add decimal part back
    if decimal_places == 2:
        result = result + '.' + decimal_part

    # Add negative sign if needed
    if is_negative:
        result = '-' + result
    
    return result

def write_line(output, line):
    print(line)
    output.write(line + "\n")


def calc_global_total(category_stat, global_total):
    global_total[INVESTED] = global_total.get(INVESTED, 0) + category_stat[INVESTED]
    global_total[CURR_VALUE] = global_total.get(CURR_VALUE, 0) + category_stat[CURR_VALUE]
    global_total[PROFIT_LOSS] = global_total.get(PROFIT_LOSS, 0) + category_stat[PROFIT_LOSS]
    global_total[PERCENTAGE_OF_INVESTED] = round(global_total.get(PERCENTAGE_OF_INVESTED, 0) + category_stat[PERCENTAGE_OF_INVESTED], 0)
    global_total[PERCENTAGE_OF_CURR_VALUE] = round(global_total.get(PERCENTAGE_OF_CURR_VALUE, 0) + category_stat[PERCENTAGE_OF_CURR_VALUE], 0)
    global_total[PERCENTAGE_CHANGE] = "{}".format(round(100 * global_total.get(PROFIT_LOSS, 0) / global_total.get(INVESTED, 0), 0))


def print_sub_total(category, category_stats, output):
    category_stat = category_stats[category]
    line = f'Sub-Total,,,,,"{format_indian_number(category_stat[INVESTED])}",,"{format_indian_number(category_stat[CURR_VALUE])}","{format_indian_number(category_stat[PROFIT_LOSS])}",{category_stat[PERCENTAGE_CHANGE]},{category_stat[PERCENTAGE_OF_INVESTED]}%,{category_stat[PERCENTAGE_OF_CURR_VALUE]}%'
    write_line(output, line)
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
    # update_by_market_price(avg_out(holdings, {INSTRUMENT: "INFY", AVG_COST: "551.86", QUANTITY: "50"}))
    # update_by_market_price(avg_out(holdings, {INSTRUMENT: "ITC", AVG_COST: "159.11", QUANTITY: "100"}))
    update_by_market_price(avg_out(holdings, {INSTRUMENT: "SBIN", AVG_COST: "182.67", QUANTITY: "500"}))


def avg_out(holdings, newHolding):
    for h in holdings:
        if h[INSTRUMENT] == newHolding[INSTRUMENT]:
            total_quantity = float(h[QUANTITY]) + float(newHolding[QUANTITY])
            avg_cost = (float(h[AVG_COST]) * float(h[QUANTITY]) + (
                    float(newHolding[AVG_COST]) * float(newHolding[QUANTITY]))) / total_quantity
            h[QUANTITY] = total_quantity
            h[AVG_COST] = format(avg_cost, ".2f")
            return h
    # if it is really a new holding then just append to holdings
    holdings.append(newHolding)
    return newHolding


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


def retry_on_none_error(max_retries=3, delay=1):
    def decorator(func):
        @wraps(func)
        def wrapper(*args, **kwargs):
            for attempt in range(max_retries):
                try:
                    result = func(*args, **kwargs)
                    if result is not None:
                        return result
                    raise AttributeError("Result is None")
                except AttributeError as e:
                    if attempt == max_retries - 1:
                        raise
                    print(f"Attempt {attempt + 1} failed, retrying in {delay} seconds...")
                    time.sleep(delay)
            return None
        return wrapper
    return decorator

@retry_on_none_error(max_retries=3, delay=2)
def get_bse_quote(bse_instrument):
    return bse.getQuote(bse_instrument)

def update_by_market_price_on_bse(holding, instrument, retry=1):
    bse_instrument = BSE_CODES.get(instrument)
    if not bse_instrument:
        print(f"BSE code not found for: {instrument}")
        return holding
    print(f"Getting market price of: {instrument} on BSE with code: {bse_instrument}")
    try:
        bse_quote = get_bse_quote(bse_instrument)
        if not bse_quote:
            print(f"Market price of: {instrument} not found on BSE with code: {bse_instrument}")
            return holding
    except AttributeError:
        print(f"Failed to fetch quote for {instrument} after retries")
        return holding

    print(f"BSE: {bse_quote}")
    if not bse_quote.get('securityID') or (not instrument in bse_quote.get('securityID') and not bse_quote.get('securityID') in instrument):
        if retry < 100:
            return update_by_market_price_on_bse(holding, instrument, retry=retry+1)
        else:
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
    with concurrent.futures.ThreadPoolExecutor() as executor:
        # Submit all tasks and get their futures
        futures = [executor.submit(update_by_market_price, h) for h in holdings]
        # Wait for all tasks to complete and gather results
        updated_holdings = [f.result() for f in concurrent.futures.as_completed(futures)]
    return updated_holdings


def filter(holdings):
    symbols_to_filter = SYMBOLS_TO_FILTER
    return [h for h in holdings if h[INSTRUMENT] not in symbols_to_filter]

def update_quantities(holdings):
    for h in holdings:
        if UPDATED_QUANTITIES.get(h[INSTRUMENT]):
            h[QUANTITY] = UPDATED_QUANTITIES.get(h[INSTRUMENT]) or h[QUANTITY]
        else:
            print(f"Quantity not updated for: {h[INSTRUMENT]}")
    return holdings

def generate_stock_report():
    holdings = read_stock_holdings(
        "/Users/ynaik1/workspace/personal/programming-problems/python/src/stock_analysis/holdings.csv"
    )
    # add_hdfc_securities(holdings)
    holdings = update_quantities(holdings)
    holdings = filter(holdings)
    update_all_holdings_by_market_price(holdings)
    holdings_by_category, category_stats = analyse_stock_holdings(holdings)
    write_analysed_stock_holdings(holdings_by_category, category_stats)


def main():
    generate_stock_report()


main()
