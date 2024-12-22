package Model;

public class Models {
    private int ID;
    private int brandID;
    private String name;

    public Models(int ID, int brandID, String name){
        this.ID = ID;
        this.brandID = brandID;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public int getBrandID() {
        return brandID;
    }

    public String getName() {
        return name;
    }


}
