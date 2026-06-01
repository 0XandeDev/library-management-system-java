package bibliotecasystem.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ConexaoBDTest extends DatabaseTestBase {

    @Test
    void deveEstabelecerConexaoComOBanco() {
        Connection conexao = ConexaoBD.getConexao();
        assertNotNull(conexao, "A conexão não deve ser nula");
        assertFalse(conexao.isClosed(), "A conexão não deve estar fechada");
    }
}
