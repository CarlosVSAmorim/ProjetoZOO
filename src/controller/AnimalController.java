package controller;

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

    public AnimalController(AnimalModel model, AnimalView animalView, LoginView loginView, RegisterView registerView, ReportView reportView) {
        this.model = model;
        this.animalView = animalView;
        this.loginView = loginView;
        this.registerView = registerView;
        this.reportView = reportView;

        // Login
        loginView.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginView.getUsername();
                String password = loginView.getPassword();
                if (model.authenticate(username, password)) {
                    loginView.close();
                    animalView.setVisible(true);
                    updateAnimalArea();
                } else {
                    JOptionPane.showMessageDialog(loginView.frame, "Usuário ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Registro
        loginView.addRegisterListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginView.close();
                registerView.setVisible(true);
            }
        });

        registerView.addRegisterListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = registerView.getUsername();
                String password = registerView.getPassword();
                model.registerUser (username, password);
                JOptionPane.showMessageDialog(registerView.frame, "Usuário registrado com sucesso!");
                registerView.close();
                loginView.setVisible(true);
            }
        });

        registerView.addBackListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerView.close();
                loginView.setVisible(true);
            }
        });

        // Gerenciamento de Animais
        animalView.addAnimalListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = animalView.getNameInput();
                String species = animalView.getSpeciesInput();
                model.addAnimal(name, species);
                updateAnimalArea();
            }
        });

        animalView.addUpdateListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(animalView.getIdInput());
                String name = animalView.getNameInput();
                String species = animalView.getSpeciesInput();
                model.updateAnimal(id, name, species);
                updateAnimalArea();
            }
        });

        animalView.addRemoveListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(animalView.getIdInput());
                model.removeAnimal(id);
                updateAnimalArea();
            }
        });

        animalView.addReportListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });
    }

    public void updateAnimalArea() {
        StringBuilder animals = new StringBuilder();
        try {
            ResultSet resultSet = model.getAnimals();
            while (resultSet.next()) {
                animals.append("ID: ").append(resultSet.getInt("id"))
                        .append(", Nome: ").append(resultSet.getString("name"))
                        .append(", Espécie: ").append(resultSet.getString("species"))
                        .append("\n");
            }
            animalView.setAnimalArea(animals.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void generateReport() {
        StringBuilder report = new StringBuilder();
        try {
            ResultSet resultSet = model.generateReport();
            while (resultSet.next()) {
                report.append("ID: ").append(resultSet.getInt("id"))
                        .append(", Nome: ").append(resultSet.getString("name"))
                        .append(", Espécie: ").append(resultSet.getString("species"))
                        .append("\n");
            }
            reportView.setReport(report.toString());
            reportView.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}