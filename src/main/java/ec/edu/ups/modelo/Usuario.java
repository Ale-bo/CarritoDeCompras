package ec.edu.ups.modelo;

import java.util.List;

public class Usuario {
    private String username;
    private String contrasenia;
    private Rol rol;
    private String nombre;
    private String fechaNacimiento;
    private String correo;
    private String celular;
    private List<PreguntaSeguridad> preguntasDeSeguridad;

    public Usuario() {
        this.rol = Rol.USUARIO;
    }

    public Usuario(String username, String contrasenia, String nombre, String correo, String celular, String fechaNacimiento, List<PreguntaSeguridad> preguntas) {
        this.username = username;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.correo = correo;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.preguntasDeSeguridad = preguntas;
        this.rol = Rol.USUARIO;
    }

    // Getters y Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getContrasenia() { return contrasenia; }
    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
    public List<PreguntaSeguridad> getPreguntasDeSeguridad() { return preguntasDeSeguridad; }
    public void setPreguntasDeSeguridad(List<PreguntaSeguridad> preguntasDeSeguridad) { this.preguntasDeSeguridad = preguntasDeSeguridad; }
}