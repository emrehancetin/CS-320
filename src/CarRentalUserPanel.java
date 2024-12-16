import View.LoginPanel;
import View.LoginScreen;

import javax.swing.*;

public class CarRentalUserPanel {
    public static void showuserGUI(){
        LoginScreen giris = new LoginScreen();
        JFrame deneme = new JFrame();
        deneme.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        giris.setSize(1000,600);
        deneme.setSize(1920,1080);

        LoginPanel backgroundPanel = new LoginPanel();
        deneme.add(backgroundPanel);

        deneme.setVisible(true);
    }
}
