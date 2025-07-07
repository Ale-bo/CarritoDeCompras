package ec.edu.ups;

import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.InicioDeSesion.RecuperarContraseñaView;
import ec.edu.ups.vista.InicioDeSesion.RegistrarUsuarioView;
import ec.edu.ups.vista.Usuario.ListarUsuarioView;
import ec.edu.ups.vista.Usuario.EliminarUsuarioView;
import ec.edu.ups.vista.Usuario.ActualizarUsuarioView;

import javax.swing.*;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        // 1. Configuración del idioma
        Locale.setDefault(new Locale("es", "EC"));
        MensajeInternacionalizacionHandler mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");

        // 2. Inicialización del DAO (Capa de acceso a datos)
        UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();

        // 3. Inicialización de todas las vistas
        LoginView loginView = new LoginView();
        RegistrarUsuarioView registrarUsuarioView = new RegistrarUsuarioView();
        ListarUsuarioView listarUsuarioView = new ListarUsuarioView(mensajeHandler);
        EliminarUsuarioView eliminarUsuarioView = new EliminarUsuarioView(mensajeHandler);
        ActualizarUsuarioView actualizarUsuarioView = new ActualizarUsuarioView(mensajeHandler);
        RecuperarContraseñaView recuperarContraseñaView = new RecuperarContraseñaView();

        // 4. Inicialización del controlador principal
        UsuarioController usuarioController = new UsuarioController(
                usuarioDAO,
                loginView,
                registrarUsuarioView,
                listarUsuarioView,
                eliminarUsuarioView,
                actualizarUsuarioView,
                recuperarContraseñaView
        );

        // 5. Mostrar la ventana de inicio de sesión
        loginView.setVisible(true);
    }
    private static void mostrarVentana(MenuPrincipalView principal, JInternalFrame ventana) {
        if (!ventana.isVisible()) {
            if (ventana.getParent() == null) {
                principal.getDesktopPane().add(ventana);
            }
            ventana.setVisible(true);
        }
        ventana.toFront();
    }
}



