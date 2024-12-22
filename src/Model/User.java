package Model;

public abstract class User {
    private int ID;
    private String username;
    private String password;
    public User(int ID, String username, String password){
        this.ID = ID;
        this.username = username;
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
