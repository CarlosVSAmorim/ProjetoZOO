package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuView {
    private JFrame frame;
    private JButton animalButton;
    private JButton userButton;
    private JButton cuidadorButton; // Renomeado para camelCase
    private JButton reportButton;
    private JButton logoutButton;
    private JButton exitButton; // Novo botão de saída

    public MenuView() {
        frame = new JFrame("Menu Principal - Zoológico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400); // Aumentado para acomodar o novo botão de saída
        frame.setLayout(new GridLayout(6, 1, 10, 10)); // Alterado para 6 linhas

        // Adicionar padding
        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        animalButton = new JButton("Gerenciar Animais");
        userButton = new JButton("Gerenciar Usuários");
        cuidadorButton = new JButton("Gerenciar Cuidadores"); // Renomeado
        logoutButton = new JButton("Logout");
        exitButton = new JButton("Sair"); // Novo botão

        // Estilizar botões
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        animalButton.setFont(buttonFont);
        userButton.setFont(buttonFont);
        cuidadorButton.setFont(buttonFont); // Aplicar fonte ao novo botão
        logoutButton.setFont(buttonFont);
        exitButton.setFont(buttonFont); // Aplicar fonte ao novo botão

        mainPanel.add(animalButton);
        mainPanel.add(userButton);
        mainPanel.add(cuidadorButton); // Adicionar o novo botão
        mainPanel.add(logoutButton);
        mainPanel.add(exitButton); // Adicionar o botão de saída

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void close() {
        frame.dispose();
    }

    public void addAnimalButtonListener(ActionListener listener) {
        animalButton.addActionListener(listener);
    }

    public void addUserButtonListener(ActionListener listener) {
        userButton.addActionListener(listener);
    }

    public void addCuidadorButtonListener(ActionListener listener) { // Renomeado
        cuidadorButton.addActionListener(listener);
    }

    public void addReportButtonListener(ActionListener listener) {
        reportButton.addActionListener(listener);
    }

    public void addLogoutButtonListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }

    // Novo método para adicionar listener ao botão de saída
    public void addExitListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    // Métodos para exibir mensagens e diálogos de confirmação
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public int showConfirmDialog(String message) {
        return JOptionPane.showConfirmDialog(frame, message, "Confirmação",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
}