package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CuidadorView {
    public JFrame frame;
    private JTextField nameField;
    private JTextField cpfField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField specializationField;
    private JTextField hireDateField;
    private JTextField salaryField;
    private JTextField idField;
    private JButton addButton;
    private JButton updateButton;
    private JButton removeButton;
    private JButton reportButton;
    private JButton backButton;
    private JTextArea CuidadorArea;

    public CuidadorView() {
        frame = new JFrame("Gerenciamento de Cuidadores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(10, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Nome:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        inputPanel.add(cpfField);

        inputPanel.add(new JLabel("Telefone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Especialização:"));
        specializationField = new JTextField();
        inputPanel.add(specializationField);

        inputPanel.add(new JLabel("Data de Contratação (YYYY-MM-DD):"));
        hireDateField = new JTextField();
        inputPanel.add(hireDateField);

        inputPanel.add(new JLabel("Salário:"));
        salaryField = new JTextField();
        inputPanel.add(salaryField);

        inputPanel.add(new JLabel("ID (para Atualizar/Remover):"));
        idField = new JTextField();
        inputPanel.add(idField);

        addButton = new JButton("Adicionar Cuidador");
        updateButton = new JButton("Atualizar Cuidador");
        removeButton = new JButton("Remover Cuidador");
        reportButton = new JButton("Gerar Relatório");
        backButton = new JButton("Voltar ao Menu");

        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(reportButton);
        buttonPanel.add(backButton);

        inputPanel.add(buttonPanel);

        CuidadorArea = new JTextArea();
        CuidadorArea.setEditable(false);
        CuidadorArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(CuidadorArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Cuidadores Cadastrados"));

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

    public String getCpfInput() {
        return cpfField.getText();
    }

    public String getPhoneInput() {
        return phoneField.getText();
    }

    public String getEmailInput() {
        return emailField.getText();
    }

    public String getSpecializationInput() {
        return specializationField.getText();
    }

    public String getHireDateInput() {
        return hireDateField.getText();
    }

    public String getSalaryInput() {
        return salaryField.getText();
    }

    public String getIdInput() {
        return idField.getText();
    }

    public void setCuidadorArea(String Cuidadors) {
        CuidadorArea.setText(Cuidadors);
    }

    public void clearFields() {
        nameField.setText("");
        cpfField.setText("");
        phoneField.setText("");
        emailField.setText("");
        specializationField.setText("");
        hireDateField.setText("");
        salaryField.setText("");
        idField.setText("");
    }

    public void fillFieldsForUpdate(String name, String cpf, String phone, String email,
                                    String specialization, String hireDate, String salary) {
        nameField.setText(name);
        cpfField.setText(cpf);
        phoneField.setText(phone);
        emailField.setText(email);
        specializationField.setText(specialization);
        hireDateField.setText(hireDate);
        salaryField.setText(salary);
    }

    public void addCuidadorListener(ActionListener listener) {
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