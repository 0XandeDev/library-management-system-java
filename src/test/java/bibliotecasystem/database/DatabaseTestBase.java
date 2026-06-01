package bibliotecasystem.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseTestBase {

    @BeforeEach
    void setUp() throws SQLException {
        System.setProperty("db.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        System.setProperty("db.user", "sa");
        System.setProperty("db.password", "");

        try (Connection conn = ConexaoBD.getConexao();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nome VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) UNIQUE NOT NULL, " +
                    "telefone VARCHAR(20), " +
                    "tipo VARCHAR(50) NOT NULL, " +
                    "senha_hash VARCHAR(255) NOT NULL DEFAULT '', " +
                    "emprestimos_ativos INT DEFAULT 0)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS livros (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "titulo VARCHAR(200) NOT NULL, " +
                    "autor VARCHAR(100) NOT NULL, " +
                    "isbn VARCHAR(20) UNIQUE NOT NULL, " +
                    "quantidade INT NOT NULL, " +
                    "disponiveis INT NOT NULL)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS emprestimos (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "id_usuario INT NOT NULL, " +
                    "id_livro INT NOT NULL, " +
                    "data_emprestimo DATE NOT NULL, " +
                    "data_devolucao DATE NOT NULL, " +
                    "data_devolvida DATE NULL, " +
                    "status VARCHAR(20) NOT NULL)");
        }
    }

    @AfterEach
    void tearDown() {
        ConexaoBD.fecharConexao();
    }
}
