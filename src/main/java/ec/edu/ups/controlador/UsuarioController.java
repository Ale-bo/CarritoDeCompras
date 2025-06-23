package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.LoginView;

public class UsuarioController {
     private Usuario usuario;
     private final UsuarioDAO usuarioDAO;
     private final LoginView loginView;

    public UsuarioController(LoginView loginView, UsuarioDAO usuarioDAO, Usuario usuario) {
        this.loginView = loginView;
        this.usuarioDAO = usuarioDAO;
        this.usuario = new Usuario();
    }
    private void iniciarSesion(){

    }
}
