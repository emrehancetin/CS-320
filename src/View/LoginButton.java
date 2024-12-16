package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginButton implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(
                null,
                "Düğmeye bastınız!",
                "Bilgilendirme",
                JOptionPane.INFORMATION_MESSAGE
        );
        //buttonClicked(usernamelabel.getText(), passwordlabel.getText());
    }
}
