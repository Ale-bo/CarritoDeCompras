package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.Producto.AnadirProductoView;
import ec.edu.ups.vista.Producto.EliminarProductoView;
import ec.edu.ups.vista.Producto.ListarProductoView;
import ec.edu.ups.vista.Producto.ActualizarProductoView;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.Collections;
import java.util.List;

public class ProductoController {

    private final ProductoDAO productoDAO;
    private final MensajeInternacionalizacionHandler mensajes;
    private final AnadirProductoView anadirView;
    private final ListarProductoView listarView;
    private final EliminarProductoView eliminarView;
    private final ActualizarProductoView actualizarView;

    public ProductoController(ProductoDAO productoDAO,
                              MensajeInternacionalizacionHandler mensajes,
                              AnadirProductoView anadirView,
                              ListarProductoView listarView,
                              EliminarProductoView eliminarView,
                              ActualizarProductoView actualizarView) {
        this.productoDAO    = productoDAO;
        this.mensajes       = mensajes;
        this.anadirView     = anadirView;
        this.listarView     = listarView;
        this.eliminarView   = eliminarView;
        this.actualizarView = actualizarView;

        // Añadir
        anadirView.getBtnAceptar().addActionListener(e -> crearProducto());
        anadirView.getBtnLimpiar().addActionListener(e -> anadirView.limpiarCampos());

        // Listar
        listarView.getBtnListar().addActionListener(e -> listarProductos());
        listarView.getBtnBuscar().addActionListener(e -> cargarListadoFiltrado());

        // Eliminar
        eliminarView.getBtnBuscar().addActionListener(e -> buscarProductoParaEliminar());
        eliminarView.getBtnEliminar().addActionListener(e -> eliminarProductoSeleccionado());

        actualizarView.getBtnBuscar().addActionListener(e -> cargarTablaMod());
        actualizarView.getBtnActualizar().addActionListener(e -> actualizarProducto());
        actualizarView.getBtnCancelar().addActionListener(e -> actualizarView.limpiarCampos());
    }

    private void crearProducto() {
        try {
            int codigo = Integer.parseInt(anadirView.getTxtCodigo().getText().trim());
            String nombre = anadirView.getTxtNombre().getText().trim();
            double precio = Double.parseDouble(anadirView.getTxtPrecio().getText().trim());
            if (nombre.isEmpty()) throw new IllegalArgumentException();
            productoDAO.crear(new Producto(codigo, nombre, precio));
            anadirView.mostrarMensaje(mensajes.get("producto.success.creado"));
            anadirView.limpiarCampos();
        } catch (Exception ex) {
            anadirView.mostrarMensaje(mensajes.get("producto.error.cod_precio_nombre"));
        }
    }

    private void listarProductos() {
        DefaultTableModel m = listarView.getModelo();
        m.setRowCount(0);
        for (Producto p : productoDAO.listarTodos()) {
            m.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio()});
        }
    }

    private void cargarListadoFiltrado() {
        String txt = listarView.getTxtBuscar().trim().toLowerCase();
        DefaultTableModel m = listarView.getModelo();
        m.setRowCount(0);
        productoDAO.listarTodos().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(txt))
                .forEach(p -> m.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio()}));
    }

    public void buscarProductoParaEliminar() {
        String filtro = eliminarView.getFiltro();
        String txt    = eliminarView.getTxtBusqueda().trim().toLowerCase();
        DefaultTableModel m = eliminarView.getModelResultado();
        m.setRowCount(0);

        List<Producto> lista;
        if (filtro.equals(mensajes.get("producto.view.eliminar.filtro.codigo"))) {
            try {
                int code = Integer.parseInt(txt);
                Producto p = productoDAO.buscarPorCodigo(code);
                lista = p != null ? List.of(p) : Collections.emptyList();
            } catch (NumberFormatException ex) {
                lista = Collections.emptyList();
            }
        } else {
            lista = productoDAO.buscarPorNombre(txt);
        }

        lista.forEach(p -> m.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio()}));
    }

    public void eliminarProductoSeleccionado() {
        int fila = eliminarView.getTablaResultado().getSelectedRow();
        if (fila < 0) {
            eliminarView.mostrarMensaje(mensajes.get("producto.error.seleccione_producto"));
            return;
        }
        int cod = (int) eliminarView.getModelResultado().getValueAt(fila, 0);
        productoDAO.eliminar(cod);
        eliminarView.mostrarMensaje(mensajes.get("producto.success.eliminado"));
        buscarProductoParaEliminar();
    }

    /** Público: recarga la tabla de modificar */
    public void cargarTablaMod() {
        DefaultTableModel m = actualizarView.getTableModel();
        m.setRowCount(0);
        for (Producto p : productoDAO.listarTodos()) {
            m.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio()});
        }
    }

    /** Público: actualiza el producto seleccionado */
    public void actualizarProducto() {
        try {
            int fila = actualizarView.getTableProductos().getSelectedRow();
            if (fila < 0) throw new IllegalStateException();

            int codigo = (int) actualizarView.getTableModel().getValueAt(fila, 0);
            String nombre = actualizarView.getTxtNombre().getText().trim();
            double precio = Double.parseDouble(actualizarView.getTxtPrecio().getText().trim());

            productoDAO.actualizar(new Producto(codigo, nombre, precio));
            actualizarView.mostrarMensaje(mensajes.get("producto.success.actualizado"));
            cargarTablaMod();
        } catch (Exception e) {
            actualizarView.mostrarMensaje(mensajes.get("producto.error.formato_invalido"));
        }
    }
}
