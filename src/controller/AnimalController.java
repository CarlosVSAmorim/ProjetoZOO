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
    // Removido LoginView, RegisterView, MenuView do construtor do AnimalController
    // A responsabilidade de transição de tela e autenticação é do MenuController/LoginController
    private ReportView reportView;

    public AnimalController(AnimalModel model, AnimalView animalView, ReportView reportView) {
        this.model = model;
        this.animalView = animalView;
        this.reportView = reportView;

        setupListeners();
    }

    private void setupListeners() {
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

        // O listener de "Voltar" será gerenciado pelo MenuController
        // animalView.addBackListener(e -> {
        //     animalView.setVisible(false);
        //     menuView.setVisible(true);
        // });
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
        String reportContent = getReportAsString(); // Obter o relatório como string
        if (!reportContent.isEmpty()) {
            reportView.setReport(reportContent);
            reportView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum animal encontrado para gerar o relatório.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Novo método para retornar o relatório como String
    public String getReportAsString() {
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
            }
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório de animais (string): " + e.getMessage());
            return "Erro ao gerar relatório de animais.";
        }
        return report.toString();
    }

    // Métodos CRUD adicionados para serem chamados pelo MenuController (se necessário)
    // No entanto, a AnimalView já tem listeners para isso, então o MenuController
    // apenas precisa mostrar a AnimalView.
    public void createAnimal() {
        // A lógica já está no addAnimalListener
        animalView.addAnimalListener(null); // Apenas para fins de exemplo, não deve ser chamado assim
    }

    public void editAnimal() {
        // A lógica já está no addUpdateListener
    }

    public void deleteAnimal() {
        // A lógica já está no addRemoveListener
    }

    public void listAnimals() {
        updateAnimalArea();
    }

    public void searchAnimal() {
        // Implementar lógica de busca se necessário
        JOptionPane.showMessageDialog(animalView.frame, "Funcionalidade de busca de animal não implementada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
}
