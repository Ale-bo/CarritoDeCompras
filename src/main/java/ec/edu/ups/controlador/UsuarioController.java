package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.InicioDeSesion.*;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.MenuPrincipalView;
import ec.edu.ups.vista.Usuario.*;
import ec.edu.ups.util.CedulaValidator;
import ec.edu.ups.util.PasswordValidator;
import ec.edu.ups.excepciones.ValidacionException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Controlador que gestiona las operaciones relacionadas con los usuarios.
 * Incluye lógica para autenticación, registro, recuperación de contraseña y gestión CRUD de usuarios.
 *
 * @author Ivanna Alexandra Nievecela Pérez
 * @version 1.0
 */
public class UsuarioController {

    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private final RegistrarUsuarioView registrarFrameView;
    private final RegistroView registrarInternalView;
    private final ListarUsuarioView listarView;
    private final EliminarUsuarioView eliminarView;
    private final ActualizarUsuarioView actualizarView; // Vista para modificar usuario
    private final MenuPrincipalView principal;
    private final MensajeInternacionalizacionHandler mensajeHandler;
    private Usuario usuarioActual;

    /**
     * Constructor del controlador de Usuario.
     * Inyecta las dependencias necesarias (DAO, vistas, manejador de mensajes).
     *
     * @param uDAO El objeto DAO para el acceso a datos de Usuario.
     * @param lV La vista de Login.
     * @param rV La vista de registro de usuario (JFrame).
     * @param liV La vista para listar usuarios.
     * @param dV La vista para eliminar usuarios.
     * @param upV La vista para actualizar usuarios.
     * @param pV La vista principal del menú.
     * @param msg El manejador de mensajes para internacionalización.
     * @param internalRegView La vista de registro de usuario (JInternalFrame).
     */
    public UsuarioController(UsuarioDAO uDAO, LoginView lV, RegistrarUsuarioView rV,
                             ListarUsuarioView liV, EliminarUsuarioView dV, ActualizarUsuarioView upV,
                             MenuPrincipalView pV, MensajeInternacionalizacionHandler msg,
                             RegistroView internalRegView) {
        this.usuarioDAO = uDAO;
        this.loginView = lV;
        this.registrarFrameView = rV;
        this.registrarInternalView = internalRegView;
        this.listarView = liV;
        this.eliminarView = dV;
        this.actualizarView = upV;
        this.principal = pV;
        this.mensajeHandler = msg;
        configurarEventos();
    }

    /**
     * Configura los ActionListeners para los botones de las vistas de usuario.
     */
    private void configurarEventos() {
        loginView.getBtnIniciarSesion().addActionListener(e -> autenticarUsuario());
        loginView.getBtnRegistrarse().addActionListener(e -> abrirRegistro());
        loginView.getBtnOlvidar().addActionListener(e -> abrirRecuperacion());

        registrarFrameView.getRegistrarButton().addActionListener(e -> {
            try {
                registrarUsuario();
            } catch (ValidacionException ex) {
                registrarFrameView.mostrarMensaje(ex.getMessage());
            }
        });
        registrarFrameView.getCancelarButton().addActionListener(e -> cancelarRegistro());

        registrarInternalView.getBtnCrear().addActionListener(e -> {
            try {
                registrarUsuarioInterno();
            }
            // Los catch blocks deben estar aquí
            catch (ValidacionException ex) { // Captura la excepción de validación
                registrarInternalView.mostrarMensaje(ex.getMessage());
            }
        });
        registrarInternalView.getBtnCancelar().addActionListener(e -> cancelarRegistroInterno());

        listarView.getBtnBuscar().addActionListener(e -> buscarUsuarios());
        listarView.getBtnRefrescar().addActionListener(e -> cargarUsuarios());

        eliminarView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaEliminar());
        eliminarView.getBtnEliminar().addActionListener(e -> eliminarUsuarioSeleccionado());

        // --- CONECTAR EL BOTÓN 'Buscar' de ActualizarUsuarioView a la búsqueda específica ---
        actualizarView.getBtnBuscar().addActionListener(e -> buscarUsuarioEspecificoParaActualizar());
        // --- FIN CONEXIÓN ---

        // El botón btnBuscarPorUsername (si lo habías añadido) ya no sería necesario
        // ya que repurponemos el botón "Buscar" existente. Si lo añadiste en el .form,
        // puedes eliminarlo para mantener la interfaz limpia.
        // Si no lo eliminas del form, solo asegúrate de que no tenga un ActionListener.
        // actualizarView.getBtnBuscarPorUsername().addActionListener(e -> buscarUsuarioEspecificoParaActualizar()); // COMENTA O ELIMINA ESTA LÍNEA

        actualizarView.getBtnActualizar().addActionListener(e -> actualizarUsuario());
        actualizarView.getBtnCancelar().addActionListener(e -> actualizarView.limpiarCampos());
        actualizarView.getTblUsuarios().getSelectionModel().addListSelectionListener(this::seleccionarUsuarioParaActualizar);
    }

    /**
     * Autentica a un usuario con el nombre de usuario y contraseña proporcionados.
     * @return El objeto Usuario del usuario actual.
     */
    public void autenticarUsuario() {
        String usr = loginView.getTxtUsername().getText().trim();
        String pwd = new String(loginView.getTxtContrasenia().getPassword());
        Usuario u = usuarioDAO.autenticar(usr, pwd);
        if (u != null) {
            this.usuarioActual = u;
            loginView.dispose();
        } else {
            loginView.mostrarMensaje(mensajeHandler.get("usuario.error.incorrecto"));
        }
    }

    /**
     * Obtiene el usuario actualmente autenticado.
     * @return El objeto Usuario del usuario actual.
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Abre la vista de registro de usuario (JFrame) para el registro inicial.
     */
    public void abrirRegistro() {
        registrarFrameView.actualizarIdioma(mensajeHandler);
        registrarFrameView.setVisible(true);
    }

    /**
     * Abre el diálogo de recuperación de contraseña, solicitando el nombre de usuario y presentando
     * una pregunta de seguridad aleatoria.
     */
    public void abrirRecuperacion() {
        String usr = JOptionPane.showInputDialog(loginView, mensajeHandler.get("login.label.usuario"));
        if (usr == null || usr.trim().isEmpty()) return;

        Usuario u = usuarioDAO.buscarPorUsername(usr.trim());
        if (u != null && u.getPreguntasDeSeguridad() != null && !u.getPreguntasDeSeguridad().isEmpty()) {
            RecuperarContraseñaView recuperarDialog = new RecuperarContraseñaView(loginView, mensajeHandler);

            Random rand = new Random();
            PreguntaSeguridad p = u.getPreguntasDeSeguridad().get(rand.nextInt(u.getPreguntasDeSeguridad().size()));

            recuperarDialog.setUsername(u.getUsername());
            recuperarDialog.setCorreo(u.getCorreo());
            recuperarDialog.setPregunta(mensajeHandler.get("preguntas.seguridad." + p.getPreguntaId()), p.getPreguntaId());

            recuperarDialog.addRecuperarListener(e -> recuperarContraseña(recuperarDialog, u));
            recuperarDialog.addCancelarListener(e -> recuperarDialog.dispose());
            recuperarDialog.setVisible(true);
        } else {
            loginView.mostrarMensaje(mensajeHandler.get("usuario.error.no_encontrado"));
        }
    }

    /**
     * Registra un nuevo usuario desde el JFrame de registro inicial (RegistrarUsuarioView).
     * Realiza validaciones de cédula y contraseña, y persiste el usuario.
     * @throws ValidacionException Si alguna validación falla.
     */
    private void registrarUsuario() throws ValidacionException {
        registrarFrameView.validarCampos(); // Valida campos obligatorios, formatos, etc.

        String username = registrarFrameView.getTextususario().getText().trim();
        // Validación de cédula
        if (!CedulaValidator.isValidCedula(username)) {
            throw new ValidacionException("La cédula ingresada como usuario no es válida.");
        }

        String pwd1 = new String(registrarFrameView.getPasswordcontrasena().getPassword());
        // Validación de contraseña segura
        if (!PasswordValidator.isValidPassword(pwd1)) {
            throw new ValidacionException("La contraseña no cumple los requisitos de seguridad: mínimo 6 caracteres, al menos una mayúscula, una minúscula y uno de '@', '_', '-'.");
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            throw new ValidacionException("El nombre de usuario (cédula) ya existe. Por favor, elija otro.");
        }


        List<PreguntaSeguridad> preguntas = new ArrayList<>();
        preguntas.add(new PreguntaSeguridad(registrarFrameView.getPreguntasIdsSeleccionadas().get(0), registrarFrameView.getRespuesta1()));
        preguntas.add(new PreguntaSeguridad(registrarFrameView.getPreguntasIdsSeleccionadas().get(1), registrarFrameView.getRespuesta2()));
        preguntas.add(new PreguntaSeguridad(registrarFrameView.getPreguntasIdsSeleccionadas().get(2), registrarFrameView.getRespuesta3()));

        String fecha = registrarFrameView.getTextDia().getText() + "/" + registrarFrameView.getTextMes().getText() + "/" + registrarFrameView.getTextAnio().getText();

        Usuario nuevo = new Usuario(username, pwd1,
                registrarFrameView.getTextnombre().getText().trim(),
                registrarFrameView.getTextcorreo().getText().trim(),
                registrarFrameView.getTextcelular().getText().trim(),
                fecha, preguntas);

        usuarioDAO.crear(nuevo);
        registrarFrameView.mostrarMensaje("Usuario registrado con éxito.");
        cancelarRegistro();
    }

    /**
     * Cancela el proceso de registro en el JFrame de registro inicial, limpia los campos y cierra la vista.
     */
    private void cancelarRegistro() {
        registrarFrameView.limpiarCampos();
        registrarFrameView.dispose();
    }

    /**
     * Registra un nuevo usuario desde el JInternalFrame de registro (RegistroView).
     * Realiza validaciones de cédula y contraseña, y persiste el usuario.
     * @throws ValidacionException Si alguna validación falla.
     */
    private void registrarUsuarioInterno() throws ValidacionException {
        registrarInternalView.validarCampos(); // Valida campos obligatorios, formatos, etc.

        String username = registrarInternalView.getTxtUsuario().getText().trim();
        // Validación de cédula
        if (!CedulaValidator.isValidCedula(username)) {
            throw new ValidacionException("La cédula ingresada como usuario no es válida.");
        }

        String pwd1 = new String(registrarInternalView.getTxtPassword().getPassword());
        // Validación de contraseña segura
        if (!PasswordValidator.isValidPassword(pwd1)) {
            throw new ValidacionException("La contraseña no cumple los requisitos de seguridad: mínimo 6 caracteres, al menos una mayúscula, una minúscula y uno de '@', '_', '-'.");
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            throw new ValidacionException("El nombre de usuario (cédula) ya existe. Por favor, elija otro.");
        }


        List<PreguntaSeguridad> preguntas = new ArrayList<>();

        if (registrarInternalView.getPreguntasIdsSeleccionadas() != null && registrarInternalView.getPreguntasIdsSeleccionadas().size() >= 3) {
            preguntas.add(new PreguntaSeguridad(registrarInternalView.getPreguntasIdsSeleccionadas().get(0), registrarInternalView.getTxtRespuesta1().getText().trim()));
            preguntas.add(new PreguntaSeguridad(registrarInternalView.getPreguntasIdsSeleccionadas().get(1), registrarInternalView.getTxtRespuesta2().getText().trim()));
            preguntas.add(new PreguntaSeguridad(registrarInternalView.getPreguntasIdsSeleccionadas().get(2), registrarInternalView.getTxtRespuesta3().getText().trim()));
        } else {
            throw new ValidacionException("Debe seleccionar y responder las preguntas de seguridad.");
        }

        int dia = (Integer) registrarInternalView.getSpnDia().getValue();
        int mes = (Integer) registrarInternalView.getSpnMes().getValue();
        int anio = (Integer) registrarInternalView.getSpnAño().getValue();
        String fechaNacimiento = String.format("%02d/%02d/%04d", dia, mes, anio);

        Usuario nuevo = new Usuario(username, pwd1,
                registrarInternalView.getTxtNombresComp().getText().trim(),
                registrarInternalView.getTxtCorreo().getText().trim(),
                registrarInternalView.getTxtTelefono().getText().trim(),
                fechaNacimiento, preguntas);

        usuarioDAO.crear(nuevo);
        registrarInternalView.mostrarMensaje("Usuario registrado con éxito.");
        cancelarRegistroInterno();
    }

    /**
     * Cancela el proceso de registro en el JInternalFrame de registro, limpia los campos y cierra la vista.
     */
    private void cancelarRegistroInterno() {
        registrarInternalView.limpiarCampos();
        registrarInternalView.dispose();
    }

    /**
     * Permite la recuperación de contraseña de un usuario mediante una pregunta de seguridad.
     * @param recuperarDialog El diálogo de recuperación de contraseña.
     * @param u El usuario para el cual se intenta recuperar la contraseña.
     */
    private void recuperarContraseña(RecuperarContraseñaView recuperarDialog, Usuario u) {
        if (u == null) return;

        boolean ok = u.getPreguntasDeSeguridad().stream()
                .anyMatch(p -> p.getPreguntaId() == recuperarDialog.getPreguntaIdActual() && p.getRespuesta().equalsIgnoreCase(recuperarDialog.getRespuesta1()));

        if (ok) {
            String nPwd = JOptionPane.showInputDialog(recuperarDialog, "Ingrese la nueva contraseña:");
            if (nPwd != null && !nPwd.trim().isEmpty()) {
                // Validación de contraseña segura al recuperar/cambiar
                if (!PasswordValidator.isValidPassword(nPwd)) {
                    recuperarDialog.mostrarMensaje("La nueva contraseña no cumple los requisitos de seguridad: mínimo 6 caracteres, al menos una mayúscula, una minúscula y uno de '@', '_', '-'.");
                    return; // No actualiza si la contraseña no es válida
                }
                u.setContrasenia(nPwd);
                usuarioDAO.actualizar(u);
                recuperarDialog.mostrarMensaje("Contraseña actualizada.");
                recuperarDialog.dispose();
            }
        } else {
            recuperarDialog.mostrarMensaje("Respuesta incorrecta.");
        }
    }

    /**
     * Carga todos los usuarios registrados en la tabla de la vista de listado.
     * Si no hay usuarios, muestra un mensaje indicándolo.
     */
    public void cargarUsuarios() {
        DefaultTableModel model = listarView.getTableModel();
        model.setRowCount(0); // Limpia la tabla antes de cargar

        List<Usuario> todosLosUsuarios = usuarioDAO.listarTodos(); // Obtiene todos los usuarios

        if (todosLosUsuarios.isEmpty()) {
            listarView.mostrarMensaje("No hay usuarios registrados en el sistema.");
        } else {
            for (Usuario u : todosLosUsuarios) {
                model.addRow(new Object[]{u.getUsername(), u.getRol().toString()});
            }
        }
    }

    /**
     * Busca usuarios según el criterio de búsqueda y muestra los resultados en la tabla de la vista de listado.
     */
    private void buscarUsuarios() {
        String criterio = listarView.getTxtBuscar().getText().trim().toLowerCase();
        DefaultTableModel model = listarView.getTableModel();
        model.setRowCount(0); // Limpia la tabla antes de la búsqueda

        // Si el criterio de búsqueda está vacío, carga todos los usuarios
        if (criterio.isEmpty()) {
            cargarUsuarios(); // Reutiliza el método cargarUsuarios para listar todos si la búsqueda es vacía
            return;
        }

        List<Usuario> usuariosFiltrados = usuarioDAO.listarTodos().stream()
                .filter(u -> u.getUsername().toLowerCase().contains(criterio) || u.getRol().toString().toLowerCase().contains(criterio))
                .collect(Collectors.toList());

        if (usuariosFiltrados.isEmpty()) {
            listarView.mostrarMensaje("No se encontraron usuarios que coincidan con el criterio: '" + criterio + "'.");
        } else {
            usuariosFiltrados.forEach(u -> model.addRow(new Object[]{u.getUsername(), u.getRol().toString()}));
        }
    }

    /**
     * Busca usuarios para eliminar según el filtro seleccionado (username o rol).
     * Muestra los resultados en la tabla de la vista de eliminación.
     */
    public void buscarUsuarioParaEliminar() {
        String criterio = eliminarView.getTxtBuscar().getText().trim().toLowerCase();
        String filtroSeleccionado = (String) eliminarView.getCbxFiltro().getSelectedItem();
        DefaultTableModel model = eliminarView.getTableModel();
        model.setRowCount(0);

        if (filtroSeleccionado == null || criterio.isEmpty()) { // Añadir validación para criterio vacío
            eliminarView.mostrarMensaje("Por favor, ingrese un criterio de búsqueda.");
            return;
        }

        List<Usuario> usuariosEncontrados = usuarioDAO.listarTodos().stream()
                .filter(usuario -> {
                    if (filtroSeleccionado.equals(mensajeHandler.get("usuario.view.eliminar.filtro.username"))) {
                        return usuario.getUsername().toLowerCase().contains(criterio);
                    } else if (filtroSeleccionado.equals(mensajeHandler.get("usuario.view.eliminar.filtro.rol"))) {
                        return usuario.getRol().toString().toLowerCase().contains(criterio);
                    }
                    return false;
                })
                .collect(Collectors.toList());

        if (usuariosEncontrados.isEmpty()) {
            eliminarView.mostrarMensaje("No se encontraron usuarios con ese criterio.");
        } else {
            usuariosEncontrados.forEach(u -> model.addRow(new Object[]{u.getUsername(), u.getRol().toString()}));
        }
    }


    /**
     * Elimina el usuario seleccionado de la tabla en la vista de eliminación.
     */
    public void eliminarUsuarioSeleccionado() {
        int fila = eliminarView.getTableUsuarios().getSelectedRow();
        if (fila >= 0) {
            String username = (String) eliminarView.getTableModel().getValueAt(fila, 0);
            if ("admin".equalsIgnoreCase(username)) {
                eliminarView.mostrarMensaje("No se puede eliminar al administrador.");
                return;
            }
            usuarioDAO.eliminar(username);
            eliminarView.mostrarMensaje("Usuario eliminado.");
            buscarUsuarioParaEliminar(); // Recarga la tabla después de eliminar
        } else {
            eliminarView.mostrarMensaje("Seleccione un usuario de la tabla para eliminar.");
        }
    }

    /**
     * Carga todos los usuarios registrados en la tabla de la vista de actualización.
     */
    public void cargarUsuariosParaActualizar() {
        DefaultTableModel model = actualizarView.getTableModel();
        model.setRowCount(0);
        usuarioDAO.listarTodos().forEach(u -> model.addRow(new Object[]{u.getUsername(), u.getRol().toString()}));
    }

    /**
     * --- NUEVO MÉTODO: Buscar un usuario específico para actualizar ---
     * Busca un usuario por el username ingresado en el campo de texto y lo muestra en la tabla.
     */
    private void buscarUsuarioEspecificoParaActualizar() {
        String usernameBuscar = actualizarView.getTxtUsername().getText().trim();
        DefaultTableModel model = actualizarView.getTableModel();
        model.setRowCount(0); // Limpia la tabla

        if (usernameBuscar.isEmpty()) {
            actualizarView.mostrarMensaje("Por favor, ingrese el nombre de usuario a buscar.");
            actualizarView.limpiarCampos(); // Limpia y re-habilita si está vacío
            return;
        }

        Usuario u = usuarioDAO.buscarPorUsername(usernameBuscar);

        if (u != null) {
            model.addRow(new Object[]{u.getUsername(), u.getRol().toString()});
            actualizarView.getTxtUsername().setEnabled(false); // Deshabilita la edición del username
            actualizarView.getTxtPassword().setText("");
            actualizarView.getPasswordconfcontrasenia().setText("");
        } else {
            actualizarView.mostrarMensaje("Usuario '" + usernameBuscar + "' no encontrado.");
            actualizarView.limpiarCampos(); // Limpia los campos si no se encuentra
        }
    }

    /**
     * Selecciona un usuario de la tabla en la vista de actualización y carga sus datos en los campos de texto.
     * @param e El evento de selección de lista.
     */
    private void seleccionarUsuarioParaActualizar(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int fila = actualizarView.getTblUsuarios().getSelectedRow();
            if (fila != -1) {
                String username = (String) actualizarView.getTableModel().getValueAt(fila, 0);
                Usuario u = usuarioDAO.buscarPorUsername(username);
                if (u != null) {
                    actualizarView.getTxtUsername().setText(u.getUsername());
                    actualizarView.getTxtUsername().setEnabled(false);
                    actualizarView.getTxtPassword().setText("");
                    actualizarView.getPasswordconfcontrasenia().setText("");
                }
            }
        }
    }

    /**
     * Actualiza los datos de un usuario existente a partir de los datos ingresados en la vista.
     */
    private void actualizarUsuario() {
        String username = actualizarView.getTxtUsername().getText();
        String pass1 = new String(actualizarView.getTxtPassword().getPassword());
        String pass2 = new String(actualizarView.getPasswordconfcontrasenia().getPassword());

        // Validar contraseñas al actualizar también
        try {
            if (pass1.isEmpty() || pass2.isEmpty()) {
                throw new ValidacionException("Los campos de contraseña no pueden estar vacíos.");
            }
            if (!pass1.equals(pass2)) {
                throw new ValidacionException("Las contraseñas no coinciden.");
            }
            if (!PasswordValidator.isValidPassword(pass1)) {
                throw new ValidacionException("La nueva contraseña no cumple los requisitos de seguridad: mínimo 6 caracteres, al menos una mayúscula, una minúscula y uno de '@', '_', '-'.");
            }
        } catch (ValidacionException ex) {
            actualizarView.mostrarMensaje(ex.getMessage());
            return;
        }

        Usuario u = usuarioDAO.buscarPorUsername(username);
        if (u != null) {
            u.setContrasenia(pass1);
            usuarioDAO.actualizar(u);
            actualizarView.mostrarMensaje("Usuario actualizado con éxito.");
            actualizarView.limpiarCampos();
            actualizarView.getTxtUsername().setEnabled(true); // Re-habilitar el campo username después de actualizar
            cargarUsuariosParaActualizar(); // Recargar la tabla si es necesario
        } else {
            actualizarView.mostrarMensaje("Error: Usuario a actualizar no encontrado. Recargue la vista.");
            actualizarView.limpiarCampos();
        }
    }

    /**
     * Actualiza el idioma de las vistas de login y registro.
     */
    public void actualizarIdiomaEnVistasLogin() {
        if (registrarFrameView != null) {
            registrarFrameView.actualizarIdioma(mensajeHandler);
        }
        if (registrarInternalView != null) {
            registrarInternalView.actualizarIdioma();
        }
    }
}