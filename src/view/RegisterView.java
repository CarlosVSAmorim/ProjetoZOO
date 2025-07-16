package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegisterView {
    public JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;

    public RegisterView() {
        frame = new JFrame("Registrar - Sistema Zoológico");
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

        registerButton = new JButton("Registrar");
        backButton = new JButton("Voltar");

        // Estilizar botões
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        registerButton.setFont(buttonFont);
        backButton.setFont(buttonFont);

        mainPanel.add(registerButton);
        mainPanel.add(backButton);

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

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}