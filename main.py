from fastapi import FastAPI

from routers.index import router
from middleware import error_handling_middleware
from database import Base, engine

app = FastAPI()

# Middleware
app.middleware("http")(error_handling_middleware)

# Include the combined router
app.include_router(router)

# Initialize database tables
Base.metadata.create_all(bind=engine)
