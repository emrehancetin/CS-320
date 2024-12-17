from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from schemas import BrandSchema, ModelSchema, CarSchema, RentingCarSchema, RentingSchema, FuelSchema
from models import Brand, Model, Car, RentingCar, Renting, Fuel
from database import get_db
from typing import Optional, List

router = APIRouter()

@router.post("/fuels/", response_model=FuelSchema, tags=["Fuels"])
def create_fuel(fuel: FuelSchema, db: Session = Depends(get_db)):
    db_fuel = Fuel(**fuel.dict())
    db.add(db_fuel)
    db.commit()
    db.refresh(db_fuel)
    return db_fuel

@router.get("/fuels/", response_model=List[FuelSchema], tags=["Fuels"])
def get_fuels(db: Session = Depends(get_db)):
    return db.query(Fuel).all()

# Brand Routes
@router.post("/brands/", response_model=BrandSchema, tags=["Brands"])
def create_brand(brand: BrandSchema, db: Session = Depends(get_db)):
    db_brand = Brand(**brand.dict())
    db.add(db_brand)
    db.commit()
    db.refresh(db_brand)
    return db_brand

@router.get("/brands/", response_model=List[BrandSchema], tags=["Brands"])
def get_brands(db: Session = Depends(get_db)):
    return db.query(Brand).all()

# Model Routes
@router.post("/models/", response_model=ModelSchema, tags=["Models"])
def create_model(model: ModelSchema, db: Session = Depends(get_db)):
    db_model = Model(**model.dict())
    db.add(db_model)
    db.commit()
    db.refresh(db_model)
    return db_model

@router.get("/models/", response_model=List[ModelSchema], tags=["Models"])
def get_models(brand_id: Optional[int] = None, db: Session = Depends(get_db)):
    if brand_id:
        return db.query(Model).filter(Model.brand_id == brand_id).all()
    return db.query(Model).all()

# Car Routes
@router.post("/cars/", response_model=CarSchema, tags=["Cars"])
def create_car(car: CarSchema, db: Session = Depends(get_db)):
    db_car = Car(**car.dict())
    db.add(db_car)
    db.commit()
    db.refresh(db_car)
    return db_car

@router.get("/cars/", response_model=List[CarSchema], tags=["Cars"])
def get_cars(brand_id: Optional[int] = None, model_id: Optional[int] = None, year: Optional[int] = None, fuel_id: Optional[int] = None, db: Session = Depends(get_db)):
    query = db.query(Car)
    if brand_id:
        query = query.filter(Car.brand_id == brand_id)
    if model_id:
        query = query.filter(Car.model_id == model_id)
    if year:
        query = query.filter(Car.year == year)
    if fuel_id:
        query = query.filter(Car.fuel_id == fuel_id)
    return query.all()

# RentingCar Routes
@router.post("/renting_cars/", response_model=RentingCarSchema, tags=["Renting Cars"])
def create_renting_car(renting_car: RentingCarSchema, db: Session = Depends(get_db)):
    db_renting_car = RentingCar(**renting_car.dict())
    db.add(db_renting_car)
    db.commit()
    db.refresh(db_renting_car)
    return db_renting_car

@router.put("/renting_cars/{id}", response_model=RentingCarSchema, tags=["Renting Cars"])
def update_renting_car(id: int, renting_car: RentingCarSchema, db: Session = Depends(get_db)):
    db_renting_car = db.query(RentingCar).filter(RentingCar.id == id).first()
    if not db_renting_car:
        raise HTTPException(status_code=404, detail="Renting car not found")
    for key, value in renting_car.dict().items():
        setattr(db_renting_car, key, value)
    db.commit()
    db.refresh(db_renting_car)
    return db_renting_car

# Renting Routes
@router.post("/rentings/", response_model=RentingSchema, tags=["Rentings"])
def create_renting(renting: RentingSchema, db: Session = Depends(get_db)):
    db_renting = Renting(**renting.dict())
    db.add(db_renting)
    db.commit()
    db.refresh(db_renting)
    return db_renting

@router.delete("/rentings/{id}", status_code=204, tags=["Rentings"])
def cancel_renting(id: int, db: Session = Depends(get_db)):
    db_renting = db.query(Renting).filter(Renting.id == id).first()
    if not db_renting:
        raise HTTPException(status_code=404, detail="Renting not found")
    db.delete(db_renting)
    db.commit()
