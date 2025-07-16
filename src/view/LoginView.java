package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView {
    public JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginView() {
        frame = new JFrame("Login - Sistema Zoológico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLayout(new GridLayout(3, 2, 10, 10));

        // Adicionar padding
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        mainPanel.add(new JLabel("Usuário:"));
        usernameField = new JTextField();
        mainPanel.add(usernameField);

        mainPanel.add(new JLabel("Senha:"));
        passwordField = new JPasswordField();
        mainPanel.add(passwordField);

        loginButton = new JButton("Login");
        registerButton = new JButton("Registrar");

        // Estilizar botões
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        loginButton.setFont(buttonFont);
        registerButton.setFont(buttonFont);

        mainPanel.add(loginButton);
        mainPanel.add(registerButton);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }
}