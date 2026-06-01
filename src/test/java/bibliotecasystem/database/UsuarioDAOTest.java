package bibliotecasystem.database;

import bibliotecasystem.modelos.Usuario;
import bibliotecasystem.security.PasswordUtils;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDAOTest extends DatabaseTestBase {

    @Test
    void deveInserirEListarUsuario() throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario("Teste", "teste@example.com", "(11) 99999-9999", "Estudante", PasswordUtils.hashPassword("senha123"));
        usuarioDAO.inserir(usuario);

        List<Usuario> usuarios = usuarioDAO.listarTodos();
        assertFalse(usuarios.isEmpty(), "A lista de usuários não deve estar vazia");
        assertTrue(usuarios.stream().anyMatch(u -> u.getEmail().equals("teste@example.com")), "Deve encontrar o usuário inserido");
    }

    @Test
    void deveBuscarUsuarioPorLogin() throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario("LoginTest", "login@example.com", "(11) 88888-8888", "Professor", PasswordUtils.hashPassword("senha456"));
        usuarioDAO.inserir(usuario);

        Usuario encontrado = usuarioDAO.buscarPorLogin("login@example.com");
        assertNotNull(encontrado, "Usuário deve ser encontrado por email");
        assertEquals("LoginTest", encontrado.getNome());
    }
}
