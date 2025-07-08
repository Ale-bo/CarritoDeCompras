package ec.edu.ups.vista.Producto;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ActualizarProductoView extends JInternalFrame {

    private final ProductoController productoController;
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

    public ActualizarProductoView(ProductoController controller,
                                  MensajeInternacionalizacionHandler mensajeHandler) {
        super("", true, true, true, true);
        this.productoController = controller;
        this.mensajeHandler = mensajeHandler;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);

        modelo = (DefaultTableModel) tblProductos.getModel();
        actualizarIdioma();

        btnBuscar.addActionListener(e -> productoController.cargarTablaMod());
        btnActualizar.addActionListener(e -> productoController.actualizarProducto());
        btnCancelar.addActionListener(e -> limpiarCampos());
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

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
