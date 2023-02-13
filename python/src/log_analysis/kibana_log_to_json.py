import json
from datetime import datetime

from log_analysis.log_to_json import logs_to_json


def read_kibana_json_log(file_name):
    with open(file_name) as f:
        data = json.load(f)
    return to_log_messages(data['hits']['hits'])


def to_log_messages(hits):
    logs = []
    for hit in hits:
        log = hit['_source']['log']
        logs.append(log)
    return logs


def write_log_result(log_result):
    filename = f"log_result-{datetime.now().strftime('%Y%m%d_%H%M%S')}.json"
    with open(filename, "w") as outfile:
        json.dump(log_result, outfile, indent=2)


def group_incoming_outgoing(incoming_outgoing_logs):
    log_grouped = {}
    for log in incoming_outgoing_logs:
        correlation_id = log.get("Outgoing Response") or log.get("Incoming Request")
        correlation_id_group = log_grouped.get(correlation_id, {})
        group_name = "incoming" if log.get("Incoming Request") else "outgoing"
        correlation_id_group.update({group_name: log})
        log_grouped[correlation_id] = correlation_id_group
    return log_grouped


def main():
    logs = read_kibana_json_log('kibana_response.json')
    incoming_outgoing_logs = logs_to_json(logs)
    log_grouped = group_incoming_outgoing(incoming_outgoing_logs)
    write_log_result(log_grouped)


main()
