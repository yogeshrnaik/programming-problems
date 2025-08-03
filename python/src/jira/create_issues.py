import csv
import json
import os
import sys
import requests

def create_jira_issue(jira_access_token, jira_url, issue_data):
    """
    Creates a Jira issue using the REST API.

    Args:
        jira_access_token (str): Jira Personal access token.
        jira_url (str): Base Jira API URL (e.g., https://jira.intuit.com/rest/api/2/issue).
        issue_data (dict): Dictionary containing the fields for the new Jira issue.

    Returns:
        tuple: (HTTP Status Code, Response Body JSON or Text)
    """
    auth_header = f"Bearer {jira_access_token}"

    headers = {
        'Authorization': auth_header,
        'Content-Type': 'application/json'
    }

    try:
        response = requests.post(jira_url, headers=headers, data=json.dumps(issue_data))
        response.raise_for_status() # Raise an HTTPError for bad responses (4xx or 5xx)
        return response.status_code, response.json()
    except requests.exceptions.HTTPError as http_err:
        print(f"HTTP error occurred: {http_err}")
        print(f"Response Text: {response.text}")
        return response.status_code, {"errorMessages": [f"HTTP Error: {http_err}", f"Response: {response.text}"]}
    except requests.exceptions.ConnectionError as conn_err:
        print(f"Connection error occurred: {conn_err}")
        return None, {"errorMessages": [f"Connection Error: {conn_err}"]}
    except requests.exceptions.Timeout as timeout_err:
        print(f"Timeout error occurred: {timeout_err}")
        return None, {"errorMessages": [f"Timeout Error: {timeout_err}"]}
    except requests.exceptions.RequestException as req_err:
        print(f"An unexpected request error occurred: {req_err}")
        return None, {"errorMessages": [f"Request Error: {req_err}"]}
    except json.JSONDecodeError:
        # This happens if response.json() fails, meaning the response isn't valid JSON
        print("Response was not valid JSON.")
        return response.status_code, response.text


def main():
    if len(sys.argv) != 3:
        print("Usage: python create_jira_tickets.py <jira_base_url> <csv_file_path>")
        print("Example: python create_jira_tickets.py https://jira.intuit.com issues.csv")
        sys.exit(1)

    jira_base_url = sys.argv[1].rstrip('/')
    csv_file_path = sys.argv[2]

    jira_access_token = os.getenv("JIRA_ACCESS_TOKEN")

    if not jira_access_token:
        print("Error: JIRA_ACCESS_TOKEN environment variables must be set.")
        sys.exit(1)

    create_issue_url = f"{jira_base_url}/rest/api/2/issue"

    # Issue Type Mapping
    issue_type_map = {
        "Epic": "11",
        "Story": "12",
        "Sub-Task": "5"
    }
    
    # Priority ID Mapping
    priority_map = {
        "Immediate": "1",
        "High": "2",
        "Medium": "3",
        "Low": "4"
    }

    # Custom Field IDs
    STORY_POINTS_FIELD = "customfield_11703"
    SCRUM_TEAM_FIELD = "customfield_13505"
    EPIC_FIELD = "customfield_12002"
    TARGETED_RELEASE_FIELD = "customfield_17002"

    try:
        with open(csv_file_path, mode='r', encoding='utf-8-sig') as csvfile: # 'utf-8-sig' handles BOM if present
            reader = csv.DictReader(csvfile)
            for row in reader:
                project_key = row.get('Project Key', '').strip()
                issue_type_name = row.get('Issue Type', '').strip()
                epic = row.get('Epic', '').strip()
                summary = row.get('Summary', '').strip()
                description = row.get('Description', '').strip()
                story_points = row.get('Story Points', '').strip()
                component = row.get('Component', '').strip()
                targeted_release = row.get('Targeted Release', '').strip()
                assignee = row.get('Assignee', '').strip()
                priority = row.get('Priority', '').strip()
                scrum_team = row.get('Scrum Team', '').strip()

                if not project_key or not issue_type_name or not summary:
                    print(f"Skipping row due to missing mandatory fields (Project Key, Issue Type, Summary): {row}")
                    continue

                # Build the base fields dictionary
                fields = {
                    "project": {
                        "key": project_key
                    },
                    "summary": summary,
                    "description": description,
                    "issuetype": {
                        "name": issue_type_name
                    },
                    "components": [{"name": component}],
                    TARGETED_RELEASE_FIELD: {"value": targeted_release}
                }

                # Conditionally add optional fields
                if epic:
                    fields[EPIC_FIELD] = epic
                if assignee:
                    fields["assignee"] = {"name": assignee}
                if priority:
                    priority_id = priority_map.get(priority)
                    if priority_id:
                        fields["priority"] = {"id": priority_id}
                    else:
                        print(f"Warning: Unknown priority '{priority}' for '{summary}'. Skipping priority for this issue.")
                if story_points:
                    try:
                        fields[STORY_POINTS_FIELD] = int(story_points)
                    except ValueError:
                        print(f"Warning: Invalid Story Points value '{story_points}' for '{summary}'. Skipping story points for this issue.")
                if scrum_team:
                    fields[SCRUM_TEAM_FIELD] = {"value": scrum_team}

                issue_payload = {"fields": fields}

                print(f"--- Creating issue: {summary} ---")
                # Uncomment the line below to see the exact JSON payload being sent for debugging
                print(f"Payload: {json.dumps(issue_payload, indent=2)}")

                status_code, response_body = create_jira_issue(jira_access_token, create_issue_url, issue_payload)

                print(f"HTTP Status Code: {status_code}")
                print(f"Response Body: {json.dumps(response_body, indent=2)}") # Pretty print JSON response
                print("")

    except FileNotFoundError:
        print(f"Error: CSV file not found at '{csv_file_path}'")
        sys.exit(1)
    except Exception as e:
        print(f"An unexpected error occurred: {e}")
        sys.exit(1)

if __name__ == "__main__":
    main()