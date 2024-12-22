from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from schemas.index import RentingCarSchema, CarSchema
from models.index import RentingCar, Car
from database import get_db
from typing import List, Optional

router = APIRouter(tags=["Renting Cars"])

@router.get("/", response_model=List[RentingCarSchema])
def get_renting_cars(
    year: Optional[int] = None,
    brand_id: Optional[int] = None,
    model_id: Optional[int] = None,
    fuel_id: Optional[int] = None,
    db: Session = Depends(get_db),
):
    query = db.query(RentingCar).join(Car)

    if year is not None:
        query = query.filter(Car.year == year)
    if brand_id is not None:
        query = query.filter(Car.brand_id == brand_id)
    if model_id is not None:
        query = query.filter(Car.model_id == model_id)
    if fuel_id is not None:
        query = query.filter(Car.fuel_id == fuel_id)

    renting_cars = query.all()
    return renting_cars

@router.post("/", response_model=RentingCarSchema)
def create_renting_car(renting_car: RentingCarSchema, db: Session = Depends(get_db)):
    db_renting_car = RentingCar(**renting_car.model_dump())
    db.add(db_renting_car)
    db.commit()
    db.refresh(db_renting_car)
    return db_renting_car

@router.put("/{id}", response_model=RentingCarSchema)
def update_renting_car(id: int, renting_car: RentingCarSchema, db: Session = Depends(get_db)):
    db_renting_car = db.query(RentingCar).filter(RentingCar.id == id).first()
    if not db_renting_car:
        raise HTTPException(status_code=404, detail="Renting car not found")
    for key, value in renting_car.model_dump().items():
        setattr(db_renting_car, key, value)
    db.commit()
    db.refresh(db_renting_car)
    return db_renting_car

@router.delete("/{id}", status_code=204)
def delete_renting_car(id: int, db: Session = Depends(get_db)):
    db_renting_car = db.query(RentingCar).filter(RentingCar.id == id).first()
    if not db_renting_car:
        raise HTTPException(status_code=404, detail="Renting car not found")
    db.delete(db_renting_car)
    db.commit()