package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.Carrito.AnadirCarritoView;
import ec.edu.ups.vista.Carrito.ListarCarritoView;
import ec.edu.ups.vista.Carrito.EliminarCarritoView;
import ec.edu.ups.vista.Carrito.ActualizarCarritoView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final MensajeInternacionalizacionHandler mensajes;

    private final AnadirCarritoView anadirView;
    private final ListarCarritoView listarView;
    private final EliminarCarritoView eliminarView;
    private final ActualizarCarritoView actualizarView;

    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO,
                             MensajeInternacionalizacionHandler mensajes,
                             AnadirCarritoView anadirView, ListarCarritoView listarView,
                             EliminarCarritoView eliminarView, ActualizarCarritoView actualizarView) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.mensajes = mensajes;
        this.anadirView = anadirView;
        this.listarView = listarView;
        this.eliminarView = eliminarView;
        this.actualizarView = actualizarView;

        configurarEventos();
    }

    private void configurarEventos() {
        // --- Añadir Carrito ---
        anadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearCarrito();
            }
        });

        // --- Listar Carrito ---
        listarView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarCarritos();
            }
        });
        listarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEnListado();
            }
        });

        // --- Eliminar Carrito ---
        eliminarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarritoParaEliminar();
            }
        });
        eliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCarritoSeleccionado();
            }
        });

        // --- Actualizar Carrito ---
        actualizarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarCarritoParaModificar();
            }
        });
        actualizarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarItemCarrito();
            }
        });
    }


    // El parámetro (ActionEvent e) se elimina de la definición del método.
    public void crearCarrito() {
        try {
            int codigoProducto = Integer.parseInt(anadirView.getCodigo());
            int cantidad = Integer.parseInt(anadirView.getCantidad());

            Producto producto = productoDAO.buscarPorCodigo(codigoProducto);
            if (producto == null) {
                anadirView.mostrarMensaje("Producto no encontrado.");
                return;
            }

            Carrito carrito = new Carrito();
            carrito.agregarProducto(producto, cantidad);

            carritoDAO.crear(carrito);
            anadirView.mostrarMensaje("Carrito creado con éxito.");
            anadirView.limpiarCampos();
        } catch (NumberFormatException ex) {
            anadirView.mostrarMensaje("El código y la cantidad deben ser números.");
        }
    }

    public void listarCarritos() {
        DefaultTableModel model = listarView.getModelo();
        model.setRowCount(0);
        List<Carrito> lista = carritoDAO.listarTodos();
        for (Carrito carrito : lista) {
            model.addRow(new Object[]{
                    carrito.getCodigo(),
                    carrito.getFechaCreacion().getTime(),
                    carrito.calcularTotal()
            });
        }
    }

    // ##### LÓGICA IMPLEMENTADA #####
    public void buscarEnListado() {
        String criterio = listarView.getTxtBuscar();
        DefaultTableModel model = listarView.getModelo();
        model.setRowCount(0);

        try {
            int codigoCarrito = Integer.parseInt(criterio);
            Carrito carrito = carritoDAO.buscarPorCodigo(codigoCarrito);
            if (carrito != null) {
                model.addRow(new Object[]{
                        carrito.getCodigo(),
                        carrito.getFechaCreacion().getTime(),
                        carrito.calcularTotal()
                });
            } else {
                listarView.mostrarMensaje("No se encontró ningún carrito con ese código.");
            }
        } catch (NumberFormatException ex) {
            listarView.mostrarMensaje("Por favor, ingrese un código de carrito válido (número).");
        }
    }

    // ##### LÓGICA IMPLEMENTADA #####
    public void buscarCarritoParaEliminar() {
        DefaultTableModel model = eliminarView.getModelResultado();
        model.setRowCount(0);
        try {
            int codigo = Integer.parseInt(eliminarView.getTxtBusqueda().getText());
            Carrito c = carritoDAO.buscarPorCodigo(codigo);
            if (c != null) {
                model.addRow(new Object[]{c.getCodigo(), c.getFechaCreacion().getTime(), c.calcularTotal()});
            } else {
                eliminarView.mostrarMensaje("Carrito no encontrado.");
            }
        } catch (NumberFormatException e) {
            eliminarView.mostrarMensaje("Ingrese un código numérico válido.");
        }
    }

    // ##### LÓGICA IMPLEMENTADA #####
    public void eliminarCarritoSeleccionado() {
        int fila = eliminarView.getTablaResultado().getSelectedRow();
        if (fila >= 0) {
            int codigo = (int) eliminarView.getModelResultado().getValueAt(fila, 0);
            carritoDAO.eliminar(codigo);
            eliminarView.mostrarMensaje("Carrito eliminado con éxito.");
            buscarCarritoParaEliminar(); // Refresca la tabla
        } else {
            eliminarView.mostrarMensaje("Por favor, seleccione un carrito de la tabla para eliminar.");
        }
    }

    // ##### LÓGICA IMPLEMENTADA #####
    public void cargarCarritoParaModificar() {
        DefaultTableModel model = actualizarView.getTableModel();
        model.setRowCount(0);
        try {
            int codigo = Integer.parseInt(actualizarView.getTxtCodigo().getText());
            Carrito carrito = carritoDAO.buscarPorCodigo(codigo);
            if (carrito != null) {
                for(ItemCarrito item : carrito.obtenerItems()) {
                    model.addRow(new Object[]{
                            item.getProducto().getCodigo(),
                            item.getProducto().getNombre(),
                            item.getCantidad(),
                            item.getSubtotal()
                    });
                }
            } else {
                actualizarView.mostrarMensaje("Carrito no encontrado.");
            }
        } catch (NumberFormatException e) {
            actualizarView.mostrarMensaje("Ingrese un código de carrito válido.");
        }
    }

    // ##### LÓGICA IMPLEMENTADA #####
    public void actualizarItemCarrito() {
        int filaSeleccionada = actualizarView.getTblCarritos().getSelectedRow();
        if (filaSeleccionada < 0) {
            actualizarView.mostrarMensaje("Seleccione un producto de la tabla para actualizar.");
            return;
        }

        try {
            int codigoCarrito = Integer.parseInt(actualizarView.getTxtCodigo().getText());
            int nuevaCantidad = Integer.parseInt(actualizarView.getTxtCantidad().getText());

            if (nuevaCantidad <= 0) {
                actualizarView.mostrarMensaje("La cantidad debe ser mayor a cero.");
                return;
            }

            int codigoProducto = (int) actualizarView.getTableModel().getValueAt(filaSeleccionada, 0);

            Carrito carrito = carritoDAO.buscarPorCodigo(codigoCarrito);
            if (carrito != null) {
                // Actualizar la cantidad del item específico
                for (ItemCarrito item : carrito.obtenerItems()) {
                    if (item.getProducto().getCodigo() == codigoProducto) {
                        item.setCantidad(nuevaCantidad);
                        break;
                    }
                }
                carritoDAO.actualizar(carrito);
                actualizarView.mostrarMensaje("Carrito actualizado con éxito.");
                cargarCarritoParaModificar(); // Refrescar la tabla
            }

        } catch (NumberFormatException e) {
            actualizarView.mostrarMensaje("La cantidad debe ser un número válido.");
        }
    }
}
