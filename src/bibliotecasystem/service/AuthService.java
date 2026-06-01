package bibliotecasystem.service;

import bibliotecasystem.database.UsuarioDAO;
import bibliotecasystem.modelos.Usuario;
import bibliotecasystem.security.PasswordUtils;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AuthService {
    private static final Logger LOGGER = Logger.getLogger(AuthService.class.getName());
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario autenticar(String login, String senha) throws SQLException {
        Usuario usuario = usuarioDAO.buscarPorLogin(login);
        if (usuario == null) {
            LOGGER.warning("Tentativa de login falhou: usuário não encontrado [" + login + "]");
            return null;
        }
        if (PasswordUtils.verifyPassword(senha, usuario.getSenhaHash())) {
            LOGGER.info("Login bem-sucedido para: " + login);
            return usuario;
        }
        LOGGER.warning("Tentativa de login falhou: senha incorreta para [" + login + "]");
        return null;
    }
}
