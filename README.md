
# Car Rental API

## Overview

This project is a **Car Rental API** built with FastAPI. It provides endpoints for managing users, brands, models, cars, fuels, renting cars, and rentings. The API follows a modular structure, organizing routes into categories for better maintainability.

---

## Features

- User authentication (registration and login)
- Management of cars, brands, models, fuels, and renting operations
- Flexible querying with connected `AND` logic for filters
- Easy-to-extend modular structure
- Fully RESTful API with CRUD operations for each resource
- Clean and reusable code with Pydantic for schema validation
- Middleware for global request handling

---

## Folder Structure

```
.
├── models          # SQLAlchemy database models
│   ├── __init__.py # Initializes models package
│   ├── index.py    # Defines all database models
├── routers         # API routes
│   ├── __init__.py # Initializes routers package
│   ├── auth.py     # User authentication routes
│   ├── brand.py    # Brand-related routes
│   ├── car.py      # Car-related routes
│   ├── fuel.py     # Fuel-related routes
│   ├── model.py    # Model-related routes
│   ├── renting.py  # Renting-related routes
│   ├── renting_car.py # Renting Car-related routes
│   ├── index.py    # Combines all routes into a single router
├── schemas         # Pydantic models for request/response validation
│   ├── __init__.py # Initializes schemas package
│   ├── index.py    # Defines all schemas
├── main.py         # Main application entry point
├── middleware.py   # Middleware for request/response handling
├── clean.py        # Script for cleaning up the database
├── test.py         # Test file for verifying API endpoints
├── database.py     # Database setup with SQLAlchemy
├── requirements.txt # Python dependencies
├── README.md       # Project documentation
```

---

## API Endpoints

### **Authentication (`/auth`)**
Handles user registration, login, and retrieval.

| Method | Endpoint         | Parameters                                  | Response Format                                      |
|--------|------------------|---------------------------------------------|-----------------------------------------------------|
| `GET`  | `/auth/`         | None                                        | `[{"id": int, "username": str}]`                    |
| `POST` | `/auth/register/`| `{"username": str, "password": str}`        | `{"id": int, "username": str}`                      |
| `POST` | `/auth/login/`   | `{"username": str, "password": str}`        | `{"message": "Login successful"}`                  |
| `DELETE`| `/auth/{id}`    | None                                        | `204 No Content`                                    |

---

### **Brands (`/brands`)**
Manages car brands.

| Method | Endpoint         | Parameters                                  | Response Format                                      |
|--------|------------------|---------------------------------------------|-----------------------------------------------------|
| `GET`  | `/brands/`       | None                                        | `[{"id": int, "name": str}]`                        |
| `POST` | `/brands/`       | `{"name": str}`                             | `{"id": int, "name": str}`                          |
| `DELETE`| `/brands/{id}`  | None                                        | `204 No Content`                                    |

---

### **Models (`/models`)**
Manages car models.

| Method | Endpoint                   | Parameters                                  | Response Format                                      |
|--------|----------------------------|---------------------------------------------|-----------------------------------------------------|
| `GET`  | `/models/`                 | None                                        | `[{"id": int, "brand_id": int, "name": str}]`       |
| `GET`  | `/models/{brand_id}`       | None                                        | `[{"id": int, "brand_id": int, "name": str}]`       |
| `POST` | `/models/`                 | `{"brand_id": int, "name": str}`            | `{"id": int, "brand_id": int, "name": str}`         |
| `DELETE`| `/models/{id}`            | None                                        | `204 No Content`                                    |

---

### **Fuels (`/fuels`)**
Manages fuel types for cars.

| Method | Endpoint                   | Parameters                                  | Response Format                                      |
|--------|----------------------------|---------------------------------------------|-----------------------------------------------------|
| `GET`  | `/fuels/`                  | None                                        | `[{"id": int, "name": str}]`                        |
| `GET`  | `/fuels/{model_id}`        | None                                        | `[{"id": int, "name": str}]`                        |
| `POST` | `/fuels/`                  | `{"name": str}`                             | `{"id": int, "name": str}`                          |
| `DELETE`| `/fuels/{id}`             | None                                        | `204 No Content`                                    |

---

### **Cars (`/cars`)**
Manages car details.

| Method | Endpoint                   | Parameters                                  | Response Format                                      |
|--------|----------------------------|---------------------------------------------|-----------------------------------------------------|
| `GET`  | `/cars/`                   | None                                        | `[{"id": int, "brand_id": int, "model_id": int, "year": int, "fuel_id": int}]` |
| `GET`  | `/cars/one`                | `brand_id, model_id, fuel_id`              | `{"id": int, "brand_id": int, "model_id": int, "year": int, "fuel_id": int}` |
| `POST` | `/cars/`                   | `{"brand_id": int, "model_id": int, "year": int, "fuel_id": int}` | `{"id": int, "brand_id": int, "model_id": int, "year": int, "fuel_id": int}` |
| `DELETE`| `/cars/{id}`              | None                                        | `204 No Content`                                    |

---

### **Renting Cars (`/renting_cars`)**
Manages cars available for renting.

| Method | Endpoint                   | Parameters                                  | Response Format                                      |
|--------|----------------------------|---------------------------------------------|-----------------------------------------------------|
| `GET`  | `/renting_cars/`           | `year, brand_id, model_id, fuel_id`         | `[{"id": int, "car_id": int, "name": str, "is_ready": bool, "color": str}]` |
| `POST` | `/renting_cars/`           | `{"car_id": int, "name": str, "is_ready": bool, "color": str}` | `{"id": int, "car_id": int, "name": str, "is_ready": bool, "color": str}` |
| `PUT`  | `/renting_cars/{id}`       | `{"name": str, "is_ready": bool, "color": str}` | `{"id": int, "car_id": int, "name": str, "is_ready": bool, "color": str}` |
| `DELETE`| `/renting_cars/{id}`      | None                                        | `204 No Content`                                    |

---

## Running the Project

1. **Install Dependencies**:
   ```bash
   pip install -r requirements.txt
   ```

2. **Run the Server**:
   ```bash
   uvicorn main:app --reload
   ```

3. **Access API Documentation**:
   - Swagger UI: [http://127.0.0.1:8000/docs](http://127.0.0.1:8000/docs)
   - ReDoc: [http://127.0.0.1:8000/redoc](http://127.0.0.1:8000/redoc)

---

## Testing

Run tests using `pytest`:
```bash
pytest test.py
```

---

## Cleanup Database

To clean up the database, use the `clean.py` script:
```bash
python clean.py
```

---

Let me know if you need further refinements!
