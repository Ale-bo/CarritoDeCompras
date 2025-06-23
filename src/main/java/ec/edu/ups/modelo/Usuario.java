package ec.edu.ups.modelo;

public class Usuario {
    private String Username;
    private String contrasenia;
    private Rol rol;

    public Usuario(String contrasenia, String nombreDeUsuario, Rol rol) {
        this.contrasenia = contrasenia;
        this.Username = nombreDeUsuario;
        this.rol = rol;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
