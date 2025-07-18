package ec.edu.ups.modelo;

import java.io.Serializable; // Importar Serializable
import java.util.List;

/**
 * Clase que representa un Usuario en el sistema de carrito de compras.
 * Permite almacenar la información personal y de autenticación del usuario.
 * Implementa {@code Serializable} para permitir el almacenamiento persistente en archivos binarios.
 *
 * @author Ivanna Alexandra Nievecela Pérez
 * @version 1.0
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L; // Recomendado para Serializable

    private String username;
    private String contrasenia;
    private Rol rol;
    private String nombre;
    private String fechaNacimiento; // Formato String "dd/MM/yyyy"
    private String correo;
    private String celular;
    private List<PreguntaSeguridad> preguntasDeSeguridad;

    /**
     * Constructor por defecto para la clase Usuario.
     * Inicializa el rol del usuario como {@code Rol.USUARIO}.
     */
    public Usuario() {
        this.rol = Rol.USUARIO;
    }

    /**
     * Constructor para crear una nueva instancia de Usuario.
     *
     * @param username El nombre de usuario (usualmente la cédula).
     * @param contrasenia La contraseña del usuario.
     * @param nombre El nombre completo del usuario.
     * @param correo La dirección de correo electrónico del usuario.
     * @param celular El número de teléfono celular del usuario.
     * @param fechaNacimiento La fecha de nacimiento del usuario en formato String (ej. "01/01/2000").
     * @param preguntas Una lista de objetos {@code PreguntaSeguridad} asociados al usuario.
     */
    public Usuario(String username, String contrasenia, String nombre, String correo, String celular, String fechaNacimiento, List<PreguntaSeguridad> preguntas) {
        this.username = username;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.correo = correo;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.preguntasDeSeguridad = preguntas;
        this.rol = Rol.USUARIO; // Por defecto, al crear un usuario nuevo es de tipo USUARIO
    }

    // Getters y Setters con Javadoc
    /**
     * Obtiene el nombre de usuario.
     * @return El nombre de usuario.
     */
    public String getUsername() { return username; }
    /**
     * Establece el nombre de usuario.
     * @param username El nuevo nombre de usuario.
     */
    public void setUsername(String username) { this.username = username; }
    /**
     * Obtiene la contraseña del usuario.
     * @return La contraseña del usuario.
     */
    public String getContrasenia() { return contrasenia; }
    /**
     * Establece la contraseña del usuario.
     * @param contrasenia La nueva contraseña.
     */
    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }
    /**
     * Obtiene el rol del usuario.
     * @return El rol del usuario (ADMINISTRADOR o USUARIO).
     */
    public Rol getRol() { return rol; }
    /**
     * Establece el rol del usuario.
     * @param rol El nuevo rol del usuario.
     */
    public void setRol(Rol rol) { this.rol = rol; }
    /**
     * Obtiene el nombre completo del usuario.
     * @return El nombre completo.
     */
    public String getNombre() { return nombre; }
    /**
     * Establece el nombre completo del usuario.
     * @param nombre El nuevo nombre completo.
     */
    public void setNombre(String nombre) { this.nombre = nombre; }
    /**
     * Obtiene la fecha de nacimiento del usuario.
     * @return La fecha de nacimiento en formato String.
     */
    public String getFechaNacimiento() { return fechaNacimiento; }
    /**
     * Establece la fecha de nacimiento del usuario.
     * @param fechaNacimiento La nueva fecha de nacimiento en formato String.
     */
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    /**
     * Obtiene la dirección de correo electrónico del usuario.
     * @return La dirección de correo electrónico.
     */
    public String getCorreo() { return correo; }
    /**
     * Establece la dirección de correo electrónico del usuario.
     * @param correo La nueva dirección de correo electrónico.
     */
    public void setCorreo(String correo) { this.correo = correo; }
    /**
     * Obtiene el número de teléfono celular del usuario.
     * @return El número de teléfono.
     */
    public String getCelular() { return celular; }
    /**
     * Establece el número de teléfono celular del usuario.
     * @param celular El nuevo número de teléfono.
     */
    public void setCelular(String celular) { this.celular = celular; }
    /**
     * Obtiene la lista de preguntas de seguridad del usuario.
     * @return Una lista de objetos {@code PreguntaSeguridad}.
     */
    public List<PreguntaSeguridad> getPreguntasDeSeguridad() { return preguntasDeSeguridad; }
    /**
     * Establece la lista de preguntas de seguridad del usuario.
     * @param preguntasDeSeguridad La nueva lista de preguntas de seguridad.
     */
    public void setPreguntasDeSeguridad(List<PreguntaSeguridad> preguntasDeSeguridad) { this.preguntasDeSeguridad = preguntasDeSeguridad; }
}