package Model;

import java.time.LocalDateTime;

public class Rent {
    private int ID;
    private int carID;
    private LocalDateTime startingTime;
    private LocalDateTime finishTime;

    public int getID() {
        return ID;
    }

    public int getCarID() {
        return carID;
    }

    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }





}
