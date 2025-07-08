package ec.edu.ups.vista;

import javax.swing.SwingUtilities;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.controlador.UsuarioController;

import javax.swing.*;

public class LoginView extends JFrame {

    private final MensajeInternacionalizacionHandler mensajeHandler;
    private JLabel lblUsuario, lblPassword;
    private JTextField txtUsuario;
    private JPasswordField txtContrasenia;
    private JButton btnIniciarSesion, btnRegistrarse, btnOlvidarContrasenia;
    private JComboBox<String> comboIdiomas;
    private JPanel panelPrincipal;
    private JPanel panel;
    private static final String[] IDIOMAS = {"Español","English","Français"};

    private UsuarioController usuarioController;

    public LoginView(MensajeInternacionalizacionHandler mh) {
        this.mensajeHandler = mh;
        initComponents();
        setContentPane(panelPrincipal);
        setTitle(mensajeHandler.get("login.titulo"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        actualizarIdioma();
    }

    private void initComponents() {
        panelPrincipal = new JPanel(null);

        lblUsuario = new JLabel();
        lblUsuario.setBounds(180, 50, 100, 30);
        panelPrincipal.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(300, 50, 150, 30);
        panelPrincipal.add(txtUsuario);

        lblPassword = new JLabel();
        lblPassword.setBounds(180, 100, 100, 30);
        panelPrincipal.add(lblPassword);

        txtContrasenia = new JPasswordField();
        txtContrasenia.setBounds(300, 100, 150, 30);
        panelPrincipal.add(txtContrasenia);

        btnIniciarSesion = new JButton();
        btnIniciarSesion.setBounds(180, 160, 120, 35);
        panelPrincipal.add(btnIniciarSesion);

        btnRegistrarse = new JButton();
        btnRegistrarse.setBounds(330, 160, 120, 35);
        panelPrincipal.add(btnRegistrarse);

        btnOlvidarContrasenia = new JButton();
        btnOlvidarContrasenia.setBounds(180, 210, 270, 30);
        panelPrincipal.add(btnOlvidarContrasenia);

        comboIdiomas = new JComboBox<>(IDIOMAS);
        comboIdiomas.setBounds(440, 10, 120, 28);
        comboIdiomas.addActionListener(e -> {
            String sel = (String) comboIdiomas.getSelectedItem();
            System.out.println("Idioma seleccionado: " + sel);
            switch (sel) {
                case "English"  -> mensajeHandler.setLenguaje("en","US");
                case "Français" -> mensajeHandler.setLenguaje("fr","FR");
                default         -> mensajeHandler.setLenguaje("es","EC");
            }
            // 1) Actualizamos esta ventana
            actualizarIdioma();
            SwingUtilities.updateComponentTreeUI(this);

            // 2) Refrescamos todas las demás vistas de login/registro/menú
            if (usuarioController != null) {
                usuarioController.actualizarIdiomaEnVistasLogin();
            }
        });
        comboIdiomas.setSelectedIndex(0);
        panelPrincipal.add(comboIdiomas);
    }

    public void setUsuarioController(UsuarioController ctrl) {
        this.usuarioController = ctrl;
        btnIniciarSesion.addActionListener(e -> usuarioController.autenticarUsuario());
        btnRegistrarse.addActionListener(e -> usuarioController.abrirRegistro());
        btnOlvidarContrasenia.addActionListener(e -> usuarioController.abrirRecuperacion());
    }

    public void actualizarIdioma() {
        setTitle(mensajeHandler.get("login.titulo"));
        lblUsuario.setText(mensajeHandler.get("login.label.usuario"));
        lblPassword.setText(mensajeHandler.get("login.label.contrasenia"));
        btnIniciarSesion.setText(mensajeHandler.get("login.boton.iniciar"));
        btnRegistrarse.setText(mensajeHandler.get("login.boton.registrarse"));
        btnOlvidarContrasenia.setText(mensajeHandler.get("login.olvidarContrasenia"));
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    // Getters existentes
    public JButton getBtnIniciarSesion()     { return btnIniciarSesion; }
    public JButton getBtnRegistrarse()       { return btnRegistrarse; }
    public JButton getBtnOlvidar()           { return btnOlvidarContrasenia; }
    public JTextField getTxtUsername()       { return txtUsuario; }
    public JPasswordField getTxtContrasenia(){ return txtContrasenia; }
    public void mostrarMensaje(String msg)   { JOptionPane.showMessageDialog(this, msg); }
}
