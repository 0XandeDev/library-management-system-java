package bibliotecasystem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ConexaoBD {
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_db";
    private static final String USER = "USER";
    private static final String PASSWORD = "SENHA";
    
    private static Connection conexao;
    
    public static Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                conexao = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Conexão com o banco estabelecida!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Erro ao conectar com o banco: " + e.getMessage());
            
            // Tentar criar o banco se não existir
            if (e.getMessage().contains("Unknown database")) {
                criarBancoSeNaoExistir();
                try {
                    conexao = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("✅ Conexão estabelecida após criar o banco!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, 
                        "Erro crítico ao conectar com o banco!\n" + ex.getMessage(), 
                        "Erro de Conexão", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Erro ao conectar com o banco de dados!\n" + e.getMessage(), 
                    "Erro de Conexão", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        return conexao;
    }
    
    private static void criarBancoSeNaoExistir() {
        try {
            // Conectar sem especificar o banco
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", USER, PASSWORD);
            Statement stmt = conn.createStatement();
            
            // Criar banco
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS biblioteca_db");
            System.out.println("✅ Banco 'biblioteca_db' criado!");
            
            // Usar o banco
            stmt.executeUpdate("USE biblioteca_db");
            
            // Criar tabelas
            criarTabelas(stmt);
            
            // Inserir dados iniciais
            inserirDadosIniciais(stmt);
            
            stmt.close();
            conn.close();
            
            System.out.println("✅ Estrutura do banco criada com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao criar banco: " + e.getMessage());
            JOptionPane.showMessageDialog(null, 
                "Não foi possível criar o banco automaticamente.\n" + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private static void criarTabelas(Statement stmt) throws SQLException {
        // Tabela de Usuários
        stmt.executeUpdate(
            "CREATE TABLE IF NOT EXISTS usuarios (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "nome VARCHAR(100) NOT NULL, " +
            "email VARCHAR(100) UNIQUE NOT NULL, " +
            "telefone VARCHAR(20), " +
            "tipo ENUM('Estudante', 'Professor', 'Funcionário') NOT NULL, " +
            "emprestimos_ativos INT DEFAULT 0, " +
            "data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
        );
        
        // Tabela de Livros
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
        
        // Tabela de Empréstimos
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
        
        System.out.println("✅ Tabelas criadas com sucesso!");
    }
    
    private static void inserirDadosIniciais(Statement stmt) throws SQLException {
        // Inserir usuários (usando IGNORE para evitar duplicatas)
        String sqlUsuarios = 
            "INSERT IGNORE INTO usuarios (nome, email, telefone, tipo) VALUES " +
            "('João Silva', 'joao@email.com', '(11) 99999-9999', 'Estudante'), " +
            "('Maria Santos', 'maria@email.com', '(11) 88888-8888', 'Professor'), " +
            "('Carlos Oliveira', 'carlos@email.com', '(11) 77777-7777', 'Funcionário'), " +
            "('Ana Costa', 'ana@email.com', '(11) 66666-6666', 'Estudante'), " +
            "('Admin', 'admin@biblioteca.com', '(11) 55555-5555', 'Funcionário')";
        stmt.executeUpdate(sqlUsuarios);
        
        // Inserir livros (usando IGNORE para evitar duplicatas)
        String sqlLivros = 
            "INSERT IGNORE INTO livros (titulo, autor, isbn, quantidade, disponiveis) VALUES " +
            "('Dom Casmurro', 'Machado de Assis', '978-85-7232-144-9', 3, 3), " +
            "('O Cortiço', 'Aluísio Azevedo', '978-85-7232-145-6', 2, 2), " +
            "('Iracema', 'José de Alencar', '978-85-7232-146-3', 4, 4), " +
            "('Memórias Póstumas de Brás Cubas', 'Machado de Assis', '978-85-7232-147-0', 3, 3), " +
            "('O Guarani', 'José de Alencar', '978-85-7232-148-7', 2, 2)";
        stmt.executeUpdate(sqlLivros);
        
        System.out.println("✅ Dados iniciais inseridos!");
    }
    
    public static void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("🔌 Conexão fechada!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
