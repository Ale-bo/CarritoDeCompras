package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;

    public UsuarioDAOMemoria(){
        usuarios = new ArrayList<Usuario>();
        crear(new Usuario("admin", "12345", Rol.ADMINISTRADOR));
        crear(new Usuario("user", "12345", Rol.USUARIO));
    }

    @Override
    public void autenticar(String username, String contrasenia) {
        for (Usuario usuario : usuarios){
            if(usuario.getUsername().equals(username) && usuario.getContrasenia().equals(contrasenia)){
                return usuario;
            }
        }
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
        Iterator<Usuario> interator = usuarios.iterator();
        while(interator.hasNext()){
            Usuario usuario = interator.next();
            if(usuario.getUsername().equals(username)){
                interator.remove();
                break;
            }
        }

    }

    @Override
    public void actualizar(Usuario usuario) {
        for(int i = 0; i < usuarios.size(); i++){
            Usuario usuarioAux = usuarios.get(i);
            if(usuarioAux.getUsername().equals(usuario.getUsername())){
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        return List.of();
    }

    @Override
    public List<Usuario> listarAdministradores() {
        return List.of();
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return List.of();
    }
}
