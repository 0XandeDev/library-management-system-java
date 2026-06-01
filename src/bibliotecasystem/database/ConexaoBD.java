package bibliotecasystem.database;

import bibliotecasystem.security.PasswordUtils;
import bibliotecasystem.util.LoggerUtils;

import java.awt.GraphicsEnvironment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConexaoBD {
    private static final Logger LOGGER = LoggerUtils.getLogger(ConexaoBD.class.getName());
    private static final String URL = System.getProperty("db.url", "jdbc:mysql://localhost:3306/biblioteca_db");
    private static final String ADMIN_URL = System.getProperty("db.adminUrl", "jdbc:mysql://localhost:3306/");
    private static final String USER = System.getProperty("db.user", "root");
    private static final String PASSWORD = System.getProperty("db.password", "Maguila02");

    private static Connection conexao;

    public static Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                conexao = DriverManager.getConnection(URL, USER, PASSWORD);
                LOGGER.info("Conexão com o banco estabelecida");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao conectar com o banco", e);
            if (e.getMessage() != null && e.getMessage().contains("Unknown database")) {
                criarBancoSeNaoExistir();
                try {
                    conexao = DriverManager.getConnection(URL, USER, PASSWORD);
                    LOGGER.info("Conexão estabelecida após criar o banco");
                } catch (SQLException ex) {
                    exibirErro("Erro crítico ao conectar com o banco!\n" + ex.getMessage(), "Erro de Conexão");
                }
            } else {
                exibirErro("Erro ao conectar com o banco de dados!\n" + e.getMessage(), "Erro de Conexão");
            }
        }
        return conexao;
    }

    private static void criarBancoSeNaoExistir() {
        try (Connection conn = DriverManager.getConnection(ADMIN_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS biblioteca_db");
            LOGGER.info("Banco 'biblioteca_db' criado ou já existente");
            stmt.executeUpdate("USE biblioteca_db");
            criarTabelas(stmt);
            inserirDadosIniciais(conn);
            LOGGER.info("Estrutura do banco criada com sucesso");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao criar banco", e);
            exibirErro("Não foi possível criar o banco automaticamente.\n" + e.getMessage(), "Erro");
        }
    }

    private static void criarTabelas(Statement stmt) throws SQLException {
        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS usuarios (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "nome VARCHAR(100) NOT NULL, " +
            "email VARCHAR(100) UNIQUE NOT NULL, " +
            "telefone VARCHAR(20), " +
            "tipo ENUM('Estudante', 'Professor', 'Funcionário') NOT NULL, " +
            "senha_hash VARCHAR(255) NOT NULL DEFAULT '', " +
            "emprestimos_ativos INT DEFAULT 0, " +
            "data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
        );

        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS livros (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "titulo VARCHAR(200) NOT NULL, " +
            "autor VARCHAR(100) NOT NULL, " +
            "isbn VARCHAR(20) UNIQUE NOT NULL, " +
            "quantidade INT NOT NULL, " +
            "disponiveis INT NOT NULL, " +
            "data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
        );

        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS emprestimos (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "id_usuario INT NOT NULL, " +
            "id_livro INT NOT NULL, " +
            "data_emprestimo DATE NOT NULL, " +
            "data_devolucao DATE NOT NULL, " +
            "data_devolvida DATE NULL, " +
            "status ENUM('ATIVO', 'FINALIZADO', 'ATRASADO') DEFAULT 'ATIVO', " +
            "FOREIGN KEY (id_usuario) REFERENCES usuarios(id), " +
            "FOREIGN KEY (id_livro) REFERENCES livros(id))"
        );
    }

    private static void inserirDadosIniciais(Connection conn) {
        criarUsuarioInicial(conn, "João Silva", "joao@email.com", "(11) 99999-9999", "Estudante", "joao123");
        criarUsuarioInicial(conn, "Maria Santos", "maria@email.com", "(11) 88888-8888", "Professor", "maria123");
        criarUsuarioInicial(conn, "Carlos Oliveira", "carlos@email.com", "(11) 77777-7777", "Funcionário", "carlos123");
        criarUsuarioInicial(conn, "Ana Costa", "ana@email.com", "(11) 66666-6666", "Estudante", "ana123");
        criarUsuarioInicial(conn, "Admin", "admin@biblioteca.com", "(11) 55555-5555", "Funcionário", "admin123");

        criarLivroInicial(conn, "Dom Casmurro", "Machado de Assis", "978-85-7232-144-9", 3);
        criarLivroInicial(conn, "O Cortiço", "Aluísio Azevedo", "978-85-7232-145-6", 2);
        criarLivroInicial(conn, "Iracema", "José de Alencar", "978-85-7232-146-3", 4);
        criarLivroInicial(conn, "Memórias Póstumas de Brás Cubas", "Machado de Assis", "978-85-7232-147-0", 3);
        criarLivroInicial(conn, "O Guarani", "José de Alencar", "978-85-7232-148-7", 2);
    }

    private static void criarUsuarioInicial(Connection conn, String nome, String email, String telefone, String tipo, String senha) {
        String sql = "INSERT INTO usuarios (nome, email, telefone, tipo, senha_hash) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, telefone);
            stmt.setString(4, tipo);
            stmt.setString(5, PasswordUtils.hashPassword(senha));
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Falha ao inserir usuário inicial: " + email, e);
        }
    }

    private static void criarLivroInicial(Connection conn, String titulo, String autor, String isbn, int quantidade) {
        String sql = "INSERT INTO livros (titulo, autor, isbn, quantidade, disponiveis) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            stmt.setString(2, autor);
            stmt.setString(3, isbn);
            stmt.setInt(4, quantidade);
            stmt.setInt(5, quantidade);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Falha ao inserir livro inicial: " + titulo, e);
        }
    }

    private static void exibirErro(String mensagem, String titulo) {
        LOGGER.severe(titulo + ": " + mensagem);
        if (!GraphicsEnvironment.isHeadless()) {
            JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                LOGGER.info("Conexão com o banco fechada");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Erro ao fechar conexão", e);
        }
    }
}
