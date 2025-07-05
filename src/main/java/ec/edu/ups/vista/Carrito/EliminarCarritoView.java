package ec.edu.ups.vista.Carrito;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EliminarCarritoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JComboBox<String> comboFiltro;
    private JTextField txtBusqueda;
    private JButton btnBuscar;
    private JTable tablaResultado;
    private JButton btnEliminar;

    private DefaultTableModel modelo;
    private final CarritoController carritoController;
    private final MensajeInternacionalizacionHandler mensajes;

    public EliminarCarritoView(CarritoController carritoController, MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.carritoController = carritoController;
        this.mensajes = mensajes;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);

        comboFiltro.addItem(mensajes.get("carrito.eliminar.filtro.codigo"));
        comboFiltro.addItem(mensajes.get("carrito.eliminar.filtro.nombre"));

        modelo = (DefaultTableModel) tablaResultado.getModel();
        modelo.setColumnIdentifiers(new Object[]{
                mensajes.get("carrito.eliminar.tabla.column.codigo"),
                mensajes.get("carrito.eliminar.tabla.column.nombre"),
                mensajes.get("carrito.eliminar.tabla.column.precio")
        });

        tablaResultado.setModel(modelo);
        actualizarIdioma();

        btnBuscar.addActionListener(e -> carritoController.buscarCarritoParaEliminar());
        btnEliminar.addActionListener(e -> carritoController.eliminarCarritoSeleccionado());
    }

    public void actualizarIdioma() {
        setTitle(mensajes.get("carrito.eliminar.titulo"));
        btnBuscar.setText(mensajes.get("carrito.eliminar.btn.buscar"));
        btnEliminar.setText(mensajes.get("carrito.eliminar.btn.eliminar"));

        comboFiltro.removeAllItems();
        comboFiltro.addItem(mensajes.get("carrito.eliminar.filtro.codigo"));
        comboFiltro.addItem(mensajes.get("carrito.eliminar.filtro.nombre"));

        modelo.setColumnIdentifiers(new Object[]{
                mensajes.get("carrito.eliminar.tabla.column.codigo"),
                mensajes.get("carrito.eliminar.tabla.column.nombre"),
                mensajes.get("carrito.eliminar.tabla.column.precio")
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


