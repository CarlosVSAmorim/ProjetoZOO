package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List; // Mantido caso populateAnimalsComboBox/populateCuidadoresComboBox ainda sejam usados em outro contexto

public class CuidadorView extends JFrame {
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JPanel contentPanel; // Mantido, embora não usado diretamente para formulários agora
    private JScrollPane scrollPane;
    private JTextArea displayArea;

    // Botões principais
    private JButton btnCreate;
    private JButton btnEdit;
    private JButton btnDelete;
    // Removidos: private JButton btnList;
    // Removidos: private JButton btnSearch;
    // Removidos: private JButton btnAssignAnimal;
    // Removidos: private JButton btnUnassignAnimal;
    private JButton btnBackToMenu;
    private JButton btnReport;

    // Componentes para formulários (mantidos, pois os formulários ainda podem ser exibidos via JOptionPane)
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextField txtEspecialidade;
    private JTextField txtSalario;
    private JTextField txtDataContratacao;
    private JTextField txtSearch; // Mantido, pode ser usado para busca interna no controller
    private JComboBox<String> cmbAnimais; // Mantido, pode ser usado para atribuição interna no controller
    private JComboBox<String> cmbCuidadores; // Mantido, pode ser usado para atribuição interna no controller

    public CuidadorView() {
        initializeComponents();
        setupLayout();
        setupFrame();
    }

    private void initializeComponents() {
        mainPanel = new JPanel(new BorderLayout());

        buttonPanel = new JPanel(new FlowLayout());
        btnCreate = new JButton("Criar Cuidador");
        btnEdit = new JButton("Editar Cuidador");
        btnDelete = new JButton("Excluir Cuidador");
        // Removidos: btnList = new JButton("Listar Cuidadores");
        // Removidos: btnSearch = new JButton("Buscar Cuidador");
        // Removidos: btnAssignAnimal = new JButton("Atribuir Animal");
        // Removidos: btnUnassignAnimal = new JButton("Desatribuir Animal");
        btnBackToMenu = new JButton("Voltar ao Menu");
        btnReport = new JButton("Gerar Relatório");

        displayArea = new JTextArea(20, 50);
        displayArea.setEditable(false);
        displayArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        scrollPane = new JScrollPane(displayArea);

        contentPanel = new JPanel(new GridBagLayout());

        txtId = new JTextField(10);
        txtNome = new JTextField(20);
        txtCpf = new JTextField(15);
        txtTelefone = new JTextField(15);
        txtEmail = new JTextField(25);
        txtEspecialidade = new JTextField(20);
        txtSalario = new JTextField(10);
        txtDataContratacao = new JTextField(10);
        txtSearch = new JTextField(20);

        cmbAnimais = new JComboBox<>();
        cmbCuidadores = new JComboBox<>();

        txtId.setEditable(false);
        txtDataContratacao.setToolTipText("Formato: dd/MM/yyyy");
        txtCpf.setToolTipText("Formato: 000.000.000-00");
        txtSalario.setToolTipText("Formato: 0000.00");
    }

    private void setupLayout() {
        buttonPanel.add(btnCreate);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        // Removidos: buttonPanel.add(btnList);
        // Removidos: buttonPanel.add(btnSearch);
        // Removidos: buttonPanel.add(btnAssignAnimal);
        // Removidos: buttonPanel.add(btnUnassignAnimal);
        buttonPanel.add(btnReport);
        buttonPanel.add(btnBackToMenu);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void setupFrame() {
        setTitle("Gestão de Cuidadores");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    // Métodos para exibir formulários (mantidos, pois são chamados pelo controller)
    public void showCreateForm() {
        clearForm();
        JPanel formPanel = createFormPanel("Criar Novo Cuidador", false);
        // showFormDialog(formPanel, "Criar Cuidador", "create"); // Removido, lógica no controller
    }

    public void showEditForm() {
        // String id = JOptionPane.showInputDialog(this, "Digite o ID do cuidador:"); // Removido, lógica no controller
        // if (id != null && !id.trim().isEmpty()) {
        //     txtId.setText(id);
        //     JPanel formPanel = createFormPanel("Editar Cuidador", true);
        //     // showFormDialog(formPanel, "Editar Cuidador", "edit"); // Removido, lógica no controller
        // }
    }

    // Removidos: public void showSearchForm() { ... }
    // Removidos: public void showAssignAnimalForm() { ... }
    // Removidos: public void showUnassignAnimalForm() { ... }

    // Este método é chamado pelo controller para obter o painel do formulário
    public JPanel createFormPanel(String title, boolean isEdit) {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(new JLabel(title), gbc);
        gbc.gridwidth = 1;

        if (isEdit) {
            gbc.gridx = 0; gbc.gridy = 1;
            formPanel.add(new JLabel("ID:"), gbc);
            gbc.gridx = 1;
            formPanel.add(txtId, gbc);
        }

        gbc.gridx = 0; gbc.gridy = isEdit ? 2 : 1;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = isEdit ? 3 : 2;
        formPanel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtCpf, gbc);

        gbc.gridx = 0; gbc.gridy = isEdit ? 4 : 3;
        formPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtTelefone, gbc);

        gbc.gridx = 0; gbc.gridy = isEdit ? 5 : 4;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = isEdit ? 6 : 5;
        formPanel.add(new JLabel("Especialidade:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtEspecialidade, gbc);

        gbc.gridx = 0; gbc.gridy = isEdit ? 7 : 6;
        formPanel.add(new JLabel("Salário:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtSalario, gbc);

        gbc.gridx = 0; gbc.gridy = isEdit ? 8 : 7;
        formPanel.add(new JLabel("Data Contratação (DD/MM/YYYY):"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDataContratacao, gbc);

        return formPanel;
    }


    public void clearForm() {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtEspecialidade.setText("");
        txtSalario.setText("");
        txtDataContratacao.setText("");
    }

    public void displayText(String text) {
        displayArea.setText(text);
    }

    public void appendText(String text) {
        displayArea.append(text);
    }

    public void clearDisplay() {
        displayArea.setText("");
    }

    // Métodos para popular ComboBoxes (mantidos, podem ser úteis para futuras implementações)
    public void populateAnimalsComboBox(List<String> animals) {
        cmbAnimais.removeAllItems();
        for (String animal : animals) {
            cmbAnimais.addItem(animal);
        }
    }

    public void populateCuidadoresComboBox(List<String> cuidadores) {
        cmbCuidadores.removeAllItems();
        for (String cuidador : cuidadores) {
            cmbCuidadores.addItem(cuidador);
        }
    }

    // Getters para dados do formulário (mantidos, pois o controller os usa)
    public String getId() { return txtId.getText().trim(); }
    public String getNome() { return txtNome.getText().trim(); }
    public String getCpf() { return txtCpf.getText().trim(); }
    public String getTelefone() { return txtTelefone.getText().trim(); }
    public String getEmail() { return txtEmail.getText().trim(); }
    public String getEspecialidade() { return txtEspecialidade.getText().trim(); }
    public String getSalario() { return txtSalario.getText().trim(); }
    public String getDataContratacao() { return txtDataContratacao.getText().trim(); }
    public String getSearchText() { return txtSearch.getText().trim(); }
    public String getSelectedAnimal() { return (String) cmbAnimais.getSelectedItem(); }
    public String getSelectedCuidador() { return (String) cmbCuidadores.getSelectedItem(); }


    public void setFormData(String id, String nome, String cpf, String telefone,
                            String email, String especialidade, String salario, String dataContratacao) {
        txtId.setText(id);
        txtNome.setText(nome);
        txtCpf.setText(cpf);
        txtTelefone.setText(telefone);
        txtEmail.setText(email);
        txtEspecialidade.setText(especialidade);
        txtSalario.setText(salario);
        txtDataContratacao.setText(dataContratacao);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public int showConfirmDialog(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Confirmação",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    public void addBackToMenuListener(ActionListener listener) {
        btnBackToMenu.addActionListener(listener);
    }

    public void addCreateCuidadorListener(ActionListener listener) {
        btnCreate.addActionListener(listener);
    }

    public void addEditCuidadorListener(ActionListener listener) {
        btnEdit.addActionListener(listener);
    }

    public void addDeleteCuidadorListener(ActionListener listener) {
        btnDelete.addActionListener(listener);
    }

    public void addReportListener(ActionListener listener) {
        btnReport.addActionListener(listener);
    }

    public void clearFields() {
    }

    public void showSearchForm() {
    }
}
