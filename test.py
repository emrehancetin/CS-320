import pytest
from fastapi.testclient import TestClient
from main import app

client = TestClient(app)

# Helper function to create an item
def create_item(endpoint, payload):
    response = client.post(endpoint, json=payload)
    if response.status_code == 400 and "already exists" in response.json().get("details", ""):
        if endpoint == "/auth/register/":
            # Return the user directly if the username already exists
            return {"username": payload["username"]}
        else:
            # Fetch the existing item
            get_response = client.get(endpoint)
            if get_response.status_code == 200:
                items = get_response.json()
                existing_item = next((item for item in items if item.get("name") == payload.get("name")), None)
                if existing_item:
                    return existing_item
    assert response.status_code == 200, f"Error in {endpoint}: {response.json()}"
    return response.json()




# Helper function to delete an item
def delete_item(endpoint, item_id):
    response = client.delete(f"{endpoint}/{item_id}")
    assert response.status_code == 204, f"Error in delete {endpoint}: {response.status_code}"

def cleanup_database():
    # Define endpoints in reverse order of dependencies
    endpoints = ["/rentings", "/renting_cars", "/cars", "/models", "/brands", "/fuels", "/auth"]
    for endpoint in endpoints:
        response = client.get(endpoint)
        if response.status_code == 200:
            for item in response.json():
                client.delete(f"{endpoint}/{item['id']}")

# Test the entire workflow
def test_workflow():
    cleanup_database()

    # Create Fuels
    fuel = create_item("/fuels/", {"name": "Gasoline"})
    fuel_id = fuel["id"]

    # Create Brands
    brand = create_item("/brands/", {"name": "Toyota"})
    brand_id = brand["id"]

    # Create Models
    model = create_item("/models/", {"brand_id": brand_id, "name": "Corolla"})
    model_id = model["id"]

    # Create Cars
    car = create_item("/cars/", {"brand_id": brand_id, "model_id": model_id, "year": 2022, "fuel_id": fuel_id})
    car_id = car["id"]

    # Create Renting Cars
    renting_car = create_item("/renting_cars/", {"car_id": car_id, "name": "Rental Corolla", "is_ready": True, "color": "Blue"})
    renting_car_id = renting_car["id"]

    # Create Auth (User)
    auth = create_item("/auth/register/", {"username": "testuser", "password": "securepassword"})

