from typing import Optional
from pydantic import BaseModel
import datetime

class BrandSchema(BaseModel):
    id: Optional[int] = None  # Make id optional
    name: str

    class Config:
        from_attributes = True

class ModelSchema(BaseModel):
    id: Optional[int] = None
    brand_id: int
    name: str

    class Config:
        from_attributes = True

class FuelSchema(BaseModel):
    id: Optional[int] = None
    name: str

    class Config:
        from_attributes = True

class CarSchema(BaseModel):
    id: Optional[int] = None
    brand_id: int
    model_id: int
    year: int
    fuel_id: int

    class Config:
        from_attributes = True

class RentingCarSchema(BaseModel):
    id: Optional[int] = None
    car_id: int
    name: str
    is_ready: bool
    color: str

    class Config:
        from_attributes = True

class RentingSchema(BaseModel):
    id: Optional[int] = None
    car_id: int
    starting_time: datetime.datetime
    finish_time: datetime.datetime

    class Config:
        from_attributes = True
