package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegisterView {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;

    public RegisterView() {
        frame = new JFrame("Registrar Usuário");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new GridLayout(4, 2));

        frame.add(new JLabel("Usuário:"));
        usernameField = new JTextField();
        frame.add(usernameField);

        frame.add(new JLabel("Senha:"));
        passwordField = new JPasswordField();
        frame.add(passwordField);

        registerButton = new JButton("Registrar");
        frame.add(registerButton);

        backButton = new JButton("Voltar");
        frame.add(backButton);

        frame.setVisible(true);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void close() {
        frame.dispose();
    }
}