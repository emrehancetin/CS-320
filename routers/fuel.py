from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from schemas.index import FuelSchema, ModelSchema, CarSchema
from models.index import Fuel, Model, Car
from database import get_db
from typing import List

router = APIRouter(tags=["Fuels"])

# Fuel Routes
@router.post("/", response_model=FuelSchema)
def create_fuel(fuel: FuelSchema, db: Session = Depends(get_db)):
    db_fuel = Fuel(**fuel.model_dump())
    db.add(db_fuel)
    db.commit()
    db.refresh(db_fuel)
    return db_fuel

@router.get("/", response_model=List[FuelSchema])
def get_fuels(db: Session = Depends(get_db)):
    return db.query(Fuel).all()

@router.get("/{model_id}", response_model=List[FuelSchema])
def get_unique_fuels_by_model(model_id: int, db: Session = Depends(get_db)):
    unique_fuels = (
        db.query(Fuel)
        .join(Car, Car.fuel_id == Fuel.id)
        .filter(Car.model_id == model_id)
        .distinct(Fuel.id)
        .all()
    )
    return unique_fuels

@router.delete("/{id}", status_code=204)
def delete_fuel(id: int, db: Session = Depends(get_db)):
    db_fuel = db.query(Fuel).filter(Fuel.id == id).first()
    if not db_fuel:
        raise HTTPException(status_code=404, detail="Fuel not found")
    db.delete(db_fuel)
    db.commit()
