package Controller;

import Panel.CarRentalUserPanel;
import javax.swing.*;

public class LoginController {
    public static boolean authenticate(String username, String password) {
        return false;
    }

    public static boolean trylogin(String username, String password){
        if(username.equals("Eren") && password.equals("blackstone")){
            CarRentalUserPanel.start();
            return true;
        }else{
            JOptionPane.showMessageDialog(
                    null,
                    "Invalid credentials!",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return false;
        }
    }
}
