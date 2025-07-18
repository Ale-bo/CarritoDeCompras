package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateoUtil;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.Carrito.AnadirCarritoView;
import ec.edu.ups.vista.Carrito.ListarCarritoView;
import ec.edu.ups.vista.Carrito.EliminarCarritoView;
import ec.edu.ups.vista.Carrito.ActualizarCarritoView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarritoController {

    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final MensajeInternacionalizacionHandler mensajes;

    private final AnadirCarritoView anadirView;
    private final ListarCarritoView listarView;
    private final EliminarCarritoView eliminarView;
    private final ActualizarCarritoView actualizarView;

    private Carrito carritoTemporal;

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
        this.carritoTemporal = new Carrito();
        configurarEventos();
    }

    private void configurarEventos() {
        // --- Añadir Carrito ---
        anadirView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoParaAnadir();
            }
        });
        anadirView.getBtnAnadir().addActionListener(new ActionListener() { // Corregido a getBtnAnadir
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirProductoACarritoTemporal();
            }
        });
        anadirView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCarrito();
            }
        });
        anadirView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarVistaAnadir();
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
        actualizarView.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarView.limpiarCampos();
            }
        });
    }

    public void buscarProductoParaAnadir() {
        try {
            int codigoProducto = Integer.parseInt(anadirView.getCodigo());
            Producto producto = productoDAO.buscarPorCodigo(codigoProducto);
            if (producto != null) {
                anadirView.getTxtNombre().setText(producto.getNombre());
                anadirView.getTxtPrecio().setText(FormateoUtil.formatearMoneda(producto.getPrecio(), mensajes.getLocale()));
            } else {
                anadirView.mostrarMensaje("Producto no encontrado.");
            }
        } catch (NumberFormatException ex) {
            anadirView.mostrarMensaje("El código debe ser un número.");
        }
    }

    public void anadirProductoACarritoTemporal() {
        try {
            int codigoProducto = Integer.parseInt(anadirView.getCodigo());
            int cantidad = Integer.parseInt(anadirView.getTxtCantidad().getText().trim());
            Producto producto = productoDAO.buscarPorCodigo(codigoProducto);

            if (producto != null && cantidad > 0) {
                carritoTemporal.agregarProducto(producto, cantidad);
                actualizarTablaCarritoTemporal();
            } else {
                anadirView.mostrarMensaje("Verifique el producto y la cantidad.");
            }
        } catch (NumberFormatException ex) {
            anadirView.mostrarMensaje("Código y cantidad deben ser números.");
        }
    }

    private void actualizarTablaCarritoTemporal() {
        DefaultTableModel model = anadirView.getTableModel();
        model.setRowCount(0);
        for (ItemCarrito item : carritoTemporal.obtenerItems()) {
            model.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getCantidad(),
                    FormateoUtil.formatearMoneda(item.getSubtotal(), mensajes.getLocale())
            });
        }
        anadirView.getTxtSubtotal().setText(FormateoUtil.formatearMoneda(carritoTemporal.calcularSubtotal(), mensajes.getLocale()));
        anadirView.getTxtIva().setText(FormateoUtil.formatearMoneda(carritoTemporal.calcularIVA(), mensajes.getLocale()));
        anadirView.getTxtTotal().setText(FormateoUtil.formatearMoneda(carritoTemporal.calcularTotal(), mensajes.getLocale()));
    }

    public void guardarCarrito() {
        if (carritoTemporal.estaVacio()) {
            anadirView.mostrarMensaje("El carrito está vacío.");
            return;
        }
        carritoDAO.crear(carritoTemporal);
        anadirView.mostrarMensaje("Carrito guardado con éxito. Código: " + carritoTemporal.getCodigo());
        limpiarVistaAnadir();
    }

    public void limpiarVistaAnadir() {
        anadirView.limpiarCampos();
        this.carritoTemporal = new Carrito();
        actualizarTablaCarritoTemporal();
    }


    public void listarCarritos() {
        DefaultTableModel model = listarView.getModelo();
        model.setRowCount(0);
        List<Carrito> lista = carritoDAO.listarTodos();
        for (Carrito carrito : lista) {
            model.addRow(new Object[]{
                    carrito.getCodigo(),
                    FormateoUtil.formatearFecha(carrito.getFechaCreacion().getTime(), mensajes.getLocale()),
                    FormateoUtil.formatearMoneda(carrito.calcularTotal(), mensajes.getLocale())
            });
        }
    }

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
                        FormateoUtil.formatearFecha(carrito.getFechaCreacion().getTime(), mensajes.getLocale()),
                        FormateoUtil.formatearMoneda(carrito.calcularTotal(), mensajes.getLocale())
                });
            } else {
                listarView.mostrarMensaje("No se encontró ningún carrito con ese código.");
            }
        } catch (NumberFormatException ex) {
            listarView.mostrarMensaje("Por favor, ingrese un código de carrito válido (número).");
        }
    }

    public void buscarCarritoParaEliminar() {
        DefaultTableModel model = eliminarView.getModelResultado();
        model.setRowCount(0);
        try {
            int codigo = Integer.parseInt(eliminarView.getTxtBusqueda().getText());
            Carrito c = carritoDAO.buscarPorCodigo(codigo);
            if (c != null) {
                model.addRow(new Object[]{c.getCodigo(), FormateoUtil.formatearFecha(c.getFechaCreacion().getTime(), mensajes.getLocale()), FormateoUtil.formatearMoneda(c.calcularTotal(), mensajes.getLocale())});
            } else {
                eliminarView.mostrarMensaje("Carrito no encontrado.");
            }
        } catch (NumberFormatException e) {
            eliminarView.mostrarMensaje("Ingrese un código numérico válido.");
        }
    }

    public void eliminarCarritoSeleccionado() {
        int fila = eliminarView.getTablaResultado().getSelectedRow();
        if (fila >= 0) {
            int codigo = (int) eliminarView.getModelResultado().getValueAt(fila, 0);
            carritoDAO.eliminar(codigo);
            eliminarView.mostrarMensaje("Carrito eliminado con éxito.");
            buscarCarritoParaEliminar();
        } else {
            eliminarView.mostrarMensaje("Por favor, seleccione un carrito de la tabla para eliminar.");
        }
    }

    public void cargarCarritoParaModificar() {
        DefaultTableModel model = actualizarView.getModelo();
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
                            FormateoUtil.formatearMoneda(item.getSubtotal(), mensajes.getLocale())
                    });
                }
                actualizarView.getTxtTotal().setText(FormateoUtil.formatearMoneda(carrito.calcularTotal(), mensajes.getLocale()));
            } else {
                actualizarView.mostrarMensaje("Carrito no encontrado.");
            }
        } catch (NumberFormatException e) {
            actualizarView.mostrarMensaje("Ingrese un código de carrito válido.");
        }
    }

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

            int codigoProducto = (int) actualizarView.getModelo().getValueAt(filaSeleccionada, 0);

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
                cargarCarritoParaModificar();
            }

        } catch (NumberFormatException e) {
            actualizarView.mostrarMensaje("La cantidad debe ser un número válido.");
        }
    }
}
