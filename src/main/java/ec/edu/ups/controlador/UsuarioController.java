package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.InicioDeSesion.*;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.MenuPrincipalView;
import ec.edu.ups.vista.Usuario.*;
import ec.edu.ups.util.CedulaValidator; // Importado previamente
import ec.edu.ups.util.PasswordValidator; // Importar la clase PasswordValidator
import ec.edu.ups.excepciones.ValidacionException; // Importado previamente

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private final RegistrarUsuarioView registrarFrameView;
    private final RegistroView registrarInternalView;
    private final ListarUsuarioView listarView;
    private final EliminarUsuarioView eliminarView;
    private final ActualizarUsuarioView actualizarView;
    private final MenuPrincipalView principal;
    private final MensajeInternacionalizacionHandler mensajeHandler;
    private Usuario usuarioActual;

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
            } catch (ValidacionException ex) {
                registrarInternalView.mostrarMensaje(ex.getMessage());
            }
        });
        registrarInternalView.getBtnCancelar().addActionListener(e -> cancelarRegistroInterno());

        listarView.getBtnBuscar().addActionListener(e -> buscarUsuarios());
        listarView.getBtnRefrescar().addActionListener(e -> cargarUsuarios());

        eliminarView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaEliminar());
        eliminarView.getBtnEliminar().addActionListener(e -> eliminarUsuarioSeleccionado());

        actualizarView.getBtnBuscar().addActionListener(e -> cargarUsuariosParaActualizar());
        actualizarView.getBtnActualizar().addActionListener(e -> actualizarUsuario());
        actualizarView.getTblUsuarios().getSelectionModel().addListSelectionListener(this::seleccionarUsuarioParaActualizar);
    }

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

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void abrirRegistro() {
        registrarFrameView.actualizarIdioma(mensajeHandler);
        registrarFrameView.setVisible(true);
    }

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


    private void cancelarRegistro() {
        registrarFrameView.limpiarCampos();
        registrarFrameView.dispose();
    }


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

    private void cancelarRegistroInterno() {
        registrarInternalView.limpiarCampos();
        registrarInternalView.dispose();
    }


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

    public void cargarUsuarios() {
        DefaultTableModel model = listarView.getTableModel();
        model.setRowCount(0);
        usuarioDAO.listarTodos().forEach(u -> model.addRow(new Object[]{u.getUsername(), u.getRol().toString()}));
    }

    private void buscarUsuarios() {
        String criterio = listarView.getTxtBuscar().getText().trim().toLowerCase();
        DefaultTableModel model = listarView.getTableModel();
        model.setRowCount(0);

        List<Usuario> usuariosFiltrados = usuarioDAO.listarTodos().stream()
                .filter(u -> u.getUsername().toLowerCase().contains(criterio) || u.getRol().toString().toLowerCase().contains(criterio))
                .collect(Collectors.toList());

        usuariosFiltrados.forEach(u -> model.addRow(new Object[]{u.getUsername(), u.getRol().toString()}));
    }

    public void buscarUsuarioParaEliminar() {
        String criterio = eliminarView.getTxtBuscar().getText().trim().toLowerCase();
        String filtroSeleccionado = (String) eliminarView.getCbxFiltro().getSelectedItem();
        DefaultTableModel model = eliminarView.getTableModel();
        model.setRowCount(0);

        if (filtroSeleccionado == null) return;

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

    private void eliminarUsuarioSeleccionado() {
        int fila = eliminarView.getTableUsuarios().getSelectedRow();
        if (fila >= 0) {
            String username = (String) eliminarView.getTableModel().getValueAt(fila, 0);
            if ("admin".equalsIgnoreCase(username)) {
                eliminarView.mostrarMensaje("No se puede eliminar al administrador.");
                return;
            }
            usuarioDAO.eliminar(username);
            eliminarView.mostrarMensaje("Usuario eliminado.");
            buscarUsuarioParaEliminar();
        } else {
            eliminarView.mostrarMensaje("Seleccione un usuario de la tabla para eliminar.");
        }
    }

    public void cargarUsuariosParaActualizar() {
        DefaultTableModel model = actualizarView.getTableModel();
        model.setRowCount(0);
        usuarioDAO.listarTodos().forEach(u -> model.addRow(new Object[]{u.getUsername(), u.getRol().toString()}));
    }

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
            actualizarView.getTxtUsername().setEnabled(true);
            cargarUsuariosParaActualizar();
        }
    }

    public void actualizarIdiomaEnVistasLogin() {
        if (registrarFrameView != null) {
            registrarFrameView.actualizarIdioma(mensajeHandler);
        }
        if (registrarInternalView != null) {
            registrarInternalView.actualizarIdioma();
        }
    }
}