from pprint import pprint

from bsedata.bse import BSE

bse = BSE(update_codes=True)

pprint(bse.getScripCodes())
