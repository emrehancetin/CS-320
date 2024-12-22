from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from schemas.index import ModelSchema
from models.index import Model
from database import get_db
from typing import List, Optional

router = APIRouter(tags=["Models"])

@router.post("/", response_model=ModelSchema)
def create_model(model: ModelSchema, db: Session = Depends(get_db)):
    db_model = Model(**model.model_dump())
    db.add(db_model)
    db.commit()
    db.refresh(db_model)
    return db_model

@router.get("/", response_model=List[ModelSchema])
def get_models(brand_id: Optional[int] = None, db: Session = Depends(get_db)):
    query = db.query(Model)
    if brand_id:
        query = query.filter(Model.brand_id == brand_id)
    return query.all()

@router.delete("/{id}", status_code=204)
def delete_model(id: int, db: Session = Depends(get_db)):
    db_model = db.query(Model).filter(Model.id == id).first()
    if not db_model:
        raise HTTPException(status_code=404, detail="Model not found")
    db.delete(db_model)
    db.commit()