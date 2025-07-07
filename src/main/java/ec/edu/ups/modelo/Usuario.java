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
    private String preguntaSeguridad;
    private String respuestaSeguridad;
    private List<String> preguntasSeguridad;
    private List<String> respuestasSeguridad;

    public Usuario() {

    }

    public Usuario(String celular, String contrasenia, String correo, String fechaNacimiento, String nombre, String preguntaSeguridad, String respuestaSeguridad, Rol rol, String username, List<String> preguntasSeguridad, List<String> respuestasSeguridad) {
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.nombre = nombre;
        this.preguntaSeguridad = preguntaSeguridad;
        this.respuestaSeguridad = respuestaSeguridad;
        this.rol = rol;
        this.username = username;
        this.preguntasSeguridad = preguntasSeguridad;
        this.respuestasSeguridad = respuestasSeguridad;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celualr) {
        this.celular = celualr;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPreguntaSeguridad() {
        return preguntaSeguridad;
    }

    public void setPreguntaSeguridad(String preguntaSeguridad) {
        this.preguntaSeguridad = preguntaSeguridad;
    }

    public String getRespuestaSeguridad() {
        return respuestaSeguridad;
    }

    public void setRespuestaSeguridad(String respuestaSeguridad) {
        this.respuestaSeguridad = respuestaSeguridad;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public List<String> getPreguntasSeguridad() {
        return preguntasSeguridad;
    }
    public void setPreguntasSeguridad(List<String> preguntasSeguridad) {
        this.preguntasSeguridad = preguntasSeguridad;
    }
    public List<String> getRespuestasSeguridad() {
        return respuestasSeguridad;
    }
    public void setRespuestasSeguridad(List<String> respuestasSeguridad) {
        this.respuestasSeguridad = respuestasSeguridad;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "celualr='" + celular + '\'' +
                ", username='" + username + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", rol=" + rol +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", correo='" + correo + '\'' +
                ", preguntaSeguridad='" + preguntaSeguridad + '\'' +
                ", respuestaSeguridad='" + respuestaSeguridad + '\'' +
                ", preguntasSeguridad=" + preguntasSeguridad +
                ", respuestasSeguridad=" + respuestasSeguridad +
                '}';
    }
}
