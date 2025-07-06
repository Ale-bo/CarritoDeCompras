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

import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        // Configuración de idioma inicial (por ejemplo, español)
        Locale.setDefault(new Locale("es", "EC"));  // Por defecto, idioma español Ecuador
        MensajeInternacionalizacionHandler mensajeHandler = new MensajeInternacionalizacionHandler("es", "EC");

        // Inicializar el DAO de usuarios en memoria (para pruebas)
        UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();

        // Inicializar vistas
        LoginView loginView = new LoginView(mensajeHandler);
        RegistrarUsuarioView registrarUsuarioView = new RegistrarUsuarioView();
        ListarUsuarioView listarUsuarioView = new ListarUsuarioView(mensajeHandler);
        EliminarUsuarioView eliminarUsuarioView = new EliminarUsuarioView(mensajeHandler);
        ActualizarUsuarioView actualizarUsuarioView = new ActualizarUsuarioView(mensajeHandler);
        RecuperarContraseñaView recuperarContraseñaView = new RecuperarContraseñaView();

        // Inicializar el controlador de usuario
        UsuarioController usuarioController = new UsuarioController(
                usuarioDAO,
                loginView,
                registrarUsuarioView,
                listarUsuarioView,
                eliminarUsuarioView,
                actualizarUsuarioView,
                recuperarContraseñaView
        );

        // Mostrar la vista de login (lo primero que verá el usuario)
        loginView.setVisible(true);

        // Ejemplo de cambio de idioma dinámico desde el código
        // usuarioController.cambiarIdioma("en", "US");  // Cambiar idioma a inglés
        // usuarioController.cambiarIdioma("fr", "FR");  // Cambiar idioma a francés

        // También puedes configurar el idioma mediante un menú o preferencias
        // Por ejemplo, agregar un menú en la vista de login que permita cambiar el idioma
        // Y luego reflejar los cambios en todas las vistas.

        // Nota: Si estás conectando a una base de datos real, aquí podrías configurar la conexión
        // Conectarse a la base de datos o revisar si existe una configuración válida.

    }
}


