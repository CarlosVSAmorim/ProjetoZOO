package controller;

import model.CuidadorModel;
import view.CuidadorView;
import view.MenuView;
import view.ReportView;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class CuidadorController {
    private CuidadorModel model;
    private CuidadorView cuidadorView;
    private MenuView menuView;
    private ReportView reportView;

    public CuidadorController(CuidadorModel model, CuidadorView cuidadorView, ReportView reportView) {
        this.model = model;
        this.cuidadorView = cuidadorView;
        this.reportView = reportView;
        setupListeners();
    }

    public void setMenuView(MenuView menuView) {
        this.menuView = menuView;
    }

    private void setupListeners() {
        cuidadorView.addCreateCuidadorListener(e -> createCuidador());
        cuidadorView.addEditCuidadorListener(e -> editCuidador());
        cuidadorView.addDeleteCuidadorListener(e -> deleteCuidador());
        cuidadorView.addReportListener(e -> generateReport());
        cuidadorView.addBackToMenuListener(e -> {
            cuidadorView.setVisible(false);
            if (menuView != null) {
                menuView.setVisible(true);
            }
        });
    }

    public void createCuidador() {
        cuidadorView.clearForm();
        JPanel formPanel = cuidadorView.createFormPanel("Criar Novo Cuidador", false);
        int result = JOptionPane.showConfirmDialog(cuidadorView, formPanel, "Criar Cuidador",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            addCuidadorLogic();
        }
    }

    private void addCuidadorLogic() {
        String name = cuidadorView.getNome();
        String cpf = cuidadorView.getCpf();
        String phone = cuidadorView.getTelefone();
        String email = cuidadorView.getEmail();
        String specialization = cuidadorView.getEspecialidade();
        String hireDateStr = cuidadorView.getDataContratacao();
        String salaryStr = cuidadorView.getSalario();

        // Validações
        if (name.trim().isEmpty() || cpf.trim().isEmpty() || hireDateStr.trim().isEmpty()) {
            cuidadorView.showError("Nome, CPF e Data de Contratação são obrigatórios!");
            return;
        }

        if (!isValidCPF(cpf)) {
            cuidadorView.showError("CPF deve conter 11 dígitos numéricos!");
            return;
        }

        try {
            if (model.cpfExists(cpf)) {
                cuidadorView.showError("CPF já cadastrado!");
                return;
            }
        } catch (SQLException e) {
            cuidadorView.showError("Erro ao verificar CPF: " + e.getMessage());
            return;
        }

        LocalDate hireDate = null;
        try {
            hireDate = LocalDate.parse(hireDateStr, CuidadorModel.DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            cuidadorView.showError("Data de Contratação deve estar no formato DD/MM/YYYY!");
            return;
        }
        if (!isValidDate(hireDate)) {
            cuidadorView.showError("Data de Contratação não pode ser futura!");
            return;
        }

        if (!email.trim().isEmpty() && !isValidEmail(email)) {
            cuidadorView.showError("Email inválido!");
            return;
        }

        double salary = 0.0;
        if (!salaryStr.trim().isEmpty()) {
            try {
                salary = Double.parseDouble(salaryStr.replace(",", "."));
            } catch (NumberFormatException e) {
                cuidadorView.showError("Salário deve ser um número válido!");
                return;
            }
        }

        try {
            CuidadorModel newCuidador = new CuidadorModel(name, cpf, phone, email, specialization, salary, hireDate);
            model.addCuidador(newCuidador);
            cuidadorView.showMessage("Cuidador adicionado com sucesso!");
            updateCuidadorArea();
            cuidadorView.clearFields();
        } catch (SQLException e) {
            cuidadorView.showError("Erro ao adicionar cuidador: " + e.getMessage());
        }
    }

    public void editCuidador() {
        String idStr = JOptionPane.showInputDialog(cuidadorView, "Digite o ID do cuidador a ser editado:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            CuidadorModel existingCuidador = model.getCuidadorById(id);
            if (existingCuidador == null) {
                cuidadorView.showError("Cuidador com ID " + id + " não encontrado.");
                return;
            }

            cuidadorView.setFormData(
                    String.valueOf(existingCuidador.getId()),
                    existingCuidador.getNome(),
                    existingCuidador.getCpf(),
                    existingCuidador.getTelefone(),
                    existingCuidador.getEmail(),
                    existingCuidador.getEspecialidade(),
                    String.valueOf(existingCuidador.getSalario()),
                    existingCuidador.getDataContratacaoFormatada()
            );

            JPanel formPanel = cuidadorView.createFormPanel("Editar Cuidador", true);
            int result = JOptionPane.showConfirmDialog(cuidadorView, formPanel, "Editar Cuidador",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                updateCuidadorLogic(id);
            }
        } catch (NumberFormatException ex) {
            cuidadorView.showError("ID deve ser um número válido!");
        } catch (SQLException e) {
            cuidadorView.showError("Erro ao buscar cuidador: " + e.getMessage());
        }
    }

    private void updateCuidadorLogic(int id) {
        String name = cuidadorView.getNome();
        String cpf = cuidadorView.getCpf();
        String phone = cuidadorView.getTelefone();
        String email = cuidadorView.getEmail();
        String specialization = cuidadorView.getEspecialidade();
        String hireDateStr = cuidadorView.getDataContratacao();
        String salaryStr = cuidadorView.getSalario();

        // Validações
        if (name.trim().isEmpty() || cpf.trim().isEmpty() || hireDateStr.trim().isEmpty()) {
            cuidadorView.showError("Nome, CPF e Data de Contratação são obrigatórios!");
            return;
        }

        if (!isValidCPF(cpf)) {
            cuidadorView.showError("CPF deve conter 11 dígitos numéricos!");
            return;
        }

        try {
            if (model.cpfExistsForOtherCuidador(cpf, id)) {
                cuidadorView.showError("CPF já cadastrado para outro cuidador!");
                return;
            }
        } catch (SQLException e) {
            cuidadorView.showError("Erro ao verificar CPF: " + e.getMessage());
            return;
        }

        LocalDate hireDate = null;
        try {
            hireDate = LocalDate.parse(hireDateStr, CuidadorModel.DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            cuidadorView.showError("Data de Contratação deve estar no formato DD/MM/YYYY!");
            return;
        }
        if (!isValidDate(hireDate)) {
            cuidadorView.showError("Data de Contratação não pode ser futura!");
            return;
        }

        if (!email.trim().isEmpty() && !isValidEmail(email)) {
            cuidadorView.showError("Email inválido!");
            return;
        }

        double salary = 0.0;
        if (!salaryStr.trim().isEmpty()) {
            try {
                salary = Double.parseDouble(salaryStr.replace(",", "."));
            } catch (NumberFormatException e) {
                cuidadorView.showError("Salário deve ser um número válido!");
                return;
            }
        }

        try {
            CuidadorModel updatedCuidador = new CuidadorModel(id, name, cpf, phone, email, specialization, salary, hireDate);
            model.updateCuidador(updatedCuidador);
            cuidadorView.showMessage("Cuidador atualizado com sucesso!");
            updateCuidadorArea();
            cuidadorView.clearFields();
        } catch (SQLException e) {
            cuidadorView.showError("Erro ao atualizar cuidador: " + e.getMessage());
        }
    }

    public void deleteCuidador() {
        String idStr = JOptionPane.showInputDialog(cuidadorView, "Digite o ID do cuidador a ser excluído:");
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            int confirm = cuidadorView.showConfirmDialog("Tem certeza que deseja remover o cuidador com ID " + id + "?");

            if (confirm == JOptionPane.YES_OPTION) {
                model.removeCuidador(id);
                cuidadorView.showMessage("Cuidador removido com sucesso!");
                updateCuidadorArea();
                cuidadorView.clearFields();
            }
        } catch (NumberFormatException ex) {
            cuidadorView.showError("ID deve ser um número válido!");
        } catch (SQLException e) {
            cuidadorView.showError("Erro ao remover cuidador: " + e.getMessage());
        }
    }

    public void listCuidadors() {
        updateCuidadorArea();
    }

    public void searchCuidador() {
        // Este método não tem mais um botão associado na view, mas pode ser chamado internamente se necessário.
        // cuidadorView.showSearchForm(); // Removido, pois o botão foi removido
        String searchTerm = cuidadorView.getSearchText();
        if (searchTerm.isEmpty()) {
            cuidadorView.showMessage("Digite um termo para buscar.");
            return;
        }
        cuidadorView.showMessage("Buscando por: '" + searchTerm + "' (Funcionalidade de busca não implementada no model).");
    }

    public void assignAnimalToCuidador() {
        // Este método não tem mais um botão associado na view, mas pode ser chamado internamente se necessário.
        // cuidadorView.showAssignAnimalForm(); // Removido, pois o botão foi removido
        String selectedCuidador = cuidadorView.getSelectedCuidador();
        String selectedAnimal = cuidadorView.getSelectedAnimal();

        if (selectedCuidador == null || selectedAnimal == null) {
            cuidadorView.showError("Selecione um cuidador e um animal.");
            return;
        }
        cuidadorView.showMessage("Animal '" + selectedAnimal + "' atribuído ao cuidador '" + selectedCuidador + "' (Simulado).");
    }

    public void unassignAnimalFromCuidador() {
        // Este método não tem mais um botão associado na view, mas pode ser chamado internamente se necessário.
        // cuidadorView.showUnassignAnimalForm(); // Removido, pois o botão foi removido
        String selectedCuidador = cuidadorView.getSelectedCuidador();
        String selectedAnimal = cuidadorView.getSelectedAnimal();

        if (selectedCuidador == null || selectedAnimal == null) {
            cuidadorView.showError("Selecione um cuidador e um animal.");
            return;
        }
        cuidadorView.showMessage("Animal '" + selectedAnimal + "' desatribuído do cuidador '" + selectedCuidador + "' (Simulado).");
    }

    public void generateReport() {
        String reportContent = getReportAsString();
        if (!reportContent.isEmpty()) {
            reportView.setReport(reportContent);
            reportView.setVisible(true);
        } else {
            reportView.showMessage("Nenhum cuidador encontrado para gerar o relatório.");
        }
    }

    public String getReportAsString() {
        StringBuilder report = new StringBuilder();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        try {
            List<CuidadorModel> cuidadores = model.getAllCuidadors();
            if (!cuidadores.isEmpty()) {
                report.append("=== RELATÓRIO DE CUIDADORES ===\n\n");
                int count = 0;
                double totalSalary = 0.0;

                for (CuidadorModel cuidador : cuidadores) {
                    count++;
                    double salary = cuidador.getSalario();
                    totalSalary += salary;

                    report.append("ID: ").append(cuidador.getId()).append("\n");
                    report.append("Nome: ").append(cuidador.getNome()).append("\n");
                    report.append("CPF: ").append(cuidador.getCpfFormatado()).append("\n"); // Adicionado CPF
                    report.append("Telefone: ").append(cuidador.getTelefoneFormatado()).append("\n"); // Adicionado Telefone
                    report.append("Email: ").append(cuidador.getEmail()).append("\n");
                    report.append("Especialização: ").append(cuidador.getEspecialidade()).append("\n");
                    report.append("Data de Contratação: ").append(cuidador.getDataContratacaoFormatada()).append("\n");
                    report.append("Salário: ").append(currencyFormat.format(salary)).append("\n");
                    report.append("----------------------------------------\n");
                }

                report.append("\n=== RESUMO ===\n");
                report.append("Total de Cuidadores: ").append(count).append("\n");
                report.append("Folha de Pagamento Total: ").append(currencyFormat.format(totalSalary));
            }
        } catch (SQLException e) {
            cuidadorView.showError("Erro ao gerar relatório de cuidadores: " + e.getMessage());
            return "Erro ao gerar relatório.";
        }
        return report.toString();
    }

    public void updateCuidadorArea() {
        StringBuilder cuidadoresText = new StringBuilder();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        try {
            List<CuidadorModel> cuidadores = model.getAllCuidadors();
            if (!cuidadores.isEmpty()) {
                for (CuidadorModel cuidador : cuidadores) {
                    cuidadoresText.append("ID: ").append(cuidador.getId());
                    cuidadoresText.append(" | Nome: ").append(cuidador.getNome());
                    cuidadoresText.append(" | CPF: ").append(cuidador.getCpfFormatado()); // Adicionado CPF
                    cuidadoresText.append(" | Telefone: ").append(cuidador.getTelefoneFormatado()); // Adicionado Telefone
                    cuidadoresText.append(" | Email: ").append(cuidador.getEmail());
                    cuidadoresText.append(" | Especialização: ").append(cuidador.getEspecialidade());
                    cuidadoresText.append(" | Data Contratação: ").append(cuidador.getDataContratacaoFormatada());
                    cuidadoresText.append(" | Salário: ").append(currencyFormat.format(cuidador.getSalario()));
                    cuidadoresText.append("\n");
                }
                cuidadorView.displayText(cuidadoresText.toString());
            } else {
                cuidadorView.displayText("Nenhum cuidador cadastrado.");
            }
        } catch (SQLException e) {
            cuidadorView.showError("Erro ao carregar cuidadores: " + e.getMessage());
        }
    }

    private boolean isValidCPF(String cpf) {
        String cleanCpf = cpf.replaceAll("[^0-9]", "");
        return cleanCpf.matches("\\d{11}");
    }

    private boolean isValidDate(LocalDate date) {
        return date != null && !date.isAfter(LocalDate.now());
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
