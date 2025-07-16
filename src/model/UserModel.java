package model;

import java.sql.*;

public class UserModel {
    private Connection connection;
    private final String DB_URL = "jdbc:sqlite:zoo.db";

    public UserModel() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createUserTable(); // Chama o método que cria a tabela e insere o usuário padrão
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }

    private void createUserTable() {
        String createTableSql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL
            )
            """;

        String checkUserCountSql = "SELECT COUNT(*) FROM users";
        String insertDefaultUserSql = "INSERT INTO users (username, password) VALUES ('admin', '123')"; // Usuário padrão: admin, Senha: 123

        try (Statement stmt = connection.createStatement()) {
            // 1. Garante que a tabela 'users' existe
            stmt.execute(createTableSql);

            // 2. Verifica se a tabela 'users' está vazia
            try (ResultSet rs = stmt.executeQuery(checkUserCountSql)) {
                if (rs.next() && rs.getInt(1) == 0) { // Se o count for 0, a tabela está vazia
                    // 3. Insere o usuário padrão
                    stmt.execute(insertDefaultUserSql);
                    System.out.println("Usuário padrão 'admin' com senha '123' criado com sucesso.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela de usuários ou inserir usuário padrão: " + e.getMessage());
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
            throw new RuntimeException("Erro ao registrar usuário", e);
        }
    }

    public void updateUser(int id, String username, String password) {
        String sql = "UPDATE users SET username = ?, password = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public void removeUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao remover usuário: " + e.getMessage());
        }
    }

    public ResultSet getUsers() {
        String sql = "SELECT * FROM users ORDER BY id";
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários: " + e.getMessage());
            return null;
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
