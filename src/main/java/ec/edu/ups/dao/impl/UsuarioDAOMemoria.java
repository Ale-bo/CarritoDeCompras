package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;

    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<>();

        List<Integer> adminPregIds = List.of(1, 2, 3);
        List<String>  adminResps   = List.of("Smith", "Blue", "Pizza");
        // --- CREAR ADMIN POR DEFECTO ---
        Usuario admin = new Usuario(
                "admin",
                "12345",
                "Administrador",
                "admin@ejemplo.com",
                "0999999999",
                adminPregIds,
                adminResps
        );
        admin.setRol(Rol.ADMINISTRADOR);
        usuarios.add(admin);

        System.out.println("Usuario ADMIN creado: username="
                + admin.getUsername()
                + ", password=" + admin.getContrasenia());
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)
                    && usuario.getContrasenia().equals(contrasenia)) {
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
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsername().equals(username)) {
                iterator.remove();
                break;
            }
        }
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
        return usuarios;
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        List<Usuario> encontrados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol().equals(rol)) {
                encontrados.add(usuario);
            }
        }
        return encontrados;
    }
}