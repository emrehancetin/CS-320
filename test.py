import httpx
import random

BASE_URL = "http://127.0.0.1:8000"

# Dynamic test data
unique_suffix = random.randint(1000, 9999)
ids = {
    "brand_id": None,
    "model_id": None,
    "fuel_id": None,
    "car_id": None,
    "renting_car_id": None,
    "renting_id": None,
}

def cleanup_test_data():
    print("Cleaning up test data...")
    if ids["renting_id"]:
        httpx.delete(f"{BASE_URL}/rentings/{ids['renting_id']}")
    if ids["renting_car_id"]:
        httpx.delete(f"{BASE_URL}/renting_cars/{ids['renting_car_id']}")
    if ids["car_id"]:
        httpx.delete(f"{BASE_URL}/cars/{ids['car_id']}")
    if ids["model_id"]:
        httpx.delete(f"{BASE_URL}/models/{ids['model_id']}")
    if ids["brand_id"]:
        httpx.delete(f"{BASE_URL}/brands/{ids['brand_id']}")
    if ids["fuel_id"]:
        httpx.delete(f"{BASE_URL}/fuels/{ids['fuel_id']}")
    print("Test data cleanup completed!")

def test_create_brand():
    print("Testing: Create Brand")
    response = httpx.post(f"{BASE_URL}/brands/", json={"name": f"Toyota-{unique_suffix}"})
    print("Status Code:", response.status_code)
    print("Response:", response.json())
    if response.status_code == 200:
        ids["brand_id"] = response.json()["id"]

def test_create_model():
    print("Testing: Create Model")
    response = httpx.post(f"{BASE_URL}/models/", json={"brand_id": ids["brand_id"], "name": f"Corolla-{unique_suffix}"})
    print("Status Code:", response.status_code)
    print("Response:", response.json())
    if response.status_code == 200:
        ids["model_id"] = response.json()["id"]

def test_create_fuel():
    print("Testing: Create Fuel")
    response = httpx.post(f"{BASE_URL}/fuels/", json={"name": f"Petrol-{unique_suffix}"})
    print("Status Code:", response.status_code)
    print("Response:", response.json())
    if response.status_code == 200:
        ids["fuel_id"] = response.json()["id"]

def test_create_car():
    print("Testing: Create Car")
    response = httpx.post(f"{BASE_URL}/cars/", json={
        "brand_id": ids["brand_id"],
        "model_id": ids["model_id"],
        "year": 2023,
        "fuel_id": ids["fuel_id"]
    })
    print("Status Code:", response.status_code)
    print("Response:", response.json())
    if response.status_code == 200:
        ids["car_id"] = response.json()["id"]

def test_create_renting_car():
    print("Testing: Create Renting Car")
    response = httpx.post(f"{BASE_URL}/renting_cars/", json={
        "car_id": ids["car_id"],
        "name": f"Corolla Rental-{unique_suffix}",
        "is_ready": True,
        "color": "FFFFFF"
    })
    print("Status Code:", response.status_code)
    print("Response:", response.json())
    if response.status_code == 200:
        ids["renting_car_id"] = response.json()["id"]

def test_create_renting():
    print("Testing: Create Renting")
    response = httpx.post(f"{BASE_URL}/rentings/", json={
        "car_id": ids["car_id"],
        "starting_time": "2024-06-16T10:00:00",
        "finish_time": "2024-06-17T10:00:00"
    })
    print("Status Code:", response.status_code)
    print("Response:", response.json())
    if response.status_code == 200:
        ids["renting_id"] = response.json()["id"]

def run_tests():
    print("Starting API Tests...\n")
    cleanup_test_data()  # Clean up before running tests
    
    test_create_brand()
    test_create_model()
    test_create_fuel()
    test_create_car()
    test_create_renting_car()
    test_create_renting()

    cleanup_test_data()  # Clean up after running tests

if __name__ == "__main__":
    run_tests()
