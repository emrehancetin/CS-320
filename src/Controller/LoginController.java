package Controller;
import Panel.CarRentalUserPanel;

import javax.swing.*;

public class LoginController {
    public static boolean authenticate(String username, String password) {
//        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
//        try (Connection con = DatabaseConnection.getConnection();
//             PreparedStatement stmt = con.prepareStatement(query)) {
//            stmt.setString(1, username);
//            stmt.setString(2, password);
//            ResultSet rs = stmt.executeQuery();
//            return rs.next();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
        return false;
    }

    public static void trylogin(String username, String password){
        if(username.equals("Eren") && password.equals("blackstone")){
            CarRentalUserPanel.start();
        }else{
            JOptionPane.showMessageDialog(
            null,
                "Invalid credentials!",
                "Info",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
