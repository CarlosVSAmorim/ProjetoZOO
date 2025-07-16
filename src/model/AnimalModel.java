package model;

import java.sql.*;

public class AnimalModel {
    private Connection connection;

    public AnimalModel() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:zoologico.db");
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS animals (id INTEGER PRIMARY KEY, name TEXT, species TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username TEXT, password TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CRUD para Animais
    public void addAnimal(String name, String species) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO animals (name, species) VALUES (?, ?)");
            pstmt.setString(1, name);
            pstmt.setString(2, species);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAnimals() {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery("SELECT * FROM animals");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateAnimal(int id, String name, String species) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("UPDATE animals SET name = ?, species = ? WHERE id = ?");
            pstmt.setString(1, name);
            pstmt.setString(2, species);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAnimal(int id) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM animals WHERE id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CRUD para Usuários
    public boolean authenticate(String username, String password) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Retorna true se o usuário for encontrado
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void registerUser (String username, String password) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para gerar relatório de animais
    public ResultSet generateReport() {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery("SELECT * FROM animals");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}