# Renting Car Application API

This is a **FastAPI-based** backend application for managing a car renting service. The API provides endpoints for managing brands, models, cars, fuels, renting cars, and rentings. It uses **PostgreSQL** as the database and supports JSON-based request/response.

---

## Features

- **Brand Management**: Create and retrieve car brands.
- **Model Management**: Associate models with brands.
- **Car Management**: Create and retrieve cars with details like brand, model, and fuel type.
- **Fuel Management**: Manage fuel types for cars.
- **Renting Cars**: Manage cars available for renting.
- **Renting**: Handle car renting operations, including creation and cancellation.

---

## Technology Stack

- **Framework**: FastAPI
- **Database**: PostgreSQL
- **ORM**: SQLAlchemy
- **Testing**: HTTPX
- **Language**: Python 3.9+

---

## Installation and Setup

### **1. Clone the Repository**

```bash
git clone https://github.com/your-repository-name/renting-car-api.git
cd renting-car-api
```

### **2. Set Up a Virtual Environment**

```bash
python -m venv venv
```

Activate the virtual environment:

- **On Linux/macOS**:
  ```bash
  source venv/bin/activate
  ```

- **On Windows**:
  ```bash
  venv\Scripts\activate
  ```

### **3. Install Dependencies**

Install the required packages from `requirements.txt`:

```bash
pip install -r requirements.txt
```

### **4. Set Up the Database**

1. Start your PostgreSQL server.
2. Create a database for the project:
   ```sql
   CREATE DATABASE renting_car_db;
   ```
3. Update the `DATABASE_URL` in `database.py` or `.env`:
   ```text
   DATABASE_URL=postgresql+psycopg2://username:password@localhost:5432/renting_car_db
   ```

### **5. Run Database Migrations**

Use the following command to create tables:
```bash
python main.py
```

### **6. Start the Server**

Run the FastAPI server using Uvicorn:
```bash
uvicorn main:app --reload
```

Access the API at:
- **Docs**: [http://127.0.0.1:8000/docs](http://127.0.0.1:8000/docs)
- **Redoc**: [http://127.0.0.1:8000/redoc](http://127.0.0.1:8000/redoc)

---

## API Endpoints

### **Brand Endpoints**
| Method | Endpoint      | Description          |
|--------|---------------|----------------------|
| POST   | `/brands/`    | Create a new brand   |
| GET    | `/brands/`    | Retrieve all brands  |

### **Model Endpoints**
| Method | Endpoint      | Description           |
|--------|---------------|-----------------------|
| POST   | `/models/`    | Create a new model    |
| GET    | `/models/`    | Retrieve all models   |

### **Fuel Endpoints**
| Method | Endpoint      | Description           |
|--------|---------------|-----------------------|
| POST   | `/fuels/`     | Create a new fuel     |
| GET    | `/fuels/`     | Retrieve all fuels    |

### **Car Endpoints**
| Method | Endpoint      | Description           |
|--------|---------------|-----------------------|
| POST   | `/cars/`      | Create a new car      |
| GET    | `/cars/`      | Retrieve all cars     |

### **Renting Car Endpoints**
| Method | Endpoint                 | Description               |
|--------|--------------------------|---------------------------|
| POST   | `/renting_cars/`         | Create a new renting car  |
| PUT    | `/renting_cars/{id}`     | Update a renting car      |

### **Renting Endpoints**
| Method | Endpoint                 | Description               |
|--------|--------------------------|---------------------------|
| POST   | `/rentings/`             | Create a new renting      |
| DELETE | `/rentings/{id}`         | Cancel a renting          |

---

## Running Tests

### **Pre-requisites**
Ensure the server is running before executing tests:
```bash
uvicorn main:app --reload
```

### **Run Tests**
Execute the test file to validate all endpoints:
```bash
python test_api.py
```
