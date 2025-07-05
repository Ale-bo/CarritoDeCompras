package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.Producto.AnadirProductoView;
import ec.edu.ups.vista.Producto.ListarProductoView;
import ec.edu.ups.vista.Producto.EliminarProductoView;
import ec.edu.ups.vista.Producto.ActualizarProductoView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductoController {

    private final ProductoDAO productoDAO;
    private final MensajeInternacionalizacionHandler mensajes;

    private final AnadirProductoView anadirView;
    private final ListarProductoView listarView;
    private final EliminarProductoView eliminarView;
    private final ActualizarProductoView actualizarView;

    public ProductoController(ProductoDAO productoDAO, MensajeInternacionalizacionHandler mensajes, AnadirProductoView anadirView, ListarProductoView listarView, EliminarProductoView eliminarView, ActualizarProductoView actualizarView) {
        this.productoDAO = productoDAO;
        this.mensajes = mensajes;
        this.anadirView = anadirView;
        this.listarView = listarView;
        this.eliminarView = eliminarView;
        this.actualizarView = actualizarView;

        // Añadir producto
        this.anadirView.getBtnAceptar().addActionListener(e -> crearProducto());
        this.anadirView.getBtnLimpiar().addActionListener(e -> anadirView.limpiarCampos());

        // Listar y buscar en ListarProductoView
        this.listarView.getBtnListar().addActionListener(e -> listarProductos());
        this.listarView.getBtnBuscar().addActionListener(e -> buscarEnListado());

        // Buscar y eliminar en EliminarProductoView
        this.eliminarView.getBtnBuscar().addActionListener(e -> buscarProductoParaEliminar());
        this.eliminarView.getBtnEliminar().addActionListener(e -> eliminarProductoSeleccionado());

        // Cargar y actualizar en ActualizarProductoView
        this.actualizarView.getBtnBuscar().addActionListener(e -> cargarTablaMod());
        this.actualizarView.getBtnActualizar().addActionListener(e -> actualizarProducto());
        this.actualizarView.getBtnCancelar().addActionListener(e -> actualizarView.limpiarCampos());
    }

    public void crearProducto() {
        try {
            int codigo = Integer.parseInt(anadirView.getCodigo());
            String nombre = anadirView.getNombre();
            double precio = Double.parseDouble(anadirView.getPrecio());

            Producto p = new Producto(codigo, nombre, precio);
            productoDAO.crear(p);
            anadirView.mostrarMensaje("producto.creado.exito");
            anadirView.limpiarCampos();
        } catch (Exception ex) {
            anadirView.mostrarMensaje("error.crear.producto");
        }
    }

    public void listarProductos() {
        DefaultTableModel model = listarView.getModelo();
        model.setRowCount(0);
        List<Producto> lista = productoDAO.listarTodos();
        for (Producto p : lista) {
            model.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio()});
        }
    }

    public void buscarEnListado() {
        String criterio = listarView.getTxtBuscar();
        DefaultTableModel model = listarView.getModelo();
        model.setRowCount(0);
        List<Producto> lista = productoDAO.listarTodos();
        for (Producto p : lista) {
            model.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio()});
        }
    }

    public void buscarProductoParaEliminar() {
        String filtro = eliminarView.getFiltro();
        String texto = eliminarView.getTxtBusqueda();
        DefaultTableModel model = eliminarView.getModelResultado();
        model.setRowCount(0);

        List<Producto> lista;
        if (filtro.equals(mensajes.get("producto.eliminar.filtro.codigo"))) {
            try {
                int code = Integer.parseInt(texto);
                Producto p = productoDAO.buscarPorCodigo(code);
                lista = (p != null) ? List.of(p) : List.of();
            } catch (NumberFormatException e) {
                lista = List.of();
            }
        } else {
            lista = productoDAO.buscarPorNombre(texto);
        }

        for (Producto p : lista) {
            model.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio()});
        }
    }

    public void eliminarProductoSeleccionado() {
        int fila = eliminarView.getTablaResultado().getSelectedRow();
        if (fila >= 0) {
            int codigo = (int) eliminarView.getModelResultado().getValueAt(fila, 0);
            productoDAO.eliminar(codigo);
            eliminarView.mostrarMensaje("producto.eliminar.exito");
            buscarProductoParaEliminar();
        } else {
            eliminarView.mostrarMensaje("producto.seleccionar.fila");
        }
    }

    public void cargarTablaMod() {
        DefaultTableModel model = actualizarView.getTableModel();
        model.setRowCount(0);
        List<Producto> lista = productoDAO.listarTodos();
        for (Producto p : lista) {
            model.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio()});
        }
    }

    public void actualizarProducto() {
        try {
            JTable tabla = actualizarView.getTableProductos();
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int codigo = (int) actualizarView.getTableModel().getValueAt(fila, 0);
                String nuevoNombre = actualizarView.getTxtNombre().getText();
                double nuevoPrecio = Double.parseDouble(actualizarView.getTxtPrecio().getText());

                Producto p = new Producto(codigo, nuevoNombre, nuevoPrecio);
                productoDAO.actualizar(p);
                actualizarView.mostrarMensaje(mensajes.get("producto.actualizar.exito"));
                cargarTablaMod();
            } else {
                actualizarView.mostrarMensaje(mensajes.get("producto.seleccionar.fila"));
            }
        } catch (Exception e) {
            actualizarView.mostrarMensaje(mensajes.get("error.actualizar.producto"));
        }
    }
}

