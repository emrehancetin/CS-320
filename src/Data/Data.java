package Data;

import Connection.HttpURLConnect;
import Model.*;
import org.json.JSONArray;
import org.json.JSONObject;


public class Data {
    public static HttpURLConnect httpURLConnect = new HttpURLConnect();
    public static Fuel[] getFuels() throws Exception {
        String urlFuels = "http://localhost:8000/fuels/";
        String response = HttpURLConnect.sendGetRequest(urlFuels);

        JSONArray jsonArray = new JSONArray(response);
        Fuel[] fuels = new Fuel[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject brand = jsonArray.getJSONObject(i);
            int fuelId = brand.getInt("id");
            String fuelName = brand.getString("name");
            Fuel fuel = new Fuel(fuelId,fuelName);
            fuels[i] = fuel;
            //System.out.println(fuel.getName());
        }
        return fuels;
    }
    public static Brands[] getBrands() throws Exception{
        String urlBrands = "http://localhost:8000/brands/";
        String response = HttpURLConnect.sendGetRequest(urlBrands);
        JSONArray jsonArray = new JSONArray(response);
        Brands[] brands = new Brands[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject brand = jsonArray.getJSONObject(i);
            int brandId = brand.getInt("id");
            String brandName = brand.getString("name");
            Brands brandObj = new Brands(brandId,brandName);
            brands[i] = brandObj;
           // System.out.println(brandObj.getName());
        }
        return brands;
    }
    public static Models[] getModels() throws Exception{
        String urlModels = "http://localhost:8000/models/";
        String response = HttpURLConnect.sendGetRequest(urlModels);
        JSONArray jsonArray = new JSONArray(response);
        Models[] models = new Models[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject model = jsonArray.getJSONObject(i);
            int modelId = model.getInt("id");
            int brandId = model.getInt("brand_id");
            String modelName = model.getString("name");
            Models modelObj = new Models(modelId,brandId,modelName);
            models[i] = modelObj;
            //System.out.println(modelObj.getName());
        }
        return models;
    }

    public static Car[] getCars() throws Exception{
        String urlCars = "http://localhost:8000/cars/";
        String response = HttpURLConnect.sendGetRequest(urlCars);
        JSONArray jsonArray = new JSONArray(response);
        Car[] cars = new Car[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject car = jsonArray.getJSONObject(i);
            int carId = car.getInt("id");
            int brandId = car.getInt("brand_id");
            int modelId = car.getInt("model_id");
            int fuelId = car.getInt("fuel_id");
            Car carObj = new Car(carId,brandId,modelId,fuelId);
            cars[i] = carObj;
            //System.out.println(carObj.toString());
        }
        return cars;
    }
    public static void addCar(Car car) throws Exception {
        String urlCars = "http://localhost:8000/cars/";
        String jsonPayload = "{\n" +
                "    \"brand_id\": "+car.getBrandID()+",\n" +
                "    \"model_id\": "+car.getModelID()+",\n" +
                "    \"fuel_id\": "+car.getFuelID()+"\n" +
                "}";
        String response = HttpURLConnect.sendPostRequest(urlCars,jsonPayload);

    }
    public static boolean deleteCarByID(int carID) throws Exception {
        String urlCars = "http://localhost:8000/cars/"+carID;
        String response = HttpURLConnect.sendDeleteRequest(urlCars);
        return true;
    }

    public static RentingCars[] getRentingCars() throws Exception{
        String urlRentingCars = "http://localhost:8000/renting_cars/";
        String response = HttpURLConnect.sendGetRequest(urlRentingCars);
        JSONArray jsonArray = new JSONArray(response);
        RentingCars[] rentingCars = new RentingCars[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject rentingCar = jsonArray.getJSONObject(i);
            int rentingCarId = rentingCar.getInt("id");
            int carId = rentingCar.getInt("car_id");
            String name = rentingCar.getString("name");
            boolean isReady = rentingCar.getBoolean("is_ready");
            String color = rentingCar.getString("color");
            RentingCars rentingCarObj = new RentingCars(rentingCarId,carId,name,isReady,color);
            rentingCars[i] = rentingCarObj;
            //System.out.println(rentingCarObj.toString());
        }
        return rentingCars;
    }

    public static Rent[] getRents() throws Exception{
        String urlRents = "http://localhost:8000/rentings/";
        String response = HttpURLConnect.sendGetRequest(urlRents);
        JSONArray jsonArray = new JSONArray(response);
        Rent[] rents = new Rent[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject rent = jsonArray.getJSONObject(i);
            int rentId = rent.getInt("id");
            int userId = rent.getInt("user_id");
            int rentingCarId = rent.getInt("renting_car_id");
            String startDate = rent.getString("starting_time");
            String endDate = rent.getString("finish_time");
            Rent rentObj = new Rent(rentId,userId,rentingCarId,startDate,endDate);
            rents[i] = rentObj;
            //System.out.println(rentObj.toString());
        }
        return rents;
    }

}
