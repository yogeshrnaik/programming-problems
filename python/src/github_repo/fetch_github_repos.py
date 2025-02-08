import aiohttp
import asyncio
import csv
import base64
import os

# Set your organization name and GitHub token
ORG_NAME = "trustfactors"
GITHUB_TOKEN = os.getenv("GITHUB_TOKEN")

# GitHub API URLs
REPOS_API_URL = f"https://api.github.com/orgs/{ORG_NAME}/repos"

# Headers with authentication
HEADERS = {
    "Authorization": f"Bearer {GITHUB_TOKEN}",
    "Accept": "application/vnd.github.v3+json"
}

async def fetch_json(session, url):
    """Fetch JSON data from a GitHub API URL."""
    async with session.get(url, headers=HEADERS) as response:
        if response.status == 200:
            return await response.json()
        return None  # Handle errors gracefully

async def fetch_all_repos():
    """Fetch all repositories concurrently with pagination."""
    repos = []
    page = 1
    async with aiohttp.ClientSession() as session:
        while True:
            url = f"{REPOS_API_URL}?per_page=100&page={page}"
            data = await fetch_json(session, url)
            if not data:
                break
            repos.extend(data)
            page += 1
    return repos

async def fetch_readme_description(session, repo_full_name):
    """Fetch and extract the first line after the first # header from README.md"""
    print(f"Fetching README description for {repo_full_name}")
    readme_url = f"https://api.github.com/repos/{repo_full_name}/contents/README.md"
    readme_data = await fetch_json(session, readme_url)

    if readme_data and "content" in readme_data:
        readme_content = base64.b64decode(readme_data["content"]).decode("utf-8", errors="ignore")
        lines = readme_content.strip().split("\n")

        found_header = False
        for line in lines:
            line = line.strip()
            if not found_header:
                if line.startswith("#"):  # First header found
                    found_header = True
            else:
                if line:  # Return the first non-empty line after the header
                    return line

    return None  # Return None if no valid description is found

async def process_repos():
    """Fetch repo details and README descriptions in parallel"""
    repos = await fetch_all_repos()
    async with aiohttp.ClientSession() as session:
        tasks = []
        for repo in repos:
            tasks.append(fetch_readme_description(session, repo["full_name"]))

        readme_descriptions = await asyncio.gather(*tasks)  # Fetch all README data concurrently

        # Generate CSV file
        with open("github_repos.csv", "w", newline="", encoding="utf-8") as file:
            writer = csv.writer(file)
            writer.writerow(["Language", "Repo Path", "Description", "Type", "Date"])

            for repo, readme_description in zip(repos, readme_descriptions):
                description = readme_description or repo.get("description", "N/A")
                print("Writing to CSV: ", repo.get("language", "N/A"), repo.get("html_url"), description, repo.get("visibility"), repo.get("created_at"))
                writer.writerow([
                    repo.get("language", "N/A"),
                    repo.get("html_url"),
                    description,
                    repo.get("visibility"),
                    repo.get("created_at"),
                ])

if __name__ == "__main__":
    asyncio.run(process_repos())
    print("CSV file 'github_repos.csv' generated successfully!")