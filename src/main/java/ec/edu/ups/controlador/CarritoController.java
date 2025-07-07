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

        // Configurar los botones y sus acciones
        anadirView.getBtnAceptar().addActionListener(e -> crearCarrito());
        anadirView.getBtnLimpiar().addActionListener(e -> anadirView.limpiarCampos());

        listarView.getBtnListar().addActionListener(e -> listarCarritos());
        listarView.getBtnBuscar().addActionListener(e -> buscarEnListado());

        eliminarView.getBtnBuscar().addActionListener(e -> buscarCarritoParaEliminar());
        eliminarView.getBtnEliminar().addActionListener(e -> eliminarCarritoSeleccionado());

        actualizarView.getBtnBuscar().addActionListener(e -> cargarTablaMod());
        actualizarView.getBtnActualizar().addActionListener(e -> actualizarCarrito());
        actualizarView.getBtnCancelar().addActionListener(e -> actualizarView.limpiarCampos());
    }

    // Método para crear un carrito
    public void crearCarrito() {
        try {
            int codigo = Integer.parseInt(anadirView.getCodigo());
            int cantidad = Integer.parseInt(anadirView.getCantidad());

            // Validación de que el producto exista en el sistema
            Producto producto = new Producto(codigo, "Producto_" + codigo, 100.0); // Ejemplo, deberías obtener datos del producto real desde un ProductoDAO
            Carrito carrito = new Carrito();

            // Crear un item para el carrito
            ItemCarrito itemCarrito = new ItemCarrito(producto, cantidad);
            carrito.agregarProducto(producto, cantidad);

            // Guardar el carrito en el DAO
            carritoDAO.crear(carrito);
            anadirView.mostrarMensaje("carrito.creado.exito");
            anadirView.limpiarCampos();
        } catch (Exception ex) {
            anadirView.mostrarMensaje("error.crear.carrito");
        }
    }

    // Método para listar los carritos
    public void listarCarritos() {
        DefaultTableModel model = listarView.getModelo();
        model.setRowCount(0);
        List<Carrito> lista = carritoDAO.listarTodos();
        for (Carrito carrito : lista) {
            model.addRow(new Object[]{carrito.getCodigo(), carrito.getFechaCreacion().getTime(), carrito.calcularTotal()});
        }
    }

    // Método para buscar en el listado de carritos
    public void buscarEnListado() {
        String criterio = listarView.getTxtBuscar();
        DefaultTableModel model = listarView.getModelo();
        model.setRowCount(0);

        List<Carrito> lista = carritoDAO.listarTodos(); // Aquí se puede buscar por código o nombre si se ajusta
        for (Carrito carrito : lista) {
            if (String.valueOf(carrito.getCodigo()).contains(criterio)) {
                model.addRow(new Object[]{carrito.getCodigo(), carrito.getFechaCreacion().getTime(), carrito.calcularTotal()});
            }
        }
    }

    // Método para buscar un carrito para eliminar
    public void buscarCarritoParaEliminar() {
        String filtro = eliminarView.getFiltro();
        String texto = eliminarView.getTxtBusqueda();
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
            lista = carritoDAO.listarTodos(); // Aquí puedes mejorar la búsqueda por nombre si lo deseas
        }

        for (Carrito carrito : lista) {
            model.addRow(new Object[]{carrito.getCodigo(), carrito.getFechaCreacion().getTime(), carrito.calcularTotal()});
        }
    }

    // Método para eliminar un carrito
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

    // Método para cargar la tabla de carritos en la vista de actualización
    public void cargarTablaMod() {
        DefaultTableModel model = actualizarView.getTableModel();
        model.setRowCount(0);
        List<Carrito> lista = carritoDAO.listarTodos();
        for (Carrito carrito : lista) {
            model.addRow(new Object[]{carrito.getCodigo(), carrito.getFechaCreacion().getTime(), carrito.calcularTotal()});
        }
    }

    // Método para actualizar un carrito
    public void actualizarCarrito() {
        try {
            int codigo = Integer.parseInt(actualizarView.getTxtCodigo().getText());
            int cantidad = Integer.parseInt(actualizarView.getTxtCantidad().getText());

            Carrito carrito = carritoDAO.buscarPorCodigo(codigo);
            if (carrito != null) {
                Producto producto = new Producto(codigo, "Producto_" + codigo, 100.0); // Obtenemos el producto por su código
                carrito.eliminarProducto(codigo); // Elimina el producto si ya existe en el carrito
                carrito.agregarProducto(producto, cantidad); // Añadimos el nuevo producto con la cantidad actualizada
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

