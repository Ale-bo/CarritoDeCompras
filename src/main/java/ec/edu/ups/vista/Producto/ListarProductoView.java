package ec.edu.ups.vista.Producto;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListarProductoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnListar;
    private JTable tablaProductos;
    private JLabel lblNombre;

    private DefaultTableModel modelo;
    private final MensajeInternacionalizacionHandler mensajes;
    private ProductoController productoController;

    public ListarProductoView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        modelo = (DefaultTableModel) tablaProductos.getModel();
        actualizarIdioma();
    }

    public void setProductoController(ProductoController productoController){
        this.productoController = productoController;
    }

    public void actualizarIdioma() {
        setTitle(mensajes.get("producto.listar.titulo"));
        lblNombre.setText(mensajes.get("producto.listar.tabla.column.nombre"));
        btnBuscar.setText(mensajes.get("producto.listar.btn.buscar"));
        btnListar.setText(mensajes.get("producto.listar.btn.listar"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajes.get("producto.listar.tabla.column.codigo"),
                mensajes.get("producto.listar.tabla.column.nombre"),
                mensajes.get("producto.listar.tabla.column.precio")
        });
    }

    public String getTxtBuscar() {
        return txtBuscar.getText();
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }
}

