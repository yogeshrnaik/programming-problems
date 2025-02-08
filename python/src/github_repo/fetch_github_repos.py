import base64

import requests
import csv
import os

# Set your organization name and GitHub token
ORG_NAME = "trustfactors"
GITHUB_TOKEN = os.getenv("GITHUB_TOKEN")

# GitHub API URLs
REPOS_API_URL = f"https://api.github.com/orgs/{ORG_NAME}/repos"

# Headers with authentication
HEADERS = {"Authorization": f"Bearer {GITHUB_TOKEN}", "Accept": "application/vnd.github.v3+json"}

def fetch_repos():
    """Fetch all repositories from the organization"""
    repos = []
    page = 1
    while True:
        response = requests.get(REPOS_API_URL, headers=HEADERS, params={"per_page": 100, "page": page})
        if response.status_code != 200:
            print("Error fetching repositories:", response.json())
            break

        data = response.json()
        if not data:
            break  # No more repositories

        repos.extend(data)
        page += 1

    return repos
def get_readme_description(repo_full_name):
    """Fetch and extract the first non-header line from README.md"""
    readme_url = f"https://api.github.com/repos/{repo_full_name}/contents/README.md"
    response = requests.get(readme_url, headers=HEADERS)

    if response.status_code == 200:
        readme_data = response.json()
        if "content" in readme_data:
            readme_content = base64.b64decode(readme_data["content"]).decode("utf-8", errors="ignore")
            lines = readme_content.strip().split("\n")

            for line in lines:
                line = line.strip()
                if line and not line.startswith("#"):  # Skip empty lines and headers
                    return line  # Return the first valid line

    return "N/A"  # Default if README is missing or has only headers


def generate_csv(repos):
    """Generate CSV file with repository details"""
    with open("github_repos.csv", "w", newline="", encoding="utf-8") as file:
        writer = csv.writer(file)
        writer.writerow(["Language", "Repo Path", "Description", "Type", "Date"])

        for repo in repos:
            repo_full_name = repo["full_name"]  # e.g., "org_name/repo_name"
            readme_description = get_readme_description(repo_full_name)
            print(f"Writing to CSV: {repo.get('language', 'N/A')}, {repo.get('html_url')}, {readme_description if readme_description != 'N/A' else repo.get('description', 'N/A')}, {repo.get('visibility')}, {repo.get('created_at')}")
            writer.writerow([
                repo.get("language", "N/A"),
                repo.get("html_url"),
                readme_description if readme_description != "N/A" else repo.get("description", "N/A"),
                repo.get("visibility"),
                repo.get("created_at"),
            ])

if __name__ == "__main__":
    repos = fetch_repos()
    generate_csv(repos)
    print("CSV file 'github_repos.csv' generated successfully!")
