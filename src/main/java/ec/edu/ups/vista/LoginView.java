package ec.edu.ups.vista;

import javax.swing.*;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import java.awt.*;

public class LoginView extends JFrame {
    private MensajeInternacionalizacionHandler mensajeHandler;
    private JPanel panelPrincipal;

    private JTextField txtUsername;
    private JPasswordField txtContrasenia;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JLabel Usuario;
    private JLabel Contrasenia;
    private JButton btnolvideMiContraseña;
    private JComboBox<String> comboIdiomas;
    private static final String[] IDIOMAS = {"Español", "English", "Français"};

    public LoginView() {

        this.mensajeHandler = new MensajeInternacionalizacionHandler("es", "ES");
        // Configurar el título y la interfaz
        setContentPane(panelPrincipal);
        setTitle(mensajeHandler.get("login.titulo"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Configurar textos en los botones y campos según el idioma
        btnIniciarSesion.setText(mensajeHandler.get("login.boton.iniciar"));
        btnRegistrarse.setText(mensajeHandler.get("login.boton.registrarse"));


        // Crear barra de menú
        JMenuBar menuBar = new JMenuBar();

        // Crear el combo box de idiomas
        comboIdiomas = new JComboBox<>(IDIOMAS);
        comboIdiomas.setMaximumSize(new Dimension(150, 30));

        // Agregar el combo box al menú
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(new JLabel("Idioma: "));
        menuBar.add(comboIdiomas);

        // Establecer la barra de menú
        setJMenuBar(menuBar);

        // Agregar el listener para cambios de idioma
        comboIdiomas.addActionListener(e -> cambiarIdioma());

        // Actualizar la interfaz con el idioma inicial
        actualizarIdioma(mensajeHandler);


    }

    private void cambiarIdioma() {
        if (mensajeHandler == null) {
            mensajeHandler = new MensajeInternacionalizacionHandler("es", "ES");
        }

        String idiomaSeleccionado = (String) comboIdiomas.getSelectedItem();
        switch (idiomaSeleccionado) {
            case "Español":
                mensajeHandler.setLenguaje("es", "ES");
                break;
            case "English":
                mensajeHandler.setLenguaje("en", "US");
                break;
            case "Français":
                mensajeHandler.setLenguaje("fr", "FR");
                break;
        }
        actualizarIdioma(mensajeHandler);
    }

    public void actualizarIdioma(MensajeInternacionalizacionHandler mensajeHandler) {
        this.mensajeHandler = mensajeHandler;

        // Actualizar título de la ventana
        setTitle(mensajeHandler.get("login.titulo"));

        // Actualizar etiquetas
        Usuario.setText(mensajeHandler.get("login.label.usuario"));
        Contrasenia.setText(mensajeHandler.get("login.label.contrasenia"));

        // Actualizar botones
        btnIniciarSesion.setText(mensajeHandler.get("login.boton.iniciar"));
        btnRegistrarse.setText(mensajeHandler.get("login.boton.registrarse"));
    }

    public void configurarBotonOlvideContraseña() {
        loginView.addOlvideContraseñaListener(e -> {
            iniciarRecuperacionContraseña();
        });
    }


    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getTxtContrasenia() {
        return txtContrasenia;
    }


    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JButton getBtnolvideMiContraseña() {
        return btnolvideMiContraseña;
    }

    public void limpiarCampos() {
        txtUsername.setText("");
        txtContrasenia.setText("");
    }

}

