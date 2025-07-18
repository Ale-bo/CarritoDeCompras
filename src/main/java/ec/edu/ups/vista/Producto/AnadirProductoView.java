package ec.edu.ups.vista.Producto;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnadirProductoView extends JInternalFrame {

    private final MensajeInternacionalizacionHandler mensajeHandler;
    private ProductoController productoController;

    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;

    public AnadirProductoView(MensajeInternacionalizacionHandler mensajeHandler) {
        super("", true, true, true, true);
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        actualizarIdioma();
    }

    // --- MÉTODO AÑADIDO PARA ENLAZAR EL CONTROLADOR ---
    public void setProductoController(ProductoController productoController) {
        this.productoController = productoController;
        // Se podrían añadir listeners aquí si fuera necesario, pero el controlador ya los tiene
    }

    public void actualizarIdioma() {
        setTitle(mensajeHandler.get("producto.view.anadir.titulo"));
        lblCodigo.setText(mensajeHandler.get("producto.view.anadir.codigo") + ":");
        lblNombre.setText(mensajeHandler.get("producto.view.anadir.nombre") + ":");
        lblPrecio.setText(mensajeHandler.get("producto.view.anadir.precio") + ":");
        btnAceptar.setText(mensajeHandler.get("producto.view.anadir.aceptar"));
        btnLimpiar.setText(mensajeHandler.get("producto.view.anadir.limpiar"));
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void mostrarMensaje(String key) {
        // Se ajusta para recibir el mensaje directamente, no la clave
        JOptionPane.showMessageDialog(this, key);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }
}