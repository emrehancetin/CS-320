from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from schemas.index import BrandSchema
from models.index import Brand
from database import get_db
from typing import List

router = APIRouter(tags=["Brands"])

# Brand Routes
@router.post("/", response_model=BrandSchema)
def create_brand(brand: BrandSchema, db: Session = Depends(get_db)):
    db_brand = Brand(**brand.model_dump())
    db.add(db_brand)
    db.commit()
    db.refresh(db_brand)
    return db_brand

@router.get("/", response_model=List[BrandSchema])
def get_brands(db: Session = Depends(get_db)):
    return db.query(Brand).all()

@router.delete("/{id}", status_code=204)
def delete_brand(id: int, db: Session = Depends(get_db)):
    db_brand = db.query(Brand).filter(Brand.id == id).first()
    if not db_brand:
        raise HTTPException(status_code=404, detail="Brand not found")
    db.delete(db_brand)
    db.commit()
