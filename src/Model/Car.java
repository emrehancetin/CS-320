package Model;

public class Car {
    private int ID;
    private int brandID;
    private int modelID;
    private int year;
    private int fuelID;

    public Car(){}
    public Car(int ID, int brandID, int modelID, int year, int fuelID){
        this.ID = ID;
        this.brandID = brandID;
        this.modelID = modelID;
        this.year = year;
        this.fuelID = fuelID;
    }

    public int getID(){
        return ID;
    }

    public int getBrandID() {
        return brandID;
    }

    public int getModelID() {
        return modelID;
    }

    public int getYear() {
        return year;
    }

    public int getFuelID() {
        return fuelID;
    }
}
