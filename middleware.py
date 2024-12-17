from fastapi import Request
from fastapi.responses import JSONResponse
from sqlalchemy.exc import IntegrityError

async def error_handling_middleware(request: Request, call_next):
    try:
        response = await call_next(request)
        return response
    except IntegrityError as exc:
        return JSONResponse(
            status_code=400,
            content={"error": "Database error occurred", "details": str(exc.orig)},
        )
    except ValueError as exc:
        return JSONResponse(
            status_code=422,
            content={"error": str(exc), "type": "ValidationError"},
        )
    except Exception as exc:
        return JSONResponse(
            status_code=500,
            content={"error": "Internal server error", "details": str(exc)},
        )
