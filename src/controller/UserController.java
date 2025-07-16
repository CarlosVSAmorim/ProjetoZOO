package controller;

import model.UserModel;
import view.UserView;
import view.MenuView; // Importar MenuView para setMenuView
import view.ReportView; // Importar ReportView para gerar relatórios

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    private UserModel model;
    private UserView userView;
    private MenuView menuView; // Adicionado para setMenuView
    private ReportView reportView; // Adicionado para gerar relatórios

    public UserController(UserModel model, UserView userView, ReportView reportView) { // Adicionado ReportView ao construtor
        this.model = model;
        this.userView = userView;
        this.reportView = reportView; // Inicializar ReportView
        setupListeners();
    }

    public void setMenuView(MenuView menuView) {
        this.menuView = menuView;
    }

    private void setupListeners() {
        userView.addUserListener(e -> addUser());
        userView.addUpdateListener(e -> updateUser());
        userView.addRemoveListener(e -> removeUser());
        // O listener de "Voltar" será gerenciado pelo MenuController
        // userView.addBackListener(e -> {
        //     userView.setVisible(false);
        //     if (menuView != null) {
        //         menuView.setVisible(true);
        //     }
        // });
    }

    private void addUser() {
        String username = userView.getUsernameInput();
        String password = userView.getPasswordInput();

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(userView.frame, "Usuário e senha são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            model.registerUser(username, password);
            updateUserArea();
            userView.clearFields();
            JOptionPane.showMessageDialog(userView.frame, "Usuário adicionado com sucesso!");
        } catch (RuntimeException e) { // Capturar RuntimeException lançada pelo UserModel
            JOptionPane.showMessageDialog(userView.frame, "Erro ao adicionar usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateUser() {
        try {
            int id = Integer.parseInt(userView.getIdInput());
            String username = userView.getUsernameInput();
            String password = userView.getPasswordInput();

            if (username.trim().isEmpty() || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(userView.frame, "Usuário e senha são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            model.updateUser(id, username, password);
            updateUserArea();
            userView.clearFields();
            JOptionPane.showMessageDialog(userView.frame, "Usuário atualizado com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(userView.frame, "ID deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeUser() {
        try {
            int id = Integer.parseInt(userView.getIdInput());
            int confirm = JOptionPane.showConfirmDialog(userView.frame,
                    "Tem certeza que deseja remover o usuário com ID " + id + "?",
                    "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                model.removeUser(id);
                updateUserArea();
                userView.clearFields();
                JOptionPane.showMessageDialog(userView.frame, "Usuário removido com sucesso!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(userView.frame, "ID deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateUserArea() {
        StringBuilder users = new StringBuilder();
        try {
            ResultSet resultSet = model.getUsers();
            if (resultSet != null) {
                while (resultSet.next()) {
                    users.append("ID: ").append(resultSet.getInt("id"))
                            .append(" | Usuário: ").append(resultSet.getString("username"))
                            .append("\n");
                }
                userView.setUserArea(users.toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(userView.frame, "Erro ao carregar usuários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Novo método para validar usuário (chamado pelo MenuController para login)
    public boolean validateUser(String username, String password) {
        return model.authenticate(username, password);
    }

    // Métodos CRUD adicionados para serem chamados pelo MenuController (se necessário)
    public void createUser() {
        // A lógica já está no addUser()
        userView.addUserListener(null); // Apenas para fins de exemplo
    }

    public void editUser() {
        // A lógica já está no updateUser()
    }

    public void deleteUser() {
        // A lógica já está no removeUser()
    }

    public void listUsers() {
        updateUserArea();
    }

    public void searchUser() {
        // Implementar lógica de busca se necessário
        JOptionPane.showMessageDialog(userView.frame, "Funcionalidade de busca de usuário não implementada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

    // Novo método para gerar relatório de usuários
    public void generateReport() {
        String reportContent = getReportAsString();
        if (!reportContent.isEmpty()) {
            reportView.setReport(reportContent);
            reportView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum usuário encontrado para gerar o relatório.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Novo método para retornar o relatório como String
    public String getReportAsString() {
        StringBuilder report = new StringBuilder();
        try {
            ResultSet resultSet = model.getUsers(); // Assumindo que getUsers() pode ser usado para relatório
            if (resultSet != null) {
                report.append("=== RELATÓRIO DE USUÁRIOS ===\n\n");
                int count = 0;
                while (resultSet.next()) {
                    count++;
                    report.append("ID: ").append(resultSet.getInt("id"))
                            .append(" | Usuário: ").append(resultSet.getString("username"))
                            .append("\n");
                }
                report.append("\n=== TOTAL DE USUÁRIOS: ").append(count).append(" ===");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório de usuários (string): " + e.getMessage());
            return "Erro ao gerar relatório de usuários.";
        }
        return report.toString();
    }
}
