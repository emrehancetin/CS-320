package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    public LoginScreen() {
        // Çerçeve ayarları
        setTitle("Login Page");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel ve bileşenler
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel userLabel = new JLabel("Kullanıcı Adı:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Şifre:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Giriş");

        // Bileşenleri panele ekle
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(new JLabel()); // Boşluk için
        panel.add(loginButton);

        // Çerçeveye panele ekle
        add(panel);

        // Login butonuna action ekle
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                // Basit doğrulama
                if ("admin".equals(username) && "1234".equals(password)) {
                    JOptionPane.showMessageDialog(null, "Giriş Başarılı!");
                } else {
                    JOptionPane.showMessageDialog(null, "Hatalı Kullanıcı Adı veya Şifre.");
                }
            }
        });
    }
}


