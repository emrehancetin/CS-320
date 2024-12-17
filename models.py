from sqlalchemy import Column, Integer, String, Boolean, ForeignKey, DateTime
from sqlalchemy.orm import relationship
from database import Base

class Brand(Base):
    __tablename__ = "brands"
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, unique=True)

class Model(Base):
    __tablename__ = "models"
    id = Column(Integer, primary_key=True, index=True)
    brand_id = Column(Integer, ForeignKey("brands.id"))
    name = Column(String)
    brand = relationship("Brand")

class Car(Base):
    __tablename__ = "cars"
    id = Column(Integer, primary_key=True, index=True)
    brand_id = Column(Integer, ForeignKey("brands.id"))
    model_id = Column(Integer, ForeignKey("models.id"))
    year = Column(Integer)
    fuel_id = Column(Integer, ForeignKey("fuels.id"))

class Fuel(Base):
    __tablename__ = "fuels"
    id = Column(Integer, primary_key=True, index=True)
    name = Column(String, unique=True)

class RentingCar(Base):
    __tablename__ = "renting_cars"
    id = Column(Integer, primary_key=True, index=True)
    car_id = Column(Integer, ForeignKey("cars.id"))
    name = Column(String)
    is_ready = Column(Boolean, default=True)
    color = Column(String)

class Renting(Base):
    __tablename__ = "rentings"
    id = Column(Integer, primary_key=True, index=True)
    car_id = Column(Integer, ForeignKey("cars.id"))
    starting_time = Column(DateTime)
    finish_time = Column(DateTime)
