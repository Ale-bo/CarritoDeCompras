package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOMemoria implements UsuarioDAO {
    private final List<Usuario> usuarios;

    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<>();
        List<PreguntaSeguridad> preguntasAdmin = new ArrayList<>();
        preguntasAdmin.add(new PreguntaSeguridad(1, "Smith"));
        preguntasAdmin.add(new PreguntaSeguridad(2, "Azul"));
        preguntasAdmin.add(new PreguntaSeguridad(3, "Pizza"));

        Usuario admin = new Usuario("0107415069", "Admin,001@", "Administrador", "admin@super.com", "0991234567", "01/01/2000", preguntasAdmin);
        admin.setRol(Rol.ADMINISTRADOR);
        usuarios.add(admin);
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getContrasenia().equals(contrasenia)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String username) {
        usuarios.removeIf(u -> u.getUsername().equals(username));
    }

    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> encontrados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol() == rol) {
                encontrados.add(usuario);
            }
        }
        return encontrados;
    }
}