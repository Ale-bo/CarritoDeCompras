package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOBinario implements UsuarioDAO {

    private String rutaArchivo; // Ruta donde se guardará el archivo binario
    private List<Usuario> usuarios; // Cache en memoria para operaciones

    public UsuarioDAOBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo + File.separator + "usuarios.dat"; // Define el nombre del archivo binario
        this.usuarios = new ArrayList<>();
        cargarUsuariosDesdeArchivo(); // Carga los usuarios existentes al inicializar
    }

    private void cargarUsuariosDesdeArchivo() {
        usuarios.clear(); // Limpiar la lista actual
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return; // Si el archivo no existe, no hay usuarios que cargar
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            usuarios = (List<Usuario>) ois.readObject();
        } catch (IOException e) {
            System.err.println("Error de E/S al leer el archivo binario de usuarios: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error de clase no encontrada al leer el archivo binario de usuarios: " + e.getMessage());
        }
    }

    private void guardarUsuariosEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.err.println("Error de E/S al escribir en el archivo binario de usuarios: " + e.getMessage());
        }
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        cargarUsuariosDesdeArchivo(); // Recargar por si el archivo ha cambiado externamente
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getContrasenia().equals(contrasenia)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        cargarUsuariosDesdeArchivo(); // Asegurar que la cache esté actualizada
        usuarios.add(usuario);
        guardarUsuariosEnArchivo();
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        cargarUsuariosDesdeArchivo();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String username) {
        cargarUsuariosDesdeArchivo();
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario u = iterator.next();
            if (u.getUsername().equals(username)) {
                iterator.remove();
                guardarUsuariosEnArchivo();
                return;
            }
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        cargarUsuariosDesdeArchivo();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                guardarUsuariosEnArchivo();
                return;
            }
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        cargarUsuariosDesdeArchivo();
        return new ArrayList<>(usuarios); // Devolver una copia para evitar modificación externa
    }

    @Override
    public List<Usuario> listarPorRol(Rol rol) {
        cargarUsuariosDesdeArchivo();
        List<Usuario> encontrados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol() == rol) {
                encontrados.add(usuario);
            }
        }
        return encontrados;
    }
}