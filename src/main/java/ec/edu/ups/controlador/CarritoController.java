package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.Carrito.AnadirCarritoView;
import ec.edu.ups.vista.Carrito.ListarCarritoView;
import ec.edu.ups.vista.Carrito.EliminarCarritoView;
import ec.edu.ups.vista.Carrito.ActualizarCarritoView;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final MensajeInternacionalizacionHandler mensajes;

    private final AnadirCarritoView anadirView;
    private final ListarCarritoView listarView;
    private final EliminarCarritoView eliminarView;
    private final ActualizarCarritoView actualizarView;

    public CarritoController(CarritoDAO carritoDAO,
                             MensajeInternacionalizacionHandler mensajes,
                             AnadirCarritoView anadirView,
                             ListarCarritoView listarView,
                             EliminarCarritoView eliminarView,
                             ActualizarCarritoView actualizarView) {
        this.carritoDAO = carritoDAO;
        this.mensajes = mensajes;
        this.anadirView = anadirView;
        this.listarView = listarView;
        this.eliminarView = eliminarView;
        this.actualizarView = actualizarView;

        // Registro de listeners
        anadirView.setCarritoController(this);
        listarView.getBtnListar().addActionListener(e -> listarCarritos());
        listarView.getBtnBuscar().addActionListener(e -> buscarEnListado());
        eliminarView.setCarritoController(this);
        actualizarView.setCarritoController(this);
    }

    public void crearCarrito() {
        try {
            int codigo = Integer.parseInt(anadirView.getCodigo());
            int cantidad = Integer.parseInt(anadirView.getCantidad());
            Producto producto = new Producto(codigo, "Producto_" + codigo, 100.0);
            Carrito carrito = new Carrito();
            carrito.agregarProducto(producto, cantidad);

            carritoDAO.crear(carrito);
            anadirView.mostrarMensaje("carrito.creado.exito");
            anadirView.limpiarCampos();
        } catch (Exception ex) {
            anadirView.mostrarMensaje("error.crear.carrito");
        }
    }

    public void listarCarritos() {
        DefaultTableModel model = listarView.getModelo();
        model.setRowCount(0);
        List<Carrito> lista = carritoDAO.listarTodos();
        for (Carrito carrito : lista) {
            model.addRow(new Object[]{carrito.getCodigo(), carrito.getFechaCreacion().getTime(), carrito.calcularTotal()});
        }
    }

    public void buscarEnListado() {
        String criterio = listarView.getTxtBuscar();
        DefaultTableModel model = listarView.getModelo();
        model.setRowCount(0);
        List<Carrito> lista = carritoDAO.listarTodos();
        for (Carrito carrito : lista) {
            if (String.valueOf(carrito.getCodigo()).contains(criterio)) {
                model.addRow(new Object[]{carrito.getCodigo(), carrito.getFechaCreacion().getTime(), carrito.calcularTotal()});
            }
        }
    }

    public void buscarCarritoParaEliminar() {
        String filtro = eliminarView.getFiltro().getSelectedItem().toString();
        String texto = eliminarView.getTxtBusqueda().getText();
        DefaultTableModel model = eliminarView.getModelResultado();
        model.setRowCount(0);

        List<Carrito> lista;
        if (filtro.equals(mensajes.get("carrito.eliminar.filtro.codigo"))) {
            try {
                int codigo = Integer.parseInt(texto);
                Carrito carrito = carritoDAO.buscarPorCodigo(codigo);
                lista = (carrito != null) ? List.of(carrito) : List.of();
            } catch (NumberFormatException e) {
                lista = List.of();
            }
        } else {
            lista = carritoDAO.listarTodos();
        }

        for (Carrito carrito : lista) {
            model.addRow(new Object[]{carrito.getCodigo(), carrito.getFechaCreacion().getTime(), carrito.calcularTotal()});
        }
    }

    public void eliminarCarritoSeleccionado() {
        int fila = eliminarView.getTablaResultado().getSelectedRow();
        if (fila >= 0) {
            int codigo = (int) eliminarView.getModelResultado().getValueAt(fila, 0);
            carritoDAO.eliminar(codigo);
            eliminarView.mostrarMensaje("carrito.eliminar.exito");
            buscarCarritoParaEliminar();
        } else {
            eliminarView.mostrarMensaje("carrito.seleccionar.fila");
        }
    }

    public void cargarTablaMod() {
        DefaultTableModel model = actualizarView.getTableModel();
        model.setRowCount(0);
        List<Carrito> lista = carritoDAO.listarTodos();
        for (Carrito carrito : lista) {
            model.addRow(new Object[]{carrito.getCodigo(), carrito.getFechaCreacion().getTime(), carrito.calcularTotal()});
        }
    }

    public void actualizarCarrito() {
        try {
            int codigo = Integer.parseInt(actualizarView.getTxtCodigo().getText());
            int cantidad = Integer.parseInt(actualizarView.getTxtCantidad().getText());
            Carrito carrito = carritoDAO.buscarPorCodigo(codigo);
            if (carrito != null) {
                carrito.eliminarProducto(codigo);
                Producto producto = new Producto(codigo, "Producto_" + codigo, 100.0);
                carrito.agregarProducto(producto, cantidad);
                carritoDAO.actualizar(carrito);
                actualizarView.mostrarMensaje("carrito.actualizar.exito");
                cargarTablaMod();
            } else {
                actualizarView.mostrarMensaje("carrito.no.encontrado");
            }
        } catch (Exception e) {
            actualizarView.mostrarMensaje("error.actualizar.carrito");
        }
    }
}

