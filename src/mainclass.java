import View.LoginPanel;
import javax.swing.*;

public class mainclass {
    public static void main(String[] args) {
        startloginscreen();
    }

    public static void startloginscreen(){
        JFrame deneme = new JFrame();
        deneme.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        deneme.setSize(1920,1080);

        LoginPanel backgroundPanel = new LoginPanel(deneme);  // Pass JFrame to LoginPanel
        deneme.add(backgroundPanel);

        deneme.setVisible(true);
    }
}