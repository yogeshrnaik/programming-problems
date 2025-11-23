import datetime
from dateutil import parser

# Sample strings
date_str_millis = '2023-10-27T10:30:05.123Z'
date_str_micros = '2023-10-27T10:30:05.123456Z'

# Parsing the milliseconds string using fromisoformat()
# Note: fromisoformat() requires a 'Z' to be replaced with '+00:00' for proper timezone handling.
dt_obj_millis_iso = datetime.datetime.fromisoformat(date_str_millis.replace('Z', '+00:00'))
print(f"Parsed with fromisoformat() (milliseconds): {dt_obj_millis_iso}")

# Parsing the microseconds string using fromisoformat()
dt_obj_micros_iso = datetime.datetime.fromisoformat(date_str_micros.replace('Z', '+00:00'))
print(f"Parsed with fromisoformat() (microseconds): {dt_obj_micros_iso}")

# A simpler, more robust alternative is the dateutil library.
# It can parse nearly any common format without explicit formatting.
print("\nUsing the dateutil.parser library:")
dt_obj_millis_dateutil = parser.isoparse(date_str_millis)
print(f"Parsed with dateutil (milliseconds): {dt_obj_millis_dateutil}")

dt_obj_micros_dateutil = parser.isoparse(date_str_micros)
print(f"Parsed with dateutil (microseconds): {dt_obj_micros_dateutil}")