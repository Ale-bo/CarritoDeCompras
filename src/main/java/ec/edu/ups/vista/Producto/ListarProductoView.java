package ec.edu.ups.vista.Producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListarProductoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JLabel lblBuscar;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnListar;
    private JTable tablaProductos;

    private DefaultTableModel modelo;
    private final MensajeInternacionalizacionHandler mensajes;

    public ListarProductoView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        modelo = (DefaultTableModel) tablaProductos.getModel();
        tablaProductos.setModel(modelo);

        actualizarIdioma();
        pack();
    }

    public void actualizarIdioma() {
        setTitle(mensajes.get("producto.listar.titulo"));
        lblBuscar.setText(mensajes.get("producto.listar.lbl.buscar"));
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

    public JTable getTablaProductos() {
        return tablaProductos;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }
}


