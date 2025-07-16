package model;

import java.sql.*;

public class CuidadorModel {
    private Connection connection;
    private final String DB_URL = "jdbc:sqlite:zoo.db";

    public CuidadorModel() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createCuidadorTable();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }

    private void createCuidadorTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Cuidador (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                cpf TEXT UNIQUE NOT NULL,
                phone TEXT,
                email TEXT,
                specialization TEXT,
                hire_date DATE NOT NULL,
                salary REAL
            )
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela de CuidadorModeles: " + e.getMessage());
        }
    }

    public void addCuidador(String name, String cpf, String phone, String email,
                             String specialization, String hireDate, double salary) {
        String sql = "INSERT INTO Cuidador (name, cpf, phone, email, specialization, hire_date, salary) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, cpf);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);
            pstmt.setString(5, specialization);
            pstmt.setString(6, hireDate);
            pstmt.setDouble(7, salary);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar CuidadorModel: " + e.getMessage());
            throw new RuntimeException("Erro ao adicionar CuidadorModel", e);
        }
    }

    public void updateCuidador(int id, String name, String cpf, String phone, String email,
                                String specialization, String hireDate, double salary) {
        String sql = "UPDATE Cuidador SET name = ?, cpf = ?, phone = ?, email = ?, specialization = ?, hire_date = ?, salary = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, cpf);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);
            pstmt.setString(5, specialization);
            pstmt.setString(6, hireDate);
            pstmt.setDouble(7, salary);
            pstmt.setInt(8, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar CuidadorModel: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar CuidadorModel", e);
        }
    }

    public void removeCuidador(int id) {
        String sql = "DELETE FROM Cuidador WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao remover CuidadorModel: " + e.getMessage());
        }
    }

    public ResultSet getCuidador() {
        String sql = "SELECT * FROM Cuidador ORDER BY name";
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar CuidadorModeles: " + e.getMessage());
            return null;
        }
    }

    public ResultSet getCuidadorById(int id) {
        String sql = "SELECT * FROM Cuidador WHERE id = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Erro ao buscar CuidadorModel por ID: " + e.getMessage());
            return null;
        }
    }

    public ResultSet generateCuidadorReport() {
        String sql = "SELECT * FROM Cuidador ORDER BY specialization, name";
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório de CuidadorModeles: " + e.getMessage());
            return null;
        }
    }

    public boolean cpfExists(String cpf) {
        String sql = "SELECT COUNT(*) FROM Cuidador WHERE cpf = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao verificar CPF: " + e.getMessage());
            return false;
        }
    }

    public boolean cpfExistsForOtherCuidador(String cpf, int currentId) {
        String sql = "SELECT COUNT(*) FROM Cuidador WHERE cpf = ? AND id != ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            pstmt.setInt(2, currentId);
            ResultSet rs = pstmt.executeQuery();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao verificar CPF: " + e.getMessage());
            return false;
        }
    }
}