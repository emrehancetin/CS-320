package Model;

public class Car {
    private int ID;
    private int brandID;
    private int modelID;

    private int fuelID;

    public Car(){}
    public Car(int brandID, int modelID,int fuelID){
        this.brandID = brandID;
        this.modelID = modelID;
        this.fuelID = fuelID;
    }
    public Car(int ID, int brandID, int modelID,int fuelID){
        this.ID = ID;
        this.brandID = brandID;
        this.modelID = modelID;
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



    public int getFuelID() {
        return fuelID;
    }

    @Override
    public String toString() {
        return "Car{" +
                "ID=" + ID +
                ", brandID=" + brandID +
                ", modelID=" + modelID +
                ", fuelID=" + fuelID +
                '}';
    }


}
