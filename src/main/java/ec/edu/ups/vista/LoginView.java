package ec.edu.ups.vista;

import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File; // Necesario para JFileChooser

public class LoginView extends JFrame {

    private final MensajeInternacionalizacionHandler mensajeHandler;
    private JLabel lblUsuario, lblPassword;
    private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JButton btnIniciarSesion, btnRegistrarse, btnOlvidarContrasenia;
    private JComboBox<String> comboIdiomas;
    private JPanel panelPrincipal;
    private JPanel panel;
    private JLabel lblTipoAlmacenamiento;
    private JComboBox<String> comboTipoAlmacenamiento;
    private JLabel lblRutaAlmacenamiento;
    private JTextField txtRutaAlmacenamiento;
    private JButton btnSeleccionarRuta;


    private static final String[] IDIOMAS = {"Español", "English", "Français"};
    private UsuarioController usuarioController;

    public LoginView(MensajeInternacionalizacionHandler mh) {
        this.mensajeHandler = mh;
        setContentPane(panelPrincipal);
        initComponents();
        setTitle(mensajeHandler.get("login.titulo"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 300); // Ajusta el tamaño de la ventana si es necesario
        setLocationRelativeTo(null);
        actualizarIdioma();
    }

    private void initComponents() {
        // --- Configuración existente de idioma ---
        DefaultComboBoxModel<String> modelIdiomas = new DefaultComboBoxModel<>(IDIOMAS);
        comboIdiomas.setModel(modelIdiomas);

        comboIdiomas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sel = (String) comboIdiomas.getSelectedItem();
                if (sel == null) return;
                switch (sel) {
                    case "English":
                        mensajeHandler.setLenguaje("en", "US");
                        break;
                    case "Français":
                        mensajeHandler.setLenguaje("fr", "FR");
                        break;
                    default: // "Español"
                        mensajeHandler.setLenguaje("es", "EC");
                        break;
                }
                actualizarIdioma();
            }
        });

        // --- Configuración NUEVA para el ComboBox de Tipo de Almacenamiento ---
        DefaultComboBoxModel<String> modelAlmacenamiento = new DefaultComboBoxModel<>();
        modelAlmacenamiento.addElement(mensajeHandler.get("login.almacenamiento.memoria"));
        modelAlmacenamiento.addElement(mensajeHandler.get("login.almacenamiento.texto"));
        modelAlmacenamiento.addElement(mensajeHandler.get("login.almacenamiento.binario"));
        comboTipoAlmacenamiento.setModel(modelAlmacenamiento);

        // Ocultar campos de ruta inicialmente
        lblRutaAlmacenamiento.setVisible(false);
        txtRutaAlmacenamiento.setVisible(false);
        btnSeleccionarRuta.setVisible(false);
        txtRutaAlmacenamiento.setEditable(false); // La ruta se selecciona, no se escribe manualmente

        // Listener para el ComboBox de tipo de almacenamiento
        comboTipoAlmacenamiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) comboTipoAlmacenamiento.getSelectedItem();
                boolean esArchivo = seleccion != null &&
                        (seleccion.equals(mensajeHandler.get("login.almacenamiento.texto")) ||
                                seleccion.equals(mensajeHandler.get("login.almacenamiento.binario")));

                lblRutaAlmacenamiento.setVisible(esArchivo);
                txtRutaAlmacenamiento.setVisible(esArchivo);
                btnSeleccionarRuta.setVisible(esArchivo);

                if (!esArchivo) {
                    txtRutaAlmacenamiento.setText(""); // Limpiar campo si no es tipo archivo
                }
            }
        });

        // Listener para el botón "Seleccionar Ruta"
        btnSeleccionarRuta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Solo directorios
                int opcion = fileChooser.showOpenDialog(LoginView.this); // 'LoginView.this' para el padre

                if (opcion == JFileChooser.APPROVE_OPTION) {
                    File directorioSeleccionado = fileChooser.getSelectedFile();
                    txtRutaAlmacenamiento.setText(directorioSeleccionado.getAbsolutePath());
                }
            }
        });
    }

    public void setUsuarioController(UsuarioController ctrl) {
        this.usuarioController = ctrl;
    }

    public void actualizarIdioma() {
        setTitle(mensajeHandler.get("login.titulo"));
        lblUsuario.setText(mensajeHandler.get("login.label.usuario"));
        lblPassword.setText(mensajeHandler.get("login.label.contrasenia"));
        btnIniciarSesion.setText(mensajeHandler.get("login.boton.iniciar"));
        btnRegistrarse.setText(mensajeHandler.get("login.boton.registrarse"));
        btnOlvidarContrasenia.setText(mensajeHandler.get("login.olvidarContrasenia"));

        // Actualizar textos de los nuevos componentes
        lblTipoAlmacenamiento.setText(mensajeHandler.get("login.label.almacenamiento"));
        lblRutaAlmacenamiento.setText(mensajeHandler.get("login.label.ruta"));
        btnSeleccionarRuta.setText(mensajeHandler.get("login.boton.seleccionarRuta"));

        // Actualizar ítems del JComboBox de Almacenamiento
        // Se hace en initComponents y con el listener de idioma en Main
        // Pero para asegurar que si se cambia el idioma, los ítems se actualicen:
        DefaultComboBoxModel<String> modelAlmacenamiento = (DefaultComboBoxModel<String>) comboTipoAlmacenamiento.getModel();
        String seleccionActual = (String) comboTipoAlmacenamiento.getSelectedItem(); // Guardar la selección actual

        modelAlmacenamiento.removeAllElements();
        modelAlmacenamiento.addElement(mensajeHandler.get("login.almacenamiento.memoria"));
        modelAlmacenamiento.addElement(mensajeHandler.get("login.almacenamiento.texto"));
        modelAlmacenamiento.addElement(mensajeHandler.get("login.almacenamiento.binario"));

        // Intentar restaurar la selección previa, si no existe, seleccionar el primero
        if (seleccionActual != null && modelAlmacenamiento.getIndexOf(seleccionActual) != -1) {
            comboTipoAlmacenamiento.setSelectedItem(seleccionActual);
        } else {
            comboTipoAlmacenamiento.setSelectedIndex(0);
        }

        // Activar el listener del combo para actualizar la visibilidad de la ruta si es necesario
        // Esto es importante para que los campos de ruta se oculten/muestren correctamente
        // al cambiar el idioma y recargar los ítems del combo.
        // Simulamos un evento de acción para que el listener se dispare.
        if (comboTipoAlmacenamiento.getActionListeners().length > 0) {
            comboTipoAlmacenamiento.getActionListeners()[0].actionPerformed(
                    new ActionEvent(comboTipoAlmacenamiento, ActionEvent.ACTION_PERFORMED, null)
            );
        }


        if (usuarioController != null) {
            usuarioController.actualizarIdiomaEnVistasLogin();
        }
        SwingUtilities.updateComponentTreeUI(this);
    }

    public JComboBox<String> getComboTipoAlmacenamiento() {
        return comboTipoAlmacenamiento;
    }

    public JTextField getTxtRutaAlmacenamiento() {
        return txtRutaAlmacenamiento;
    }

    public JButton getBtnIniciarSesion() { return btnIniciarSesion; }
    public JButton getBtnRegistrarse() { return btnRegistrarse; }
    public JButton getBtnOlvidar() { return btnOlvidarContrasenia; }
    public JTextField getTxtUsername() { return txtUsuario; }
    public JPasswordField getTxtContrasenia() { return txtContrasenia; }

    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}