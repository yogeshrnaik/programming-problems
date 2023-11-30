import requests
import threading


def make_api_request(api_url, headers):
    tenant_id = headers['X-Tenant-Id']
    print(f"Calling to {api_url} for {tenant_id}")
    response = requests.post(api_url, headers=headers)
    if response.status_code == 200:
        print(f"Request to {api_url} for {tenant_id} was successful. Response JSON: {response.json()}")
    else:
        print(f"Request to {api_url} for {tenant_id} failed with status code {response.status_code}. Response content: {response.text}")


api_url = 'http://localhost:8000/api/pan-numbers/generate/10'
cloud_edge_headers = {
    'X-Tenant-Id': 'c327f995-d63d-4c1f-9453-97a0fbe92295',
    'Content-Type': 'application/json'
}
mngs_headers = {
    'X-Tenant-Id': 'dcebdbea-7198-408c-9e5d-9d41943c8d9f',
    'Content-Type': 'application/json'
}

# Create a list of threads
threads = []

# Number of parallel requests
num_requests = 1

# Create and start threads
for i in range(num_requests):
    headers = cloud_edge_headers if i % 2 == 0 else mngs_headers
    thread = threading.Thread(target=make_api_request, args=(api_url, headers))
    threads.append(thread)
    thread.start()

# Wait for all threads to complete
for thread in threads:
    thread.join()
