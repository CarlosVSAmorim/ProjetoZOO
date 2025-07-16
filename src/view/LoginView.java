package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView {
    public JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton; // Novo botão de saída

    public LoginView() {
        frame = new JFrame("Login - Sistema Zoológico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 250); // Aumentado para acomodar o novo botão
        frame.setLayout(new GridLayout(4, 2, 10, 10)); // Alterado para 4 linhas

        // Adicionar padding
        JPanel mainPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        mainPanel.add(new JLabel("Usuário:"));
        usernameField = new JTextField();
        mainPanel.add(usernameField);

        mainPanel.add(new JLabel("Senha:"));
        passwordField = new JPasswordField();
        mainPanel.add(passwordField);

        loginButton = new JButton("Login");
        exitButton = new JButton("Sair"); // Novo botão

        // Estilizar botões
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        loginButton.setFont(buttonFont);
        exitButton.setFont(buttonFont); // Aplicar fonte ao novo botão

        mainPanel.add(loginButton);
        mainPanel.add(exitButton); // Adicionar o botão de saída

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


    // Novo método para adicionar listener ao botão de saída
    public void addExitListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    // Métodos para exibir mensagens
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}