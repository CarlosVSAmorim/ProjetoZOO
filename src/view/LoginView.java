package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginView() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new GridLayout(4, 2));

        frame.add(new JLabel("Usuário:"));
        usernameField = new JTextField();
        frame.add(usernameField);

        frame.add(new JLabel("Senha:"));
        passwordField = new JPasswordField();
        frame.add(passwordField);

        loginButton = new JButton("Login");
        frame.add(loginButton);

        registerButton = new JButton("Registrar");
        frame.add(registerButton);

        frame.setVisible(true);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void close() {
        frame.dispose();
    }
}