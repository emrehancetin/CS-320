from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from schemas.index import CarSchema
from models.index import Car
from database import get_db
from typing import List, Optional

router = APIRouter(tags=["Cars"])

@router.post("/", response_model=CarSchema)
def create_car(car: CarSchema, db: Session = Depends(get_db)):
    db_car = Car(**car.model_dump())
    db.add(db_car)
    db.commit()
    db.refresh(db_car)
    return db_car

@router.get("/", response_model=List[CarSchema])
def get_cars(brand_id: Optional[int] = None, model_id: Optional[int] = None, fuel_id: Optional[int] = None, db: Session = Depends(get_db)):
    query = db.query(Car)
    if brand_id:
        query = query.filter(Car.brand_id == brand_id)
    if model_id:
        query = query.filter(Car.model_id == model_id)
    if fuel_id:
        query = query.filter(Car.fuel_id == fuel_id)
    return query.all()

@router.get("/one", response_model=CarSchema)
def get_one_car(brand_id: int, model_id: int, fuel_id: int, db: Session = Depends(get_db)):
    db_car = db.query(Car).filter(
        Car.brand_id == brand_id,
        Car.model_id == model_id,
        Car.fuel_id == fuel_id
    ).first()
    
    if not db_car:
        raise HTTPException(status_code=404, detail="Car not found")
    
    return db_car

@router.delete("/{id}", status_code=204)
def delete_car(id: int, db: Session = Depends(get_db)):
    db_car = db.query(Car).filter(Car.id == id).first()
    if not db_car:
        raise HTTPException(status_code=404, detail="Car not found")
    db.delete(db_car)
    db.commit()
