package Model;

public class Car {
    private int ID;
    private String brand;
    private String model;
    private String color;
    private String fuelType;
    private int year;
    private double price;
    private boolean available;



    public Car(){}

    public int getID(){
        return ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    public String getBrand(){
        return brand;
    }
    public void setBrand(String brand){
        this.brand = brand;
    }
    public String getModel(){
        return model;
    }
    public String getColor(){
        return color;
    }
    public String getFuelType(){
        return fuelType;
    }
    public int getYear(){
        return year;
    }
    public double getPrice(){
        return price;
    }
    public boolean isAvailable(){
        return available;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    public void setModel(String model) {
        this.model = model;
    }
}
