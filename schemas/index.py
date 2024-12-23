from typing import Optional
from pydantic import BaseModel, ConfigDict
import datetime


class UserSchema(BaseModel):
    id: Optional[int] = None
    username: str
    password: Optional[str] = None  # Include password for input, but exclude from output

    model_config = ConfigDict(from_attributes=True, json_encoders={"password": None})


class BrandSchema(BaseModel):
    id: Optional[int] = None
    name: str

    model_config = ConfigDict(from_attributes=True)


class ModelSchema(BaseModel):
    id: Optional[int] = None
    brand_id: int
    name: str

    model_config = ConfigDict(from_attributes=True, protected_namespaces=[])


class FuelSchema(BaseModel):
    id: Optional[int] = None
    name: str

    model_config = ConfigDict(from_attributes=True)


class CarSchema(BaseModel):
    id: Optional[int] = None
    brand_id: int
    model_id: int
    fuel_id: int

    model_config = ConfigDict(from_attributes=True, protected_namespaces=[])


class RentingCarSchema(BaseModel):
    id: Optional[int] = None
    car_id: int
    name: str
    is_ready: bool
    color: str

    model_config = ConfigDict(from_attributes=True)


class RentingSchema(BaseModel):
    id: Optional[int] = None
    user_id: int
    renting_car_id: int
    starting_time: datetime.datetime
    finish_time: datetime.datetime

    model_config = ConfigDict(from_attributes=True)
