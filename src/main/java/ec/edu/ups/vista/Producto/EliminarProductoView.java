package ec.edu.ups.vista.Producto;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminarProductoView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JComboBox<String> comboFiltro;
    private JTextField txtBusqueda;
    private JButton btnBuscar;
    private JTable tablaResultado;
    private JButton btnEliminar;

    private DefaultTableModel modelo;
    // Se elimina 'final' para poder asignarlo más tarde
    private ProductoController productoController;
    private final MensajeInternacionalizacionHandler mensajes;

    // CORRECCIÓN: El constructor ya no recibe el controlador.
    public EliminarProductoView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, true, true);
        this.mensajes = mensajes;

        setContentPane(panelPrincipal);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);

        // Asegurarse de que el modelo de la tabla esté inicializado
        if (tablaResultado.getModel() instanceof DefaultTableModel) {
            modelo = (DefaultTableModel) tablaResultado.getModel();
        } else {
            modelo = new DefaultTableModel();
            tablaResultado.setModel(modelo);
        }

        actualizarIdioma();
    }

    // ##### MÉTODO NUEVO Y CORREGIDO #####
    // Permite enlazar la vista con el controlador y configurar los eventos.
    public void setProductoController(ProductoController controller) {
        this.productoController = controller;

        // Se configuran los listeners usando clases anónimas.
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productoController.buscarProductoParaEliminar();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productoController.eliminarProductoSeleccionado();
            }
        });
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
        return (String) comboFiltro.getSelectedItem();
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