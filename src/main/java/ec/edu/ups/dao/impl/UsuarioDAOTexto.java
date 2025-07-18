package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOTexto implements UsuarioDAO {

    private String rutaArchivo; // Ruta donde se guardará el archivo de texto
    private List<Usuario> usuarios; // Cache en memoria para operaciones

    public UsuarioDAOTexto (String rutaArchivo) {
        this.rutaArchivo = rutaArchivo + File.separator + "usuarios.txt"; // Define el nombre del archivo
        this.usuarios = new ArrayList<>();
        cargarUsuariosDesdeArchivo(); // Carga los usuarios existentes al inicializar
    }

    private void cargarUsuariosDesdeArchivo() {
        usuarios.clear(); // Limpiar la lista actual
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return; // Si el archivo no existe, no hay usuarios que cargar
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            Usuario usuario = null;
            List<PreguntaSeguridad> preguntasTemp = new ArrayList<>();
            boolean enPreguntas = false;

            while ((linea = reader.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue; // Ignorar líneas vacías

                if (linea.equals("---")) { // Separador entre usuarios
                    if (usuario != null) {
                        usuario.setPreguntasDeSeguridad(preguntasTemp);
                        usuarios.add(usuario);
                        usuario = null; // Reiniciar para el siguiente usuario
                        preguntasTemp = new ArrayList<>();
                        enPreguntas = false;
                    }
                } else if (linea.equals("#PREGUNTAS#")) {
                    enPreguntas = true;
                } else if (linea.equals("#FINPREGUNTAS#")) {
                    enPreguntas = false;
                } else if (enPreguntas) {
                    // Si estamos en la sección de preguntas, leemos el ID y luego la respuesta
                    try {
                        int preguntaId = Integer.parseInt(linea);
                        String respuesta = reader.readLine(); // Leer la siguiente línea como respuesta
                        if (respuesta != null) {
                            preguntasTemp.add(new PreguntaSeguridad(preguntaId, respuesta.trim()));
                        } else {
                            System.err.println("Error de formato en preguntas de seguridad: respuesta nula después del ID.");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato en preguntas de seguridad: ID de pregunta no numérico. Línea: " + linea);
                    }
                } else {
                    // Si no estamos en preguntas y no es un separador, son los atributos del usuario
                    if (usuario == null) {
                        // Creación de un usuario temporal para asignar atributos
                        usuario = new Usuario(); // Usamos el constructor por defecto
                    }

                    // Aquí asumimos un orden estricto de lectura de atributos
                    // Username, contrasenia, rol, nombre, correo, celular, fechaNacimiento
                    // Es crucial que el orden de escritura y lectura sea el mismo
                    if (usuario.getUsername() == null) {
                        usuario.setUsername(linea);
                    } else if (usuario.getContrasenia() == null) {
                        usuario.setContrasenia(linea);
                    } else if (usuario.getRol() == null) {
                        try {
                            usuario.setRol(Rol.valueOf(linea)); // Convertir String a Enum Rol
                        } catch (IllegalArgumentException e) {
                            System.err.println("Error de formato: Rol '" + linea + "' no válido. Asignando USUARIO.");
                            usuario.setRol(Rol.USUARIO); // Rol por defecto si hay error
                        }
                    } else if (usuario.getNombre() == null) {
                        usuario.setNombre(linea);
                    } else if (usuario.getCorreo() == null) {
                        usuario.setCorreo(linea);
                    } else if (usuario.getCelular() == null) {
                        usuario.setCelular(linea);
                    } else if (usuario.getFechaNacimiento() == null) {
                        usuario.setFechaNacimiento(linea);
                    }
                }
            }
            // Añadir el último usuario si el archivo no termina con un separador '---'
            if (usuario != null) {
                usuario.setPreguntasDeSeguridad(preguntasTemp);
                usuarios.add(usuario);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de usuarios: " + e.getMessage());
        }
    }

    private void guardarUsuariosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Usuario usuario : usuarios) {
                writer.write(usuario.getUsername());
                writer.newLine();
                writer.write(usuario.getContrasenia());
                writer.newLine();
                writer.write(usuario.getRol().name()); // Guardar el nombre del Enum Rol
                writer.newLine();
                writer.write(usuario.getNombre());
                writer.newLine();
                writer.write(usuario.getCorreo());
                writer.newLine();
                writer.write(usuario.getCelular());
                writer.newLine();
                writer.write(usuario.getFechaNacimiento());
                writer.newLine();

                writer.write("#PREGUNTAS#");
                writer.newLine();
                if (usuario.getPreguntasDeSeguridad() != null) {
                    for (PreguntaSeguridad ps : usuario.getPreguntasDeSeguridad()) {
                        writer.write(String.valueOf(ps.getPreguntaId()));
                        writer.newLine();
                        writer.write(ps.getRespuesta());
                        writer.newLine();
                    }
                }
                writer.write("#FINPREGUNTAS#");
                writer.newLine();
                writer.write("---"); // Separador entre usuarios
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de usuarios: " + e.getMessage());
        }
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        // Recargar por si el archivo ha cambiado externamente
        cargarUsuariosDesdeArchivo();
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
