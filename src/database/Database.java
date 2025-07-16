package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection connection;

    public Database() {
        try {
            // Cria ou conecta ao banco de dados
            connection = DriverManager.getConnection("jdbc:sqlite:zoologico.db");
            createTables();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    private void createTables() {
        String createAnimalsTable = "CREATE TABLE IF NOT EXISTS animals ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "species TEXT NOT NULL)";

        String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT UNIQUE NOT NULL, "
                + "password TEXT NOT NULL)";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createAnimalsTable);
            stmt.execute(createUsersTable);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}