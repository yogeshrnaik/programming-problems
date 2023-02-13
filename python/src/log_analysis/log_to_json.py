import json


# logs = [
# "Outgoing Response: 8471f665-fdd1-4e1c-8563-52e7c26d4fc7\nDuration: 556 ms\nHTTP/1.1 200 OK\nContent-Type: ...",
# "Incoming Request: 8471f665-fdd1-4e1c-8563-52e7c26d4fc7\nRemote: 10.45.12.19\n...",
# ]


def logs_to_json(logs):
    result = []
    for log in logs:
        out_log = {}
        splits = log.split("\n")
        curr = {}
        for s in splits:
            if "mti" in s:
                curr.update({"payload": json.loads(s)})
            else:
                header = s.split(": ")
                if header and len(header) >= 2:
                    curr[header[0]] = ''.join(header[1:]).strip()
                elif header:
                    header = s.split(" ")
                    if header and len(header) >= 2:
                        curr[header[0]] = ''.join(header[1:]).strip()
                    elif s:
                        curr[''.join(header[0:]).strip()] = ''

        result.append(curr)
    return result

# print(json.dumps(result, indent=2))
