package Model;

public class Rent {
    private int ID;
    private int userID;
    private int rentingCarID;
    private String startingTime;
    private String finishTime;

    public Rent(int ID, int userID,int rentingCarID, String startingTime, String finishTime) {
        this.ID = ID;
        this.userID = userID;
        this.rentingCarID = rentingCarID;

        this.startingTime = startingTime;
        this.finishTime = finishTime;
    }
    public int getID() {
        return ID;
    }

    public int getUserID() {
        return userID;
    }

    public int getRentingCarID() {
        return rentingCarID;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "ID=" + ID +
                ", userID=" + userID +
                ", rentingCarID=" + rentingCarID +
                ", startingTime=" + startingTime +
                ", finishTime=" + finishTime +
                '}';
    }
}
