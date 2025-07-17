package ec.edu.ups.vista;

import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private final MensajeInternacionalizacionHandler mensajeHandler;
    private JLabel lblUsuario, lblPassword;
    private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JButton btnIniciarSesion, btnRegistrarse, btnOlvidarContrasenia;
    private JComboBox<String> comboIdiomas;
    private JPanel panelPrincipal;
    private JPanel panel;
    private static final String[] IDIOMAS = {"Español", "English", "Français"};
    private UsuarioController usuarioController;

    public LoginView(MensajeInternacionalizacionHandler mh) {
        this.mensajeHandler = mh;
        initComponents(); // Llama a la inicialización de componentes
        setContentPane(panelPrincipal);
        setTitle(mensajeHandler.get("login.titulo"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        actualizarIdioma();
    }

    private void initComponents() {
        // Se asume que el panel y los componentes se inicializan desde el archivo .form
        // Si no, aquí iría la creación manual de cada componente (new JLabel(), etc.)

        // --- Listener para el ComboBox de Idiomas (usando Clase Anónima) ---
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
                    default:
                        mensajeHandler.setLenguaje("es", "EC");
                        break;
                }

                // Actualiza los textos en esta ventana
                actualizarIdioma();
                SwingUtilities.updateComponentTreeUI(LoginView.this);

                // Notifica al controlador para que actualice otras ventanas si es necesario
                if (usuarioController != null) {
                    usuarioController.actualizarIdiomaEnVistasLogin();
                }
            }
        });
    }

    /**
     * Guarda una referencia al controlador. Los listeners de los botones
     * ya se configuran dentro del propio controlador.
     */
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
    }

    // Getters para que el controlador acceda a los componentes
    public JButton getBtnIniciarSesion() { return btnIniciarSesion; }
    public JButton getBtnRegistrarse() { return btnRegistrarse; }
    public JButton getBtnOlvidar() { return btnOlvidarContrasenia; }
    public JTextField getTxtUsername() { return txtUsuario; }
    public JPasswordField getTxtContrasenia() { return txtContrasenia; }

    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}