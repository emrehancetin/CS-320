from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from schemas.index import RentingSchema
from models.index import Renting
from database import get_db
from typing import Optional, List

router = APIRouter(tags=["Rentings"])

# Renting Routes
@router.post("/", response_model=RentingSchema)
def create_renting(renting: RentingSchema, db: Session = Depends(get_db)):
    db_renting = Renting(**renting.model_dump())
    db.add(db_renting)
    db.commit()
    db.refresh(db_renting)
    return db_renting

@router.get("/", response_model=List[RentingSchema])
def get_rentings(user_id: Optional[int] = None, db: Session = Depends(get_db)):
    query = db.query(Renting)
    if user_id:
        query = query.filter(Renting.user_id == user_id)
    return query.all()

@router.delete("/{id}", status_code=204)
def delete_renting(id: int, db: Session = Depends(get_db)):
    db_renting = db.query(Renting).filter(Renting.id == id).first()
    if not db_renting:
        raise HTTPException(status_code=404, detail="Renting not found")
    db.delete(db_renting)
    db.commit()