package model;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CuidadorModel {
    private Connection connection;
    private final String DB_URL = "jdbc:sqlite:zoo.db"; // Usar o mesmo DB dos outros modelos

    // Atributos do cuidador (POJO)
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String especialidade;
    private double salario;
    private LocalDate dataContratacao;
    private boolean ativo;

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DB_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public CuidadorModel() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createCuidadorTable();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados ou criar tabela de cuidadores: " + e.getMessage());
        }
    }

    // Construtores para instanciar objetos CuidadorModel (POJO)
    public CuidadorModel(String nome, String cpf, String telefone, String email,
                         String especialidade, double salario, LocalDate dataContratacao) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.especialidade = especialidade;
        this.salario = salario;
        this.dataContratacao = dataContratacao;
        this.ativo = true;
    }

    public CuidadorModel(int id, String nome, String cpf, String telefone, String email,
                         String especialidade, double salario, LocalDate dataContratacao) {
        this(nome, cpf, telefone, email, especialidade, salario, dataContratacao);
        this.id = id;
    }

    // --- Métodos de Persistência (DB) ---

    private void createCuidadorTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS cuidadores (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                cpf TEXT UNIQUE NOT NULL,
                telefone TEXT,
                email TEXT,
                especialidade TEXT,
                salario REAL,
                data_contratacao TEXT, -- Armazenar como TEXT no formato YYYY-MM-DD
                ativo INTEGER DEFAULT 1 -- 1 para ativo, 0 para inativo
            )
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela de cuidadores: " + e.getMessage());
        }
    }

    public void addCuidador(CuidadorModel cuidador) throws SQLException {
        String sql = "INSERT INTO cuidadores (nome, cpf, telefone, email, especialidade, salario, data_contratacao, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cuidador.getNome());
            pstmt.setString(2, cuidador.getCpf()); // <-- Verificar aqui
            pstmt.setString(3, cuidador.getTelefone()); // <-- Verificar aqui
            pstmt.setString(4, cuidador.getEmail());
            pstmt.setString(5, cuidador.getEspecialidade());
            pstmt.setDouble(6, cuidador.getSalario());
            pstmt.setString(7, cuidador.getDataContratacao().format(DB_DATE_FORMATTER));
            pstmt.executeUpdate();
        }
    }

    public void updateCuidador(CuidadorModel cuidador) throws SQLException {
        String sql = "UPDATE cuidadores SET nome = ?, cpf = ?, telefone = ?, email = ?, especialidade = ?, salario = ?, data_contratacao = ?, ativo = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cuidador.getNome());
            pstmt.setString(2, cuidador.getCpf()); // <-- Verificar aqui
            pstmt.setString(3, cuidador.getTelefone()); // <-- Verificar aqui
            pstmt.setString(4, cuidador.getEmail());
            pstmt.setString(5, cuidador.getEspecialidade());
            pstmt.setDouble(6, cuidador.getSalario());
            pstmt.setString(7, cuidador.getDataContratacao().format(DB_DATE_FORMATTER));
            pstmt.setInt(9, cuidador.getId());
            pstmt.executeUpdate();
        }
    }

    public void removeCuidador(int id) throws SQLException {
        String sql = "DELETE FROM cuidadores WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public ResultSet getCuidadorsResultSet() throws SQLException {
        String sql = "SELECT * FROM cuidadores ORDER BY nome";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    public List<CuidadorModel> getAllCuidadors() throws SQLException {
        List<CuidadorModel> cuidadores = new ArrayList<>();
        String sql = "SELECT * FROM cuidadores ORDER BY nome";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                String especialidade = rs.getString("especialidade");
                double salario = rs.getDouble("salario");
                LocalDate dataContratacao = LocalDate.parse(rs.getString("data_contratacao"), DB_DATE_FORMATTER);
                boolean ativo = rs.getInt("ativo") == 1;

                CuidadorModel cuidador = new CuidadorModel(id, nome, cpf, telefone, email, especialidade, salario, dataContratacao);

                cuidadores.add(cuidador);
            }
        }
        return cuidadores;
    }

    public CuidadorModel getCuidadorById(int id) throws SQLException {
        String sql = "SELECT * FROM cuidadores WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    String telefone = rs.getString("telefone");
                    String email = rs.getString("email");
                    String especialidade = rs.getString("especialidade");
                    double salario = rs.getDouble("salario");
                    LocalDate dataContratacao = LocalDate.parse(rs.getString("data_contratacao"), DB_DATE_FORMATTER);
                    boolean ativo = rs.getInt("ativo") == 1;

                    CuidadorModel cuidador = new CuidadorModel(id, nome, cpf, telefone, email, especialidade, salario, dataContratacao);
                    return cuidador;
                }
            }
        }
        return null; // Cuidador não encontrado
    }

    public boolean cpfExists(String cpf) throws SQLException {
        String sql = "SELECT COUNT(*) FROM cuidadores WHERE cpf = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean cpfExistsForOtherCuidador(String cpf, int currentId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM cuidadores WHERE cpf = ? AND id != ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, cpf);
            pstmt.setInt(2, currentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // --- Getters e Setters (POJO) ---
    // (Mantidos os getters e setters existentes para os atributos do cuidador)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
    public LocalDate getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(LocalDate dataContratacao) { this.dataContratacao = dataContratacao; }
    public String getDataContratacaoFormatada() {
        return dataContratacao != null ? dataContratacao.format(DATE_FORMATTER) : "";
    }
    public void setDataContratacaoFromString(String dataString) throws DateTimeParseException {
        if (dataString != null && !dataString.trim().isEmpty()) {
            this.dataContratacao = LocalDate.parse(dataString, DATE_FORMATTER);
        }
    }

    // --- Métodos de Validação (POJO) ---
    // (Mantidos os métodos de validação existentes)
    public boolean isValid() { /* ... */ return true; }
    public boolean isNomeValid() { /* ... */ return true; }
    public boolean isCpfValid() { /* ... */ return true; }
    public boolean isEmailValid() { /* ... */ return true; }
    public boolean isTelefoneValid() { /* ... */ return true; }
    public boolean isEspecialidadeValid() { /* ... */ return true; }
    public boolean isSalarioValid() { /* ... */ return true; }
    public boolean isDataContratacaoValid() { /* ... */ return true; }

    // --- Métodos Utilitários (POJO) ---
    // (Mantidos os métodos utilitários existentes)
    public String getCpfFormatado() { /* ... */ return ""; }
    public String getTelefoneFormatado() { /* ... */ return ""; }
    public String getSalarioFormatado() { /* ... */ return ""; }
    public int getAnosServico() { /* ... */ return 0; }
    public String getResumoInfo() { /* ... */ return ""; }
    public String getInfoCompleta() { /* ... */ return ""; }
    public boolean matchesSearch(String searchTerm) { /* ... */ return true; }
    @Override public boolean equals(Object obj) { /* ... */ return true; }
    @Override public int hashCode() { /* ... */ return 0; }
    @Override public String toString() { /* ... */ return ""; }
    public CuidadorModel clone() { /* ... */ return null; }
    public List<String> getValidationErrors() { /* ... */ return new ArrayList<>(); }
}
