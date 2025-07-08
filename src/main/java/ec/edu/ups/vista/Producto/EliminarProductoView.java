package ec.edu.ups.vista.Producto;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EliminarProductoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JComboBox<String> comboFiltro;
    private JTextField txtBusqueda;
    private JButton btnBuscar;
    private JTable tablaResultado;
    private JButton btnEliminar;

    private DefaultTableModel modelo;
    private final ProductoController productoController;
    private final MensajeInternacionalizacionHandler mensajes;

    public EliminarProductoView(ProductoController controller, MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.productoController = controller;
        this.mensajes = mensajes;

        // panelPrincipal y componentes generados por IntelliJ
        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);

        // Inicializar filtro
        comboFiltro.addItem(mensajes.get("producto.eliminar.filtro.nombre"));
        comboFiltro.addItem(mensajes.get("producto.eliminar.filtro.codigo"));

        // Configurar modelo de la tabla
        modelo = (DefaultTableModel) tablaResultado.getModel();
        modelo.setColumnIdentifiers(new Object[]{
                mensajes.get("producto.eliminar.tabla.column.codigo"),
                mensajes.get("producto.eliminar.tabla.column.nombre"),
                mensajes.get("producto.eliminar.tabla.column.precio")
        });
        tablaResultado.setModel(modelo);

        actualizarIdioma();

        // Eventos delegados al controlador
        btnBuscar.addActionListener(e -> productoController.buscarProductoParaEliminar());
        btnEliminar.addActionListener(e -> productoController.eliminarProductoSeleccionado());
    }

    public void actualizarIdioma() {
        setTitle(mensajes.get("producto.eliminar.titulo"));
        btnBuscar.setText(mensajes.get("producto.eliminar.btn.buscar"));
        btnEliminar.setText(mensajes.get("producto.eliminar.btn.eliminar"));

        comboFiltro.removeAllItems();
        comboFiltro.addItem(mensajes.get("producto.eliminar.filtro.nombre"));
        comboFiltro.addItem(mensajes.get("producto.eliminar.filtro.codigo"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajes.get("producto.eliminar.tabla.column.codigo"),
                mensajes.get("producto.eliminar.tabla.column.nombre"),
                mensajes.get("producto.eliminar.tabla.column.precio")
        });
    }

    public String getFiltro() {
        return comboFiltro.getSelectedItem().toString();
    }

    public String getTxtBusqueda() {
        return txtBusqueda.getText();
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JTable getTablaResultado() {
        return tablaResultado;
    }

    public DefaultTableModel getModelResultado() {
        return modelo;
    }

    public void mostrarMensaje(String s) {
        JOptionPane.showMessageDialog(this, s);
    }
}
