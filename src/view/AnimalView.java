package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AnimalView {
    public JFrame frame;
    private JTextField nameField;
    private JTextField speciesField;
    private JTextField idField;
    private JButton addButton;
    private JButton updateButton;
    private JButton removeButton;
    private JButton reportButton;
    private JButton backButton;
    private JTextArea animalArea;

    public AnimalView() {
        frame = new JFrame("Gerenciamento de Animais");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Nome:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Espécie:"));
        speciesField = new JTextField();
        inputPanel.add(speciesField);

        inputPanel.add(new JLabel("ID (para Atualizar/Remover):"));
        idField = new JTextField();
        inputPanel.add(idField);

        addButton = new JButton("Adicionar Animal");
        updateButton = new JButton("Atualizar Animal");
        removeButton = new JButton("Remover Animal");
        reportButton = new JButton("Gerar Relatório");
        backButton = new JButton("Voltar ao Menu");

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(reportButton);
        buttonPanel.add(backButton);

        inputPanel.add(buttonPanel);

        animalArea = new JTextArea();
        animalArea.setEditable(false);
        animalArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(animalArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Animais Cadastrados"));

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public String getNameInput() {
        return nameField.getText();
    }

    public String getSpeciesInput() {
        return speciesField.getText();
    }

    public String getIdInput() {
        return idField.getText();
    }

    public void setAnimalArea(String animals) {
        animalArea.setText(animals);
    }

    public void clearFields() {
        nameField.setText("");
        speciesField.setText("");
        idField.setText("");
    }

    public void addAnimalListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addUpdateListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    public void addRemoveListener(ActionListener listener) {
        removeButton.addActionListener(listener);
    }

    public void addReportListener(ActionListener listener) {
        reportButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}