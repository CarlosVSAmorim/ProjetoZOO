package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuView {
    private JFrame frame;
    private JButton animalButton;
    private JButton userButton;
    private JButton CuidadorButton; // Novo botão para cuidadores
    private JButton reportButton;
    private JButton logoutButton;

    public MenuView() {
        frame = new JFrame("Menu Principal - Zoológico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350); // Aumentado para acomodar o novo botão
        frame.setLayout(new GridLayout(5, 1, 10, 10)); // Alterado para 5 linhas

        // Adicionar padding
        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        animalButton = new JButton("Gerenciar Animais");
        userButton = new JButton("Gerenciar Usuários");
        CuidadorButton = new JButton("Gerenciar Cuidadores"); // Novo botão
        reportButton = new JButton("Relatórios");
        logoutButton = new JButton("Logout");

        // Estilizar botões
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        animalButton.setFont(buttonFont);
        userButton.setFont(buttonFont);
        CuidadorButton.setFont(buttonFont); // Aplicar fonte ao novo botão
        reportButton.setFont(buttonFont);
        logoutButton.setFont(buttonFont);

        mainPanel.add(animalButton);
        mainPanel.add(userButton);
        mainPanel.add(CuidadorButton); // Adicionar o novo botão
        mainPanel.add(reportButton);
        mainPanel.add(logoutButton);

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

    public void addCuidadorButtonListener(ActionListener listener) {
        CuidadorButton.addActionListener(listener);
    }

    public void addReportButtonListener(ActionListener listener) {
        reportButton.addActionListener(listener);
    }

    public void addLogoutButtonListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }
}