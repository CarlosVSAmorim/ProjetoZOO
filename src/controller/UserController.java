package controller;

import model.UserModel;
import view.UserView;
import view.MenuView;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    private UserModel model;
    private UserView userView;
    private MenuView menuView;

    public UserController(UserModel model, UserView userView) {
        this.model = model;
        this.userView = userView;
        setupListeners();
    }

    public void setMenuView(MenuView menuView) {
        this.menuView = menuView;
    }

    private void setupListeners() {
        userView.addUserListener(e -> addUser());
        userView.addUpdateListener(e -> updateUser());
        userView.addRemoveListener(e -> removeUser());
        userView.addBackListener(e -> {
            userView.setVisible(false);
            if (menuView != null) {
                menuView.setVisible(true);
            }
        });
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(userView.frame, "Erro ao adicionar usuário. Usuário pode já existir.", "Erro", JOptionPane.ERROR_MESSAGE);
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
}