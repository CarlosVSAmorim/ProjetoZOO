package controller;

import model.CuidadorModel;
import view.CuidadorView;
import view.MenuView;
import view.ReportView;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public class CuidadorController {
    private CuidadorModel model;
    private CuidadorView CuidadorView;
    private MenuView menuView;
    private ReportView reportView;

    public CuidadorController(CuidadorModel model, CuidadorView CuidadorView, ReportView reportView) {
        this.model = model;
        this.CuidadorView = CuidadorView;
        this.reportView = reportView;
        setupListeners();
    }

    public void setMenuView(MenuView menuView) {
        this.menuView = menuView;
    }

    private void setupListeners() {
        CuidadorView.addCuidadorListener(e -> addCuidador());
        CuidadorView.addUpdateListener(e -> updateCuidador());
        CuidadorView.addRemoveListener(e -> removeCuidador());
        CuidadorView.addReportListener(e -> generateReport());
        CuidadorView.addBackListener(e -> {
            CuidadorView.setVisible(false);
            if (menuView != null) {
                menuView.setVisible(true);
            }
        });
    }

    private void addCuidador() {
        String name = CuidadorView.getNameInput();
        String cpf = CuidadorView.getCpfInput();
        String phone = CuidadorView.getPhoneInput();
        String email = CuidadorView.getEmailInput();
        String specialization = CuidadorView.getSpecializationInput();
        String hireDate = CuidadorView.getHireDateInput();
        String salaryStr = CuidadorView.getSalaryInput();

        // Validações
        if (name.trim().isEmpty() || cpf.trim().isEmpty() || hireDate.trim().isEmpty()) {
            JOptionPane.showMessageDialog(CuidadorView.frame,
                    "Nome, CPF e Data de Contratação são obrigatórios!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidCPF(cpf)) {
            JOptionPane.showMessageDialog(CuidadorView.frame,
                    "CPF deve conter 11 dígitos!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (model.cpfExists(cpf)) {
            JOptionPane.showMessageDialog(CuidadorView.frame,
                    "CPF já cadastrado!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidDate(hireDate)) {
            JOptionPane.showMessageDialog(CuidadorView.frame,
                    "Data deve estar no formato YYYY-MM-DD!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.trim().isEmpty() && !isValidEmail(email)) {
            JOptionPane.showMessageDialog(CuidadorView.frame,
                    "Email inválido!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double salary = 0.0;
        if (!salaryStr.trim().isEmpty()) {
            try {
                salary = Double.parseDouble(salaryStr.replace(",", "."));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(CuidadorView.frame,
                        "Salário deve ser um número válido!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            model.addCuidador(name, cpf, phone, email, specialization, hireDate, salary);
            updateCuidadorArea();
            CuidadorView.clearFields();
            JOptionPane.showMessageDialog(CuidadorView.frame, "Cuidador adicionado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(CuidadorView.frame,
                    "Erro ao adicionar cuidador: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCuidador() {
        try {
            int id = Integer.parseInt(CuidadorView.getIdInput());
            String name = CuidadorView.getNameInput();
            String cpf = CuidadorView.getCpfInput();
            String phone = CuidadorView.getPhoneInput();
            String email = CuidadorView.getEmailInput();
            String specialization = CuidadorView.getSpecializationInput();
            String hireDate = CuidadorView.getHireDateInput();
            String salaryStr = CuidadorView.getSalaryInput();

            // Validações
            if (name.trim().isEmpty() || cpf.trim().isEmpty() || hireDate.trim().isEmpty()) {
                JOptionPane.showMessageDialog(CuidadorView.frame,
                        "Nome, CPF e Data de Contratação são obrigatórios!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidCPF(cpf)) {
                JOptionPane.showMessageDialog(CuidadorView.frame,
                        "CPF deve conter 11 dígitos!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (model.cpfExistsForOtherCuidador(cpf, id)) {
                JOptionPane.showMessageDialog(CuidadorView.frame,
                        "CPF já cadastrado para outro cuidador!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidDate(hireDate)) {
                JOptionPane.showMessageDialog(CuidadorView.frame,
                        "Data deve estar no formato YYYY-MM-DD!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.trim().isEmpty() && !isValidEmail(email)) {
                JOptionPane.showMessageDialog(CuidadorView.frame,
                        "Email inválido!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double salary = 0.0;
            if (!salaryStr.trim().isEmpty()) {
                try {
                    salary = Double.parseDouble(salaryStr.replace(",", "."));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(CuidadorView.frame,
                            "Salário deve ser um número válido!",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            model.updateCuidador(id, name, cpf, phone, email, specialization, hireDate, salary);
            updateCuidadorArea();
            CuidadorView.clearFields();
            JOptionPane.showMessageDialog(CuidadorView.frame, "Cuidador atualizado com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(CuidadorView.frame,
                    "ID deve ser um número válido!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(CuidadorView.frame,
                    "Erro ao atualizar cuidador: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeCuidador() {
        try {
            int id = Integer.parseInt(CuidadorView.getIdInput());
            int confirm = JOptionPane.showConfirmDialog(CuidadorView.frame,
                    "Tem certeza que deseja remover o cuidador com ID " + id + "?",
                    "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                model.removeCuidador(id);
                updateCuidadorArea();
                CuidadorView.clearFields();
                JOptionPane.showMessageDialog(CuidadorView.frame, "Cuidador removido com sucesso!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(CuidadorView.frame,
                    "ID deve ser um número válido!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateReport() {
        StringBuilder report = new StringBuilder();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        try {
            ResultSet resultSet = model.generateCuidadorReport();
            if (resultSet != null) {
                report.append("=== RELATÓRIO DE CUIDADORES ===\n\n");
                int count = 0;
                double totalSalary = 0.0;

                while (resultSet.next()) {
                    count++;
                    double salary = resultSet.getDouble("salary");
                    totalSalary += salary;

                    report.append("ID: ").append(resultSet.getInt("id")).append("\n");
                    report.append("Nome: ").append(resultSet.getString("name")).append("\n");
                    report.append("CPF: ").append(resultSet.getString("cpf")).append("\n");
                    report.append("Telefone: ").append(resultSet.getString("phone")).append("\n");
                    report.append("Email: ").append(resultSet.getString("email")).append("\n");
                    report.append("Especialização: ").append(resultSet.getString("specialization")).append("\n");
                    report.append("Data de Contratação: ").append(resultSet.getString("hire_date")).append("\n");
                    report.append("Salário: ").append(currencyFormat.format(salary)).append("\n");
                    report.append("----------------------------------------\n");
                }

                report.append("\n=== RESUMO ===\n");
                report.append("Total de Cuidadores: ").append(count).append("\n");
                report.append("Folha de Pagamento Total: ").append(currencyFormat.format(totalSalary));

                reportView.setReport(report.toString());
                reportView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Nenhum cuidador encontrado para gerar o relatório.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao gerar relatório: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateCuidadorArea() {
        StringBuilder Cuidadors = new StringBuilder();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        try {
            ResultSet resultSet = model.getCuidador();
            if (resultSet != null) {
                while (resultSet.next()) {
                    Cuidadors.append("ID: ").append(resultSet.getInt("id"));
                    Cuidadors.append(" | Nome: ").append(resultSet.getString("name"));
                    Cuidadors.append(" | CPF: ").append(resultSet.getString("cpf"));
                    Cuidadors.append(" | Telefone: ").append(resultSet.getString("phone"));
                    Cuidadors.append(" | Email: ").append(resultSet.getString("email"));
                    Cuidadors.append(" | Especialização: ").append(resultSet.getString("specialization"));
                    Cuidadors.append(" | Data Contratação: ").append(resultSet.getString("hire_date"));
                    Cuidadors.append(" | Salário: ").append(currencyFormat.format(resultSet.getDouble("salary")));
                    Cuidadors.append("\n");
                }
                CuidadorView.setCuidadorArea(Cuidadors.toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(CuidadorView.frame,
                    "Erro ao carregar cuidadores: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Métodos de validação
    private boolean isValidCPF(String cpf) {
        return cpf.matches("\\d{11}");
    }

    private boolean isValidDate(String date) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}