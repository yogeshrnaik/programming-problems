import requests
import csv
import os

# Set your organization name and GitHub token
ORG_NAME = "trustfactors"
GITHUB_TOKEN = os.getenv("GITHUB_TOKEN")

# GitHub API URL to fetch repositories
API_URL = f"https://api.github.com/orgs/{ORG_NAME}/repos"

# Headers with authentication
HEADERS = {"Authorization": f"Bearer {GITHUB_TOKEN}"}

def fetch_repos():
    repos = []
    page = 1
    while True:
        response = requests.get(API_URL, headers=HEADERS, params={"per_page": 100, "page": page})
        if response.status_code != 200:
            print("Error fetching repositories:", response.json())
            break

        data = response.json()
        if not data:
            break  # No more repositories

        repos.extend(data)
        page += 1

    return repos

def generate_csv(repos):
    with open("github_repos.csv", "w", newline="", encoding="utf-8") as file:
        writer = csv.writer(file)
        writer.writerow(["Language", "Repo Path", "Description", "Type", "Date"])

        for repo in repos:
            writer.writerow([
                repo.get("language", "N/A"),
                repo.get("html_url"),
                repo.get("description", "N/A"),
                repo.get("visibility"),
                repo.get("created_at"),
            ])

if __name__ == "__main__":
    repos = fetch_repos()
    generate_csv(repos)
    print("CSV file 'github_repos.csv' generated successfully!")
