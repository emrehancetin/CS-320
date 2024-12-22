from fastapi.testclient import TestClient
from main import app

client = TestClient(app)

def cleanup_database():
    # Define endpoints and their dependencies
    endpoints = [
        "/rentings",        # Delete Rentings first
        "/renting_cars",    # Then Renting Cars
        "/cars",            # Then Cars
        "/models",          # Then Models
        "/brands",          # Then Brands
        "/fuels",           # Then Fuels
        "/auth"       # Finally, delete Users
    ]

    # Iterate through each endpoint and clean up data
    for endpoint in endpoints:
        # Check if GET is supported for this endpoint
        response = client.get(endpoint)
        if response.status_code == 200:
            items = response.json()
            for item in items:
                item_id = item.get("id")
                if item_id:
                    delete_response = client.delete(f"{endpoint}/{item_id}")
                    if delete_response.status_code != 204:
                        print(f"Failed to delete {item_id} from {endpoint}: {delete_response.status_code}")
        elif response.status_code == 405:
            print(f"GET not allowed for {endpoint}. Skipping...")
        else:
            print(f"Failed to fetch items from {endpoint}: {response.status_code}")

if __name__ == "__main__":
    cleanup_database()
    print("Database cleanup complete.")
