from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from schemas.index import UserSchema
from models.index import User
from database import get_db
from typing import Dict, List
from passlib.context import CryptContext

router = APIRouter(tags=["Auth"])
pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")

# Register Route
@router.get("/", response_model=List[UserSchema])
def get_users(db: Session = Depends(get_db)):
    users = db.query(User).all()
    for user in users:
        user.password = None  # Exclude the password from the response
    return users

@router.post("/register/", response_model=UserSchema)
def register_user(user: UserSchema, db: Session = Depends(get_db)):
    hashed_password = pwd_context.hash(user.password)
    user_dict = user.model_dump()
    user_dict["password"] = hashed_password  # Add hashed password
    db_user = User(**user_dict)
    db.add(db_user)
    db.commit()
    db.refresh(db_user)
    return db_user

# Login Route
@router.post("/login/")
def login_user(user: UserSchema, db: Session = Depends(get_db)) -> Dict[str, str]:
    db_user = db.query(User).filter(User.username == user.username).first()
    if not db_user or not pwd_context.verify(user.password, db_user.password):
        raise HTTPException(status_code=401, detail="Invalid credentials")
    return {"message": "Login successful"}

@router.delete("/{id}", status_code=204)
def delete_user(id: int, db: Session = Depends(get_db)):
    db_user = db.query(User).filter(User.id == id).first()
    if not db_user:
        raise HTTPException(status_code=404, detail="User not found")
    db.delete(db_user)
    db.commit()