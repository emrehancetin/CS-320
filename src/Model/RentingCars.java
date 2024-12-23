package Model;

public class RentingCars {
    private int ID;
    private int carID;
    private String name;
    private boolean isReady;
    private String color;

    public RentingCars(int ID, int carID, String name, boolean isReady, String color){
        this.ID = ID;
        this.carID = carID;
        this.name = name;
        this.isReady = isReady;
        this.color = color;
    }

    public int getID() {
        return ID;
    }

    public int getCarID() {
        return carID;
    }

    public String getName() {
        return name;
    }

    public boolean isReady() {
        return isReady;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "RentingCars{" +
                "ID=" + ID +
                ", carID=" + carID +
                ", name='" + name + '\'' +
                ", isReady=" + isReady +
                ", color='" + color + '\'' +
                '}';
    }
}
