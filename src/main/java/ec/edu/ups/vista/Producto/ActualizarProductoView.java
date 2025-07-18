package ec.edu.ups.vista.Producto;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActualizarProductoView extends JInternalFrame {

    private ProductoController productoController;
    private final MensajeInternacionalizacionHandler mensajeHandler;

    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnBuscar;
    private JButton btnActualizar;
    private JButton btnCancelar;
    private JTable tblProductos;
    private DefaultTableModel modelo;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;

    public ActualizarProductoView(MensajeInternacionalizacionHandler mensajeHandler) {
        super("", true, true, true, true);
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        modelo = (DefaultTableModel) tblProductos.getModel();
        actualizarIdioma();
    }

    public void setProductoController(ProductoController controller) {
        this.productoController = controller;
    }

    public void actualizarIdioma() {
        setTitle(mensajeHandler.get("producto.view.modificar.titulo"));
        lblCodigo.setText(mensajeHandler.get("producto.view.modificar.codigo") + ":");
        lblNombre.setText(mensajeHandler.get("producto.view.modificar.nombre") + ":");
        lblPrecio.setText(mensajeHandler.get("producto.view.modificar.precio") + ":");
        btnBuscar.setText(mensajeHandler.get("producto.view.modificar.buscar"));
        btnActualizar.setText(mensajeHandler.get("producto.view.modificar.actualizar"));
        btnCancelar.setText(mensajeHandler.get("producto.view.modificar.cancelar"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajeHandler.get("producto.view.modificar.codigo"),
                mensajeHandler.get("producto.view.modificar.nombre"),
                mensajeHandler.get("producto.view.modificar.precio")
        });
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JTable getTableProductos() {
        return tblProductos;
    }

    public DefaultTableModel getTableModel() {
        return modelo;
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

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtCodigo.setEnabled(true);
        txtNombre.setText("");
        txtPrecio.setText("");
        tblProductos.clearSelection();
        ((DefaultTableModel)tblProductos.getModel()).setRowCount(0);
    }

    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
