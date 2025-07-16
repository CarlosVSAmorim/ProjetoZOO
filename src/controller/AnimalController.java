package controller;

import model.AnimalModel;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnimalController {
    private AnimalModel model;
    private AnimalView animalView;
    private LoginView loginView;
    private RegisterView registerView;
    private ReportView reportView;
    private MenuView menuView;

    public AnimalController(AnimalModel model, AnimalView animalView, LoginView loginView,
                            RegisterView registerView, ReportView reportView, MenuView menuView) {
        this.model = model;
        this.animalView = animalView;
        this.loginView = loginView;
        this.registerView = registerView;
        this.reportView = reportView;
        this.menuView = menuView;

        setupListeners();
    }

    private void setupListeners() {
        // Login
        loginView.addLoginListener(e -> {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            if (model.authenticate(username, password)) {
                loginView.setVisible(false);
                menuView.setVisible(true);
                JOptionPane.showMessageDialog(null, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(loginView.frame, "Usuário ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Registro
        loginView.addRegisterListener(e -> {
            loginView.setVisible(false);
            registerView.setVisible(true);
        });

        registerView.addRegisterListener(e -> {
            String username = registerView.getUsername();
            String password = registerView.getPassword();

            if (username.trim().isEmpty() || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(registerView.frame, "Usuário e senha são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            model.registerUser(username, password);
            JOptionPane.showMessageDialog(registerView.frame, "Usuário registrado com sucesso!");
            registerView.setVisible(false);
            loginView.setVisible(true);
        });

        registerView.addBackListener(e -> {
            registerView.setVisible(false);
            loginView.setVisible(true);
        });

        // Gerenciamento de Animais
        animalView.addAnimalListener(e -> {
            String name = animalView.getNameInput();
            String species = animalView.getSpeciesInput();

            if (name.trim().isEmpty() || species.trim().isEmpty()) {
                JOptionPane.showMessageDialog(animalView.frame, "Nome e espécie são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            model.addAnimal(name, species);
            updateAnimalArea();
            animalView.clearFields();
            JOptionPane.showMessageDialog(animalView.frame, "Animal adicionado com sucesso!");
        });

        animalView.addUpdateListener(e -> {
            try {
                int id = Integer.parseInt(animalView.getIdInput());
                String name = animalView.getNameInput();
                String species = animalView.getSpeciesInput();

                if (name.trim().isEmpty() || species.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(animalView.frame, "Nome e espécie são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                model.updateAnimal(id, name, species);
                updateAnimalArea();
                animalView.clearFields();
                JOptionPane.showMessageDialog(animalView.frame, "Animal atualizado com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(animalView.frame, "ID deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        animalView.addRemoveListener(e -> {
            try {
                int id = Integer.parseInt(animalView.getIdInput());
                int confirm = JOptionPane.showConfirmDialog(animalView.frame,
                        "Tem certeza que deseja remover o animal com ID " + id + "?",
                        "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeAnimal(id);
                    updateAnimalArea();
                    animalView.clearFields();
                    JOptionPane.showMessageDialog(animalView.frame, "Animal removido com sucesso!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(animalView.frame, "ID deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        animalView.addReportListener(e -> generateReport());

        animalView.addBackListener(e -> {
            animalView.setVisible(false);
            menuView.setVisible(true);
        });
    }

    public void updateAnimalArea() {
        StringBuilder animals = new StringBuilder();
        try {
            ResultSet resultSet = model.getAnimals();
            if (resultSet != null) {
                while (resultSet.next()) {
                    animals.append("ID: ").append(resultSet.getInt("id"))
                            .append(" | Nome: ").append(resultSet.getString("name"))
                            .append(" | Espécie: ").append(resultSet.getString("species"))
                            .append("\n");
                }
                animalView.setAnimalArea(animals.toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(animalView.frame, "Erro ao carregar animais: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void generateReport() {
        StringBuilder report = new StringBuilder();
        try {
            ResultSet resultSet = model.generateReport();
            if (resultSet != null) {
                report.append("=== RELATÓRIO DE ANIMAIS ===\n\n");
                int count = 0;
                while (resultSet.next()) {
                    count++;
                    report.append("ID: ").append(resultSet.getInt("id"))
                            .append(" | Nome: ").append(resultSet.getString("name"))
                            .append(" | Espécie: ").append(resultSet.getString("species"))
                            .append("\n");
                }
                report.append("\n=== TOTAL DE ANIMAIS: ").append(count).append(" ===");
                reportView.setReport(report.toString());
                reportView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum animal encontrado para gerar o relatório.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}