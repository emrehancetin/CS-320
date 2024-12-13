package Model;

public class Admin extends User{
    public Admin(){
        super();
    }
    public void showList(){
        System.out.println("\n1. Add New Model.Car");
        System.out.println("2. View Model.Car");
        System.out.println("3. Update Model.Car");
        System.out.println("4. Delete Model.Car");
        System.out.println("5. Show Rents");
        System.out.println("6. Exit");
    }
}
