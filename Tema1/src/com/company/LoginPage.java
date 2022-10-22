package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Utilizatorul se poate autentifica din interfata grafica
public class LoginPage extends JFrame implements ActionListener {
    private final Panel panel;
    private final JLabel label1;
    private final JLabel label2;
    private final JLabel message;
    private final JTextField email;
    private final JButton button;
    private final JPasswordField password;
    public List<Account> accounts;

    LoginPage(List<Account> possibleAccounts) {
        setLayout(new FlowLayout());
        accounts = possibleAccounts;
        //Email label
        label1 = new JLabel();
        label1.setText("email:");
        email = new JTextField();
        //Password label
        label2 = new JLabel();
        label2.setText("password:");
        password = new JPasswordField();
        //Login
        button = new JButton("login");
        panel = new Panel(new GridLayout(3, 1));
        panel.add(label1);
        panel.add(email);
        panel.add(label2);
        panel.add(password);
        message = new JLabel();
        panel.add(message);
        panel.add(button);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        button.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Login Page");
        setSize(450, 350);
        setVisible(true);
        show();
        pack();
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String Email = email.getText();
        String Password = password.getText();
        for (Account obj : accounts) {
            if (Email.trim().equals(obj.information.getCredentials().getEmail()) && Password.trim().equals(obj.information.getCredentials().getPassword())) {
                message.setText("Hello " + obj.information.getName());
                new CharacterSelection(obj.characters);
                return;
            }
        }
        message.setText("Invalid credentials");
    }
}
