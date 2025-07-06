package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.InicioDeSesion.RegistrarUsuarioView;
import ec.edu.ups.vista.Usuario.ListarUsuarioView;
import ec.edu.ups.vista.Usuario.EliminarUsuarioView;
import ec.edu.ups.vista.Usuario.ActualizarUsuarioView;
import ec.edu.ups.vista.InicioDeSesion.RecuperarContraseñaView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private final RegistrarUsuarioView registrarUsuarioView;
    private final ListarUsuarioView listarUsuarioView;
    private final EliminarUsuarioView eliminarUsuarioView;
    private final ActualizarUsuarioView actualizarUsuarioView;
    private final RecuperarContraseñaView recuperarContraseñaView;

    public UsuarioController(UsuarioDAO usuarioDAO,
                             LoginView loginView,
                             RegistrarUsuarioView registrarUsuarioView,
                             ListarUsuarioView listarUsuarioView,
                             EliminarUsuarioView eliminarUsuarioView,
                             ActualizarUsuarioView actualizarUsuarioView,
                             RecuperarContraseñaView recuperarContraseñaView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.registrarUsuarioView = registrarUsuarioView;
        this.listarUsuarioView = listarUsuarioView;
        this.eliminarUsuarioView = eliminarUsuarioView;
        this.actualizarUsuarioView = actualizarUsuarioView;
        this.recuperarContraseñaView = recuperarContraseñaView;

        configurarEventos();
    }

    // Configura los eventos para los botones de la vista
    private void configurarEventos() {
        loginView.getBtnIniciarSesion().addActionListener(e -> autenticarUsuario());
        loginView.getBtnRegistrarse().addActionListener(e -> abrirRegistro());

        registrarUsuarioView.getRegistrarButton().addActionListener(e -> registrarUsuario());
        registrarUsuarioView.getCancelarButton().addActionListener(e -> cancelarRegistro());

        listarUsuarioView.getBtnBuscar().addActionListener(e -> buscarUsuarios());
        listarUsuarioView.getBtnRefrescar().addActionListener(e -> cargarUsuarios());

        eliminarUsuarioView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaEliminar());
        eliminarUsuarioView.getBtnEliminar().addActionListener(e -> eliminarUsuarioSeleccionado());

        actualizarUsuarioView.getBtnBuscar().addActionListener(e -> cargarUsuariosParaActualizar());
        actualizarUsuarioView.getBtnActualizar().addActionListener(e -> actualizarUsuario());

        recuperarContraseñaView.getAceptarButton().addActionListener(e -> recuperarContraseña());
    }

    // Método para autenticar al usuario
    private void autenticarUsuario() {
        String username = loginView.getTxtUsername().getText();
        String contrasenia = new String(loginView.getTxtContrasenia().getPassword());

        // Intentar autenticar al usuario
        Usuario usuario = usuarioDAO.autenticar(username, contrasenia);

        if (usuario == null) {
            // Si no se encuentra el usuario, mostrar mensaje de error
            loginView.mostrarMensaje("Usuario o contraseña incorrectos.");
        } else {
            // Si las credenciales son correctas, cerrar la ventana de login
            loginView.dispose();
            // Aquí podrías abrir la vista principal, por ejemplo:
            // abrirVistaPrincipal();
            JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getUsername());
        }
    }

    // Método para abrir la vista de registro de usuario
    private void abrirRegistro() {
        registrarUsuarioView.setVisible(true);  // Mostrar la ventana de registro
    }

    // Método para registrar un nuevo usuario con preguntas de seguridad aleatorias
    private void registrarUsuario() {
        String nombre = registrarUsuarioView.getTextnombre().getText();
        String nacimiento = registrarUsuarioView.getTextnacimiento().getText();
        String correo = registrarUsuarioView.getTextcorreo().getText();
        String celular = registrarUsuarioView.getTextcelular().getText();
        String username = registrarUsuarioView.getTextususario().getText();
        String contrasenia = new String(registrarUsuarioView.getPasswordcontrasena().getPassword());
        String confirmarContrasenia = new String(registrarUsuarioView.getPasswordconfcontrasena().getPassword());
        String respuestaSeguridad = registrarUsuarioView.getTextrespuesta().getText();
        String preguntaSeguridad = (String) registrarUsuarioView.getComboBoxPreguntas().getSelectedItem();

        // Validar que las contraseñas coincidan
        if (!contrasenia.equals(confirmarContrasenia)) {
            registrarUsuarioView.mostrarMensaje("Las contraseñas no coinciden.");
            return;
        }

        // Selección aleatoria de preguntas de seguridad
        List<String> preguntas = Arrays.asList("¿Cuál es tu mascota?", "¿Cuál es tu color favorito?", "¿De qué ciudad eres?");
        Collections.shuffle(preguntas);  // Mezcla las preguntas aleatoriamente

        // Crear el usuario
        Usuario usuario = new Usuario(username, contrasenia, Rol.USUARIO);  // Asumimos que el rol es 'USUARIO'
        usuarioDAO.crear(usuario);

        // Limpiar los campos de la vista
        registrarUsuarioView.limpiarCampos();
        registrarUsuarioView.mostrarMensaje("Usuario registrado exitosamente.");
    }

    // Método para cancelar el registro y limpiar la vista
    private void cancelarRegistro() {
        registrarUsuarioView.limpiarCampos();
        registrarUsuarioView.setVisible(false); // Ocultar la vista de registro
    }

    // Método para recuperar la contraseña utilizando las preguntas de seguridad
    private void recuperarContraseña() {
        String respuesta1 = recuperarContraseñaView.getTextField1().getText();
        String respuesta2 = recuperarContraseñaView.getTextField2().getText();
        String respuesta3 = recuperarContraseñaView.getTextField3().getText();

        // Validar las respuestas con las respuestas almacenadas en el sistema
        if (validarRespuestas(respuesta1, respuesta2, respuesta3)) {
            String nuevaContrasenia = new String(recuperarContraseñaView.getPasswordField1().getPassword());
            // Aquí puedes actualizar la contraseña en el sistema
            recuperarContraseñaView.mostrarMensaje("Contraseña cambiada con éxito.");
        } else {
            recuperarContraseñaView.mostrarMensaje("Respuestas incorrectas.");
        }
    }

    // Método para validar las respuestas de las preguntas de seguridad
    private boolean validarRespuestas(String respuesta1, String respuesta2, String respuesta3) {
        // Aquí debes comparar las respuestas con las respuestas correctas almacenadas
        return true;  // Asumimos que las respuestas son correctas por ahora
    }

    // Método para cargar todos los usuarios
    private void cargarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        DefaultTableModel model = (DefaultTableModel) listarUsuarioView.getTableUsuarios().getModel();
        model.setRowCount(0);  // Limpiar la tabla antes de agregar los usuarios
        for (Usuario usuario : usuarios) {
            model.addRow(new Object[]{usuario.getUsername(), usuario.getRol()});
        }
    }

    // Método para buscar usuarios
    private void buscarUsuarios() {
        String criterio = listarUsuarioView.getTxtBuscar().getText();
        List<Usuario> usuarios = usuarioDAO.listarPorRol(Rol.USUARIO);  // Buscar por rol
        DefaultTableModel model = (DefaultTableModel) listarUsuarioView.getTableUsuarios().getModel();
        model.setRowCount(0);  // Limpiar la tabla antes de mostrar los resultados
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().contains(criterio)) {
                model.addRow(new Object[]{usuario.getUsername(), usuario.getRol()});
            }
        }
    }

    // Método para buscar un usuario por criterio (nombre de usuario o rol)
    public void buscarUsuarioParaEliminar() {
        // Obtener el criterio de búsqueda (por ejemplo, nombre de usuario o rol)
        String criterio = eliminarUsuarioView.getTxtBuscar().getText();
        String filtro = (String) eliminarUsuarioView.getCbxFiltro().getSelectedItem();

        List<Usuario> usuarios = new ArrayList<>();

        if (filtro.equals("Username")) {
            // Buscar usuarios por nombre de usuario
            Usuario usuario = usuarioDAO.buscarPorUsername(criterio);
            if (usuario != null) {
                usuarios.add(usuario);  // Si se encuentra, agregarlo a la lista
            }
        } else if (filtro.equals("Rol")) {
            // Buscar usuarios por rol
            if (criterio.equalsIgnoreCase("USUARIO")) {
                usuarios = usuarioDAO.listarPorRol(Rol.USUARIO);  // Filtrar por rol 'USUARIO'
            } else if (criterio.equalsIgnoreCase("ADMINISTRADOR")) {
                usuarios = usuarioDAO.listarPorRol(Rol.ADMINISTRADOR);  // Filtrar por rol 'ADMINISTRADOR'
            }
        }

        // Limpiar la tabla antes de cargar los resultados
        DefaultTableModel model = (DefaultTableModel) eliminarUsuarioView.getTableUsuarios().getModel();
        model.setRowCount(0);  // Limpiar la tabla

        // Agregar los usuarios encontrados a la tabla
        for (Usuario usuario : usuarios) {
            model.addRow(new Object[]{usuario.getUsername(), usuario.getRol()});
        }

        // Si no se encuentra ningún usuario, mostrar un mensaje
        if (usuarios.isEmpty()) {
            eliminarUsuarioView.mostrarMensaje("No se encontraron usuarios con el criterio especificado.");
        }
    }

    // Método para eliminar un usuario seleccionado
    private void eliminarUsuarioSeleccionado() {
        int row = eliminarUsuarioView.getTableUsuarios().getSelectedRow();
        if (row != -1) {
            String username = (String) eliminarUsuarioView.getTableUsuarios().getValueAt(row, 0);
            usuarioDAO.eliminar(username);
            eliminarUsuarioView.mostrarMensaje("Usuario eliminado exitosamente.");
            buscarUsuarioParaEliminar();  // Refrescar la lista de usuarios
        } else {
            eliminarUsuarioView.mostrarMensaje("Selecciona un usuario para eliminar.");
        }
    }

    // Método para cargar los datos del usuario para actualizar
    private void cargarUsuariosParaActualizar() {
        int row = actualizarUsuarioView.getTblUsuarios().getSelectedRow();
        if (row != -1) {
            String username = (String) actualizarUsuarioView.getTblUsuarios().getValueAt(row, 0);
            Usuario usuario = usuarioDAO.buscarPorUsername(username);
            if (usuario != null) {
                actualizarUsuarioView.getTxtUsername().setText(usuario.getUsername());
                actualizarUsuarioView.getTxtPassword().setText(usuario.getContrasenia());
                actualizarUsuarioView.getTxtPassword().setText(usuario.getContrasenia());
                actualizarUsuarioView.getCbxRol().setSelectedItem(usuario.getRol().toString());
            }
        }
    }

    // Método para actualizar un usuario
    private void actualizarUsuario() {
        String username = actualizarUsuarioView.getTxtUsername().getText();
        String contrasenia = new String(actualizarUsuarioView.getTxtPassword().getPassword());
        String confirmarContrasenia = new String(actualizarUsuarioView.getPasswordconfcontrasenia().getPassword());

        if (!contrasenia.equals(confirmarContrasenia)) {
            actualizarUsuarioView.mostrarMensaje("Las contraseñas no coinciden.");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        if (usuario != null) {
            usuario.setContrasenia(contrasenia);
            usuarioDAO.actualizar(usuario);
            actualizarUsuarioView.mostrarMensaje("Usuario actualizado exitosamente.");
        } else {
            actualizarUsuarioView.mostrarMensaje("Usuario no encontrado.");
        }
    }
}


