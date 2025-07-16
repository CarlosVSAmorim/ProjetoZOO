package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserView {
    public JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField idField;
    private JButton addButton;
    private JButton updateButton;
    private JButton removeButton;
    private JButton backButton;
    private JTextArea userArea;

    public UserView() {
        frame = new JFrame("Gerenciamento de Usuários");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Usuário:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);

        inputPanel.add(new JLabel("Senha:"));
        passwordField = new JPasswordField();
        inputPanel.add(passwordField);

        inputPanel.add(new JLabel("ID (para Atualizar/Remover):"));
        idField = new JTextField();
        inputPanel.add(idField);

        addButton = new JButton("Adicionar Usuário");
        updateButton = new JButton("Atualizar Usuário");
        removeButton = new JButton("Remover Usuário");
        backButton = new JButton("Voltar ao Menu");

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(backButton);

        inputPanel.add(buttonPanel);

        userArea = new JTextArea();
        userArea.setEditable(false);
        userArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(userArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Usuários Cadastrados"));

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
        if (visible) {
            // Atualizar a área de usuários quando a tela for exibida
            updateUserArea();
        }
    }

    public String getUsernameInput() {
        return usernameField.getText();
    }

    public String getPasswordInput() {
        return new String(passwordField.getPassword());
    }

    public String getIdInput() {
        return idField.getText();
    }

    public void setUserArea(String users) {
        userArea.setText(users);
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        idField.setText("");
    }

    public void addUserListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addUpdateListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    public void addRemoveListener(ActionListener listener) {
        removeButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    private void updateUserArea() {
        // Este método será chamado pelo controller
    }
}