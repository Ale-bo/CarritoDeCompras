package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.InicioDeSesion.CambiarContraseñaView;
import ec.edu.ups.vista.InicioDeSesion.RecuperarContraseñaView;
import ec.edu.ups.vista.InicioDeSesion.RegistrarUsuarioView;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.MenuPrincipalView;
import ec.edu.ups.vista.Usuario.ActualizarUsuarioView;
import ec.edu.ups.vista.Usuario.EliminarUsuarioView;
import ec.edu.ups.vista.Usuario.ListarUsuarioView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private final RegistrarUsuarioView registrarView;
    private final ListarUsuarioView listarView;
    private final EliminarUsuarioView eliminarView;
    private final ActualizarUsuarioView actualizarView;
    private final RecuperarContraseñaView recuperarView;
    private final MenuPrincipalView principal;
    private final MensajeInternacionalizacionHandler mensajeHandler;
    private Usuario usuarioActual;

    private List<Integer> preguntasIdSeleccionadas;
    private int indicePreguntaRecuperacion;

    public UsuarioController(
            UsuarioDAO usuarioDAO,
            LoginView loginView,
            RegistrarUsuarioView registrarView,
            ListarUsuarioView listarView,
            EliminarUsuarioView eliminarView,
            ActualizarUsuarioView actualizarView,
            RecuperarContraseñaView recuperarView,
            MenuPrincipalView principal,
            MensajeInternacionalizacionHandler mensajeHandler
    ) {
        this.usuarioDAO     = usuarioDAO;
        this.loginView      = loginView;
        this.registrarView  = registrarView;
        this.listarView     = listarView;
        this.eliminarView   = eliminarView;
        this.actualizarView = actualizarView;
        this.recuperarView  = recuperarView;
        this.principal      = principal;
        this.mensajeHandler = mensajeHandler;

        preguntasIdSeleccionadas = new ArrayList<>();
        configurarEventos();
    }

    private void configurarEventos() {

        loginView.getBtnIniciarSesion().addActionListener(e -> autenticarUsuario());
        loginView.getBtnRegistrarse()    .addActionListener(e -> abrirRegistro());
        loginView.getBtnOlvidar()        .addActionListener(e -> abrirRecuperacion());

        registrarView.getRegistrarButton().addActionListener(e -> registrarUsuario());
        registrarView.getCancelarButton()  .addActionListener(e -> cancelarRegistro());

        listarView.getBtnBuscar().addActionListener(e -> buscarUsuarios());
        listarView.getBtnRefrescar().addActionListener(e -> cargarUsuarios());

        eliminarView.getBtnBuscar()  .addActionListener(e -> buscarUsuarioParaEliminar());
        eliminarView.getBtnEliminar().addActionListener(e -> eliminarUsuarioSeleccionado());

        actualizarView.getBtnBuscar()   .addActionListener(e -> cargarUsuariosParaActualizar());
        actualizarView.getBtnActualizar().addActionListener(e -> actualizarUsuario());

        recuperarView.addRecuperarListener(e -> recuperarContraseña());
        recuperarView.addCancelarListener(e -> recuperarView.dispose());
    }

    public void actualizarTodosLosIdiomas() {

        loginView.actualizarIdioma();
        SwingUtilities.updateComponentTreeUI(loginView);

        registrarView.actualizarIdioma(mensajeHandler);
        SwingUtilities.updateComponentTreeUI(registrarView);

        listarView.actualizarIdioma();
        SwingUtilities.updateComponentTreeUI(listarView);

        eliminarView.actualizarIdioma();
        SwingUtilities.updateComponentTreeUI(eliminarView);

        actualizarView.actualizarIdioma();
        SwingUtilities.updateComponentTreeUI(actualizarView);

        recuperarView.actualizarIdioma(mensajeHandler);
        SwingUtilities.updateComponentTreeUI(recuperarView);

        Locale loc = mensajeHandler.getLocale();
        principal.cambiarIdioma(loc.getLanguage(), loc.getCountry());
        SwingUtilities.updateComponentTreeUI(principal);
    }

    public void autenticarUsuario() {
        String usr = loginView.getTxtUsername().getText().trim();
        String pwd = new String(loginView.getTxtContrasenia().getPassword());
        Usuario u = usuarioDAO.autenticar(usr, pwd);
        if (u == null) {
            loginView.mostrarMensaje(mensajeHandler.get("usuario.error.incorrecto"));
        } else {
            this.usuarioActual = u;
            loginView.dispose();
        }
    }

    public void abrirRegistro() {
        preguntasIdSeleccionadas.clear();
        registrarView.actualizarIdioma(mensajeHandler);
        registrarView.setVisible(true);
    }

    public void abrirRecuperacion() {
        String usr = loginView.getTxtUsername().getText().trim();
        Usuario u = usuarioDAO.buscarPorUsername(usr);
        if (u == null) {
            loginView.mostrarMensaje(mensajeHandler.get("usuario.error.no_encontrado"));
            return;
        }
        recuperarView.setUsername(u.getUsername());
        recuperarView.setCorreo(u.getCorreo());
        recuperarView.actualizarIdioma(mensajeHandler);
        recuperarView.setVisible(true);
    }

    public void actualizarIdiomaEnVistasLogin() {
        registrarView.actualizarIdioma(mensajeHandler);
        SwingUtilities.updateComponentTreeUI(registrarView);

        recuperarView.actualizarIdioma(mensajeHandler);
        SwingUtilities.updateComponentTreeUI(recuperarView);

        Locale loc = mensajeHandler.getLocale();
        principal.cambiarIdioma(loc.getLanguage(), loc.getCountry());
        SwingUtilities.updateComponentTreeUI(principal);
    }

    private void cancelarRegistro() {
        registrarView.limpiarCampos();
        registrarView.setVisible(false);
    }

    private void registrarUsuario() {
        String username       = registrarView.getTextususario().getText().trim();
        String password       = new String(registrarView.getPasswordcontrasena().getPassword());
        String correo         = registrarView.getTextcorreo().getText().trim();
        String celular        = registrarView.getTextcelular().getText().trim();
        String nombreCompleto = registrarView.getTextnombre().getText().trim();
        String respuesta1     = registrarView.getRespuesta1().trim();
        String respuesta2     = registrarView.getRespuesta2().trim();
        String respuesta3     = registrarView.getRespuesta3().trim();

        List<Integer> ids = obtenerIdsPreguntasSeleccionadas();
        preguntasIdSeleccionadas = ids;

        if (username.isEmpty() || password.isEmpty()
                || respuesta1.isEmpty() || respuesta2.isEmpty() || respuesta3.isEmpty()) {
            registrarView.mostrarMensaje("Debes llenar todos los campos.");
            return;
        }

        Usuario usuario = new Usuario(
                username,
                password,
                nombreCompleto,
                correo,
                celular,
                ids,
                Arrays.asList(respuesta1, respuesta2, respuesta3)
        );
        usuarioDAO.crear(usuario);

        registrarView.mostrarMensaje("Usuario registrado con éxito");
        registrarView.limpiarCampos();
        registrarView.setVisible(false);
    }

    private List<Integer> obtenerIdsPreguntasSeleccionadas() {
        List<Integer> ids = new ArrayList<>();
        List<String> todas = Arrays.asList(
                "1. ¿Cuál es el nombre de tu primer Novio?",
                "2. ¿Cuál es tu color Favorito?",
                "3. ¿Qué comida te gusta más?",
                "4. ¿Cómo se llama tu primera mascota?",
                "5. ¿Cuál es tu bebida favorita?",
                "6. ¿Cuál es el nombre de tu Escuela?",
                "7. ¿Cómo se llama tu mejor amigo?",
                "8. ¿Cuál es el segundo nombre de tu Madre?",
                "9. ¿De dónde son tus abuelos?",
                "10. ¿Qué ciudad te gustaría visitar?"
        );
        @SuppressWarnings("unchecked")
        JComboBox<String>[] combos = new JComboBox[]{
                registrarView.comboPreguntasSeguridad1,
                registrarView.comboPreguntasSeguridad2,
                registrarView.comboPreguntasSeguridad3
        };
        for (JComboBox<String> combo : combos) {
            String sel = (String) combo.getSelectedItem();
            for (int i = 0; i < todas.size(); i++) {
                if (sel != null && sel.startsWith((i+1) + ".")) {
                    ids.add(i+1);
                    break;
                }
            }
        }
        return ids;
    }

    private void cargarUsuarios() {
        DefaultTableModel m = (DefaultTableModel) listarView.getTableUsuarios().getModel();
        m.setRowCount(0);
        usuarioDAO.listarTodos().forEach(u -> m.addRow(new Object[]{u.getUsername(), u.getRol()}));
    }

    private void buscarUsuarios() {
        String crit = listarView.getTxtBuscar().getText().toLowerCase();
        DefaultTableModel m = (DefaultTableModel) listarView.getTableUsuarios().getModel();
        m.setRowCount(0);
        usuarioDAO.listarTodos().stream()
                .filter(u -> u.getUsername().toLowerCase().contains(crit)
                        || u.getRol().toString().toLowerCase().contains(crit))
                .forEach(u -> m.addRow(new Object[]{u.getUsername(), u.getRol()}));
    }

    private void buscarUsuarioParaEliminar() {
        String crit = eliminarView.getTxtBuscar().getText().trim();
        String filt = eliminarView.getCbxFiltro().getSelectedItem().toString();
        DefaultTableModel m = (DefaultTableModel) eliminarView.getTableUsuarios().getModel();
        m.setRowCount(0);
        List<Usuario> found = new ArrayList<>();
        if ("Username".equals(filt)) {
            Usuario u = usuarioDAO.buscarPorUsername(crit);
            if (u != null) found.add(u);
        } else {
            try {
                Rol r = Rol.valueOf(crit.toUpperCase());
                found = usuarioDAO.listarPorRol(r);
            } catch (Exception ignored) { }
        }
        found.forEach(u -> m.addRow(new Object[]{u.getUsername(), u.getRol()}));
        if (found.isEmpty())
            eliminarView.mostrarMensaje(mensajeHandler.get("usuario.eliminar.no_encontrado"));
    }

    private void eliminarUsuarioSeleccionado() {
        int row = eliminarView.getTableUsuarios().getSelectedRow();
        if (row < 0) {
            eliminarView.mostrarMensaje(mensajeHandler.get("usuario.eliminar.no_seleccion"));
            return;
        }
        String usr = eliminarView.getTableUsuarios().getValueAt(row, 0).toString();
        usuarioDAO.eliminar(usr);
        eliminarView.mostrarMensaje(mensajeHandler.get("usuario.eliminar.exito"));
        buscarUsuarioParaEliminar();
    }

    private void cargarUsuariosParaActualizar() {
        int row = actualizarView.getTblUsuarios().getSelectedRow();
        if (row < 0) return;
        String usr = actualizarView.getTblUsuarios().getValueAt(row, 0).toString();
        Usuario u = usuarioDAO.buscarPorUsername(usr);
        if (u != null) {
            actualizarView.getTxtUsername().setText(u.getUsername());
            actualizarView.getTxtPassword().setText(u.getContrasenia());
            actualizarView.getCbxRol().setSelectedItem(u.getRol().toString());
        }
    }

    private void actualizarUsuario() {
        String usr = actualizarView.getTxtUsername().getText().trim();
        String pwd = new String(actualizarView.getTxtPassword().getPassword());
        if (usr.isEmpty() || pwd.isEmpty()) {
            actualizarView.mostrarMensaje(mensajeHandler.get("usuario.actualizar.campos_obligatorios"));
            return;
        }
        Usuario u = usuarioDAO.buscarPorUsername(usr);
        if (u == null) {
            actualizarView.mostrarMensaje(mensajeHandler.get("usuario.actualizar.no_encontrado"));
            return;
        }
        u.setContrasenia(pwd);
        u.setRol(Rol.valueOf(actualizarView.getCbxRol().getSelectedItem().toString()));
        usuarioDAO.actualizar(u);
        actualizarView.mostrarMensaje(mensajeHandler.get("usuario.actualizar.exito"));
        cargarUsuariosParaActualizar();
    }

    private void recuperarContraseña() {
        String usr = recuperarView.getUsername();
        Usuario u = usuarioDAO.buscarPorUsername(usr);
        if (u == null) {
            recuperarView.mostrarMensaje(mensajeHandler.get("usuario.error.no_encontrado"));
            return;
        }
        List<Integer> preguntasIds = u.getPreguntasIds();
        List<String> respuestas     = u.getRespuestasSeguridad();
        if (preguntasIds.isEmpty() || respuestas.isEmpty()) {
            recuperarView.mostrarMensaje(mensajeHandler.get("usuario.recuperacion.no_preguntas"));
            return;
        }
        indicePreguntaRecuperacion = new Random().nextInt(preguntasIds.size());
        int preguntaId = preguntasIds.get(indicePreguntaRecuperacion);

        String pregunta = mensajeHandler.get("preguntas.seguridad." + preguntaId);
        recuperarView.setPregunta(pregunta);

        String respUsuario = recuperarView.getRespuesta1().trim();
        if (respUsuario.isEmpty()) {
            recuperarView.mostrarMensaje(mensajeHandler.get("usuario.recuperacion.respuesta_vacia"));
            return;
        }
        if (!respUsuario.equalsIgnoreCase(respuestas.get(indicePreguntaRecuperacion))) {
            recuperarView.mostrarMensaje(mensajeHandler.get("usuario.recuperacion.respuesta_incorrecta"));
            return;
        }

        CambiarContraseñaView cambiarView = new CambiarContraseñaView();
        cambiarView.actualizarIdioma(mensajeHandler);
        cambiarView.setUsuario(usr);

        cambiarView.getGuardarButton().addActionListener(ev -> {
            String nueva = cambiarView.getNuevaContraseña().trim();
            String conf  = cambiarView.getConfirmarContraseña().trim();
            if (nueva.isEmpty() || conf.isEmpty()) {
                cambiarView.mostrarMensaje(mensajeHandler.get("usuario.recuperacion.nueva_vacia"));
                return;
            }
            if (!nueva.equals(conf)) {
                cambiarView.mostrarMensaje(mensajeHandler.get("usuario.recuperacion.pass_distintas"));
                return;
            }
            u.setContrasenia(nueva);
            usuarioDAO.actualizar(u);
            cambiarView.mostrarMensaje(mensajeHandler.get("usuario.recuperacion.exito"));
            cambiarView.dispose();
        });
        cambiarView.getCancelarButton().addActionListener(ev -> cambiarView.dispose());

        recuperarView.dispose();
        cambiarView.setVisible(true);
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}