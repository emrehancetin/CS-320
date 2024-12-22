from fastapi import APIRouter
from routers import auth, brand, model, car, fuel, renting_car, renting

router = APIRouter()

# Include categorized routers
router.include_router(auth.router, prefix="/auth", tags=["Auth"])
router.include_router(brand.router, prefix="/brands", tags=["Brands"])
router.include_router(model.router, prefix="/models", tags=["Models"])
router.include_router(car.router, prefix="/cars", tags=["Cars"])
router.include_router(fuel.router, prefix="/fuels", tags=["Fuels"])
router.include_router(renting_car.router, prefix="/renting_cars", tags=["Renting Cars"])
router.include_router(renting.router, prefix="/rentings", tags=["Rentings"])
