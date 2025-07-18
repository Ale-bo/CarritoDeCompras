package ec.edu.ups.vista;

import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

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
        setContentPane(panelPrincipal); // Es importante que esto esté antes de manipular componentes
        initComponents();
        setTitle(mensajeHandler.get("login.titulo"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);
        actualizarIdioma();
    }

    private void initComponents() {
        // 1. Poblar el JComboBox de idiomas para que no esté vacío
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(IDIOMAS);
        comboIdiomas.setModel(model);

        // 2. Añadir el ActionListener que reacciona al cambio
        comboIdiomas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sel = (String) comboIdiomas.getSelectedItem();
                if (sel == null) return;

                // Cambia el idioma en el manejador
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

                // Llama al método que actualiza todos los textos en la ventana
                actualizarIdioma();
            }
        });
    }



    public void setUsuarioController(UsuarioController ctrl) {
        this.usuarioController = ctrl;
    }

    // Este método se encarga de actualizar todos los textos de la UI
    public void actualizarIdioma() {
        setTitle(mensajeHandler.get("login.titulo"));
        lblUsuario.setText(mensajeHandler.get("login.label.usuario"));
        lblPassword.setText(mensajeHandler.get("login.label.contrasenia"));
        btnIniciarSesion.setText(mensajeHandler.get("login.boton.iniciar"));
        btnRegistrarse.setText(mensajeHandler.get("login.boton.registrarse"));
        btnOlvidarContrasenia.setText(mensajeHandler.get("login.olvidarContrasenia"));

        // Notifica al controlador para que actualice otras vistas si es necesario
        if (usuarioController != null) {
            usuarioController.actualizarIdiomaEnVistasLogin();
        }

        // Forza la actualización visual de toda la ventana
        SwingUtilities.updateComponentTreeUI(this);
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