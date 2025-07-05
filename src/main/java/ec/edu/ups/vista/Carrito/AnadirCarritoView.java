package ec.edu.ups.vista.Carrito;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class AnadirCarritoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtCantidad;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JLabel lblCodigo;
    private JLabel lblCantidad;

    private final CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajes;

    public AnadirCarritoView(CarritoController carritoController, MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.carritoController = carritoController;
        this.mensajes = mensajes;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        actualizarIdioma();
        btnAceptar.addActionListener(e -> carritoController.crearCarrito());
        btnLimpiar.addActionListener(e -> limpiarCampos());

        pack();
    }

    public void actualizarIdioma() {
        setTitle(mensajes.get("carrito.anadir.titulo"));
        lblCodigo.setText(mensajes.get("carrito.anadir.lbl.codigo"));
        lblCantidad.setText(mensajes.get("carrito.anadir.lbl.cantidad"));
        btnAceptar.setText(mensajes.get("carrito.anadir.btn.aceptar"));
        btnLimpiar.setText(mensajes.get("carrito.anadir.btn.limpiar"));
    }

    public String getCodigo() {
        return txtCodigo.getText();
    }

    public String getCantidad() {
        return txtCantidad.getText();
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public void mostrarMensaje(String clave) {
        JOptionPane.showMessageDialog(this, mensajes.get(clave));
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtCantidad.setText("");
    }
}


