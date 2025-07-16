package model;

import java.sql.*;

public class AnimalModel {
    private Connection connection;
    private final String DB_URL = "jdbc:sqlite:zoo.db";

    public AnimalModel() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }

    private void createTables() {
        String animalTable = """
            CREATE TABLE IF NOT EXISTS animals (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                species TEXT NOT NULL
            )
            """;

        String userTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL
            )
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(animalTable);
            stmt.execute(userTable);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }

    public void addAnimal(String name, String species) {
        String sql = "INSERT INTO animals (name, species) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, species);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar animal: " + e.getMessage());
        }
    }

    public void updateAnimal(int id, String name, String species) {
        String sql = "UPDATE animals SET name = ?, species = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, species);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar animal: " + e.getMessage());
        }
    }

    public void removeAnimal(int id) {
        String sql = "DELETE FROM animals WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao remover animal: " + e.getMessage());
        }
    }

    public ResultSet getAnimals() {
        String sql = "SELECT * FROM animals ORDER BY id";
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar animais: " + e.getMessage());
            return null;
        }
    }

    public ResultSet generateReport() {
        String sql = "SELECT * FROM animals ORDER BY species, name";
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório: " + e.getMessage());
            return null;
        }
    }

    public void registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao registrar usuário: " + e.getMessage());
        }
    }

    public boolean authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Erro ao autenticar usuário: " + e.getMessage());
            return false;
        }
    }
}