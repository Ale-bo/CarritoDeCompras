package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.InicioDeSesion.*;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.MenuPrincipalView;
import ec.edu.ups.vista.Usuario.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public UsuarioController(UsuarioDAO uDAO, LoginView lV, RegistrarUsuarioView rV, ListarUsuarioView liV, EliminarUsuarioView dV, ActualizarUsuarioView upV, RecuperarContraseñaView recV, MenuPrincipalView pV, MensajeInternacionalizacionHandler msg) {
        this.usuarioDAO = uDAO;
        this.loginView = lV;
        this.registrarView = rV;
        this.listarView = liV;
        this.eliminarView = dV;
        this.actualizarView = upV;
        this.recuperarView = recV;
        this.principal = pV;
        this.mensajeHandler = msg;
        configurarEventos();
    }

    private void configurarEventos() {
        loginView.getBtnIniciarSesion().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { autenticarUsuario(); }
        });
        loginView.getBtnRegistrarse().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { abrirRegistro(); }
        });
        loginView.getBtnOlvidar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { abrirRecuperacion(); }
        });
        registrarView.getRegistrarButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { registrarUsuario(); }
        });
        registrarView.getCancelarButton().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { cancelarRegistro(); }
        });
        listarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { buscarUsuarios(); }
        });
        listarView.getBtnRefrescar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { cargarUsuarios(); }
        });
        eliminarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { buscarUsuarioParaEliminar(); }
        });
        eliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { eliminarUsuarioSeleccionado(); }
        });
        actualizarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { cargarUsuariosParaActualizar(); }
        });
        actualizarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { actualizarUsuario(); }
        });
        actualizarView.getTblUsuarios().getSelectionModel().addListSelectionListener(this::seleccionarUsuarioParaActualizar);
        recuperarView.addRecuperarListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { recuperarContraseña(); }
        });
        recuperarView.addCancelarListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { recuperarView.dispose(); }
        });
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

    public void abrirRegistro() {
        registrarView.actualizarIdioma(mensajeHandler);
        registrarView.setVisible(true);
    }

    public void abrirRecuperacion() {
        String usr = JOptionPane.showInputDialog(loginView, mensajeHandler.get("login.label.usuario"));
        if (usr == null || usr.trim().isEmpty()) return;
        Usuario u = usuarioDAO.buscarPorUsername(usr.trim());
        if (u != null && u.getPreguntasDeSeguridad() != null && !u.getPreguntasDeSeguridad().isEmpty()) {
            Random rand = new Random();
            PreguntaSeguridad p = u.getPreguntasDeSeguridad().get(rand.nextInt(u.getPreguntasDeSeguridad().size()));
            recuperarView.setUsername(u.getUsername());
            recuperarView.setCorreo(u.getCorreo());
            recuperarView.setPregunta(mensajeHandler.get("preguntas.seguridad." + p.getPreguntaId()), p.getPreguntaId());
            principal.getDesktopPane().add(recuperarView);
            recuperarView.setVisible(true);
        } else {
            loginView.mostrarMensaje(mensajeHandler.get("usuario.error.no_encontrado"));
        }
    }

    public void actualizarIdiomaEnVistasLogin() {
        // Llama al método para actualizar textos en la vista de registro
        if (registrarView != null) {
            registrarView.actualizarIdioma(mensajeHandler);
            SwingUtilities.updateComponentTreeUI(registrarView);
        }

        // Llama al método para actualizar textos en la vista de recuperación
        if (recuperarView != null) {
            recuperarView.actualizarIdioma(mensajeHandler);
            SwingUtilities.updateComponentTreeUI(recuperarView);
        }
    }

    private void registrarUsuario() {
        String pwd1 = new String(registrarView.getPasswordcontrasena().getPassword());
        String pwd2 = new String(registrarView.getPasswordconfcontrasena().getPassword());
        if (!pwd1.equals(pwd2)) {
            registrarView.mostrarMensaje("Las contraseñas no coinciden.");
            return;
        }
        List<PreguntaSeguridad> preguntas = new ArrayList<>();
        preguntas.add(new PreguntaSeguridad(registrarView.getPreguntasIdsSeleccionadas().get(0), registrarView.getRespuesta1()));
        preguntas.add(new PreguntaSeguridad(registrarView.getPreguntasIdsSeleccionadas().get(1), registrarView.getRespuesta2()));
        preguntas.add(new PreguntaSeguridad(registrarView.getPreguntasIdsSeleccionadas().get(2), registrarView.getRespuesta3()));
        Usuario nuevo = new Usuario(registrarView.getTextususario().getText().trim(), pwd1, registrarView.getTextnombre().getText().trim(), registrarView.getTextcorreo().getText().trim(), registrarView.getTextcelular().getText().trim(), "fecha", preguntas);
        usuarioDAO.crear(nuevo);
        registrarView.mostrarMensaje("Usuario registrado con éxito.");
        cancelarRegistro();
    }

    private void cancelarRegistro() {
        registrarView.limpiarCampos();
        registrarView.setVisible(false);
    }

    private void cargarUsuarios() {
        DefaultTableModel model = listarView.getTableModel();
        model.setRowCount(0);
        usuarioDAO.listarTodos().forEach(u -> model.addRow(new Object[]{u.getUsername(), u.getRol().toString()}));
    }

    private void buscarUsuarios() {
        String criterio = listarView.getTxtBuscar().getText().trim().toLowerCase();
        DefaultTableModel model = listarView.getTableModel();
        model.setRowCount(0);
        usuarioDAO.listarTodos().stream()
                .filter(u -> u.getUsername().toLowerCase().contains(criterio) || u.getRol().toString().toLowerCase().contains(criterio))
                .forEach(u -> model.addRow(new Object[]{u.getUsername(), u.getRol().toString()}));
    }

    private void buscarUsuarioParaEliminar() {
        // 1. Obtener los datos de la vista
        String criterio = eliminarView.getTxtBuscar().getText().trim().toLowerCase();
        String filtroSeleccionado = (String) eliminarView.getCbxFiltro().getSelectedItem();
        DefaultTableModel model = eliminarView.getTableModel();

        // 2. Limpiar la tabla antes de mostrar los nuevos resultados
        model.setRowCount(0);

        // 3. Obtener la lista completa de usuarios del DAO
        List<Usuario> todosLosUsuarios = usuarioDAO.listarTodos();

        // 4. Filtrar la lista según el criterio y el filtro seleccionados
        List<Usuario> usuariosEncontrados = new ArrayList<>();

        // Comprueba si se ha seleccionado un filtro
        if (filtroSeleccionado != null) {
            for (Usuario usuario : todosLosUsuarios) {
                // Si el filtro es por "Usuario" (Username)
                if (filtroSeleccionado.equals(mensajeHandler.get("usuario.view.eliminar.filtro.username"))) {
                    if (usuario.getUsername().toLowerCase().contains(criterio)) {
                        usuariosEncontrados.add(usuario);
                    }
                }
                // Si el filtro es por "Rol"
                else if (filtroSeleccionado.equals(mensajeHandler.get("usuario.view.eliminar.filtro.rol"))) {
                    if (usuario.getRol().toString().toLowerCase().contains(criterio)) {
                        usuariosEncontrados.add(usuario);
                    }
                }
            }
        }

        // 5. Poblar la tabla con los resultados encontrados
        if (usuariosEncontrados.isEmpty()) {
            eliminarView.mostrarMensaje("No se encontraron usuarios con ese criterio.");
        } else {
            for (Usuario u : usuariosEncontrados) {
                model.addRow(new Object[]{u.getUsername(), u.getRol().toString()});
            }
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
        } else {
            eliminarView.mostrarMensaje("Seleccione un usuario.");
        }
    }

    private void cargarUsuariosParaActualizar() {
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
                    actualizarView.getTxtPassword().setText(u.getContrasenia());
                    actualizarView.getPasswordconfcontrasenia().setText(u.getContrasenia());
                }
            }
        }
    }

    private void actualizarUsuario() {
        String username = actualizarView.getTxtUsername().getText();
        String pass1 = new String(actualizarView.getTxtPassword().getPassword());
        String pass2 = new String(actualizarView.getPasswordconfcontrasenia().getPassword());
        if (!pass1.equals(pass2)) {
            actualizarView.mostrarMensaje("Las contraseñas no coinciden.");
            return;
        }
        Usuario u = usuarioDAO.buscarPorUsername(username);
        if (u != null) {
            u.setContrasenia(pass1);
            usuarioDAO.actualizar(u);
            actualizarView.mostrarMensaje("Usuario actualizado.");
            actualizarView.limpiarCampos();
            actualizarView.getTxtUsername().setEnabled(true);
            cargarUsuariosParaActualizar();
        }
    }

    private void recuperarContraseña() {
        Usuario u = usuarioDAO.buscarPorUsername(recuperarView.getUsername());
        if (u == null) return;
        boolean ok = u.getPreguntasDeSeguridad().stream()
                .anyMatch(p -> p.getPreguntaId() == recuperarView.getPreguntaIdActual() && p.getRespuesta().equalsIgnoreCase(recuperarView.getRespuesta1()));

        if (ok) {
            String nPwd = JOptionPane.showInputDialog(recuperarView, "Ingrese la nueva contraseña:");
            if (nPwd != null && !nPwd.trim().isEmpty()) {
                u.setContrasenia(nPwd);
                usuarioDAO.actualizar(u);
                recuperarView.mostrarMensaje("Contraseña actualizada.");
                recuperarView.dispose();
            }
        } else {
            recuperarView.mostrarMensaje("Respuesta incorrecta.");
        }
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}