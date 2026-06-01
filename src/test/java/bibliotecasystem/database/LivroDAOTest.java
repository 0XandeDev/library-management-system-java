package bibliotecasystem.database;

import bibliotecasystem.modelos.Livro;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LivroDAOTest extends DatabaseTestBase {

    @Test
    void deveInserirAtualizarBuscarEExcluirLivro() throws SQLException {
        LivroDAO livroDAO = new LivroDAO();
        Livro livro = new Livro("Teste Livro", "Autor Teste", "ISBN-1234", 5);

        livroDAO.inserir(livro);
        assertTrue(livro.getId() > 0, "O livro inserido deve receber um ID");

        Livro buscado = livroDAO.buscarPorId(livro.getId());
        assertNotNull(buscado, "O livro deve ser encontrado por ID");
        assertEquals("Teste Livro", buscado.getTitulo());

        livro.setTitulo("Teste Livro Atualizado");
        livro.setQuantidade(10);
        livroDAO.atualizar(livro);

        Livro atualizado = livroDAO.buscarPorId(livro.getId());
        assertEquals("Teste Livro Atualizado", atualizado.getTitulo());
        assertEquals(10, atualizado.getQuantidade());

        livroDAO.deletar(livro.getId());
        assertNull(livroDAO.buscarPorId(livro.getId()), "O livro deve ser excluído");
    }

    @Test
    void deveBuscarLivrosPorTitulo() throws SQLException {
        LivroDAO livroDAO = new LivroDAO();
        Livro livro = new Livro("Livro Pesquisa", "Autor X", "ISBN-5678", 3);
        livroDAO.inserir(livro);

        List<Livro> resultados = livroDAO.buscarPorTitulo("Pesquisa");
        assertFalse(resultados.isEmpty(), "A pesquisa por título deve retornar resultados");
    }
}
