package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controller.LoginController;

public class LoginPanel extends JPanel {

    private Image backgroundImage;
    public LoginPanel(){

        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("resources/carwallpaper.png")).getImage();

        setLayout(new GridBagLayout());
        JPanel centerpanel = new JPanel(new GridLayout(8,1, 10, 10));
        centerpanel.setBackground(Color.LIGHT_GRAY);
        JLabel loginlabel = new JLabel("Welcome to");
        loginlabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginlabel.setVerticalAlignment(SwingConstants.BOTTOM);
        loginlabel.setFont(new Font("Arial", Font.BOLD, 40));

        JLabel loginlabel2 = new JLabel("Car Rental System");
        loginlabel2.setHorizontalAlignment(SwingConstants.CENTER);
        loginlabel2.setVerticalAlignment(SwingConstants.BOTTOM);
        loginlabel2.setFont(new Font("Arial", Font.BOLD, 40));
        centerpanel.add(loginlabel);
        centerpanel.add(loginlabel2);

        JPanel usernamepanel = new JPanel(new BorderLayout());
        usernamepanel.setBackground(Color.LIGHT_GRAY);
        JLabel usernamelabel = new JLabel("Username");
        usernamelabel.setHorizontalAlignment(SwingConstants.LEFT);
        usernamepanel.add(usernamelabel, BorderLayout.SOUTH);
        JTextField userfield = new JTextField();

        JPanel passwordpanel = new JPanel(new BorderLayout());
        passwordpanel.setBackground(Color.LIGHT_GRAY);
        JLabel passwordlabel = new JLabel("Password");
        passwordlabel.setHorizontalAlignment(SwingConstants.LEFT);
        passwordpanel.add(passwordlabel, BorderLayout.SOUTH);
        JTextField passwordfield = new JTextField();

        JButton loginButton = new JButton("Log In");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClicked(userfield.getText(), passwordfield.getText());
            }
        });

        centerpanel.add(usernamepanel);
        centerpanel.add(userfield);
        centerpanel.add(passwordpanel);
        centerpanel.add(passwordfield);
        centerpanel.add(new JLabel());
        centerpanel.add(loginButton);
        centerpanel.setPreferredSize(new Dimension(600,400));

        JPanel outerpanel = new JPanel(new GridBagLayout());
        outerpanel.setPreferredSize(new Dimension(700, 500));
        outerpanel.setBackground(Color.LIGHT_GRAY);
        outerpanel.setBounds(600, 200, 680, 500);
        outerpanel.add(centerpanel);

        add(outerpanel);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public void buttonClicked(String username, String password){
        LoginController.trylogin(username, password);
    }
}
