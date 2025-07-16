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
    private JTextArea animalArea;

    public AnimalView() {
        frame = new JFrame("Zoológico - Gerenciamento de Animais");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

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

        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(removeButton);
        inputPanel.add(reportButton);

        animalArea = new JTextArea();
        animalArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(animalArea);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
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

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}