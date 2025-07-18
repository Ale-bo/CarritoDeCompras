package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.Producto.AnadirProductoView;
import ec.edu.ups.vista.Producto.EliminarProductoView;
import ec.edu.ups.vista.Producto.ListarProductoView;
import ec.edu.ups.vista.Producto.ActualizarProductoView;

import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class ProductoController {

    private final ProductoDAO productoDAO;
    private final MensajeInternacionalizacionHandler mensajes;
    private final AnadirProductoView anadirView;
    private final ListarProductoView listarView;
    private final EliminarProductoView eliminarView;
    private final ActualizarProductoView actualizarView;

    public ProductoController(ProductoDAO pDAO, MensajeInternacionalizacionHandler msg,
                              AnadirProductoView addV, ListarProductoView listV,
                              EliminarProductoView delV, ActualizarProductoView updV) {
        this.productoDAO = pDAO;
        this.mensajes = msg;
        this.anadirView = addV;
        this.listarView = listV;
        this.eliminarView = delV;
        this.actualizarView = updV;
        configurarEventos();
    }

    private void configurarEventos() {
        // --- Añadir Producto ---
        anadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearProducto();
            }
        });
        anadirView.getBtnLimpiar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirView.limpiarCampos();
            }
        });

        // --- Listar Producto ---
        listarView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });
        listarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarListadoFiltrado();
            }
        });

        // --- Actualizar Producto ---
        actualizarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarProductoParaActualizar();
            }
        });
        actualizarView.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });
        actualizarView.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarView.limpiarCampos();
            }
        });
        actualizarView.getTableProductos().getSelectionModel().addListSelectionListener(this::seleccionarProductoParaActualizar);
    }

    public void crearProducto() {
        try {
            int codigo = Integer.parseInt(anadirView.getTxtCodigo().getText().trim());
            String nombre = anadirView.getTxtNombre().getText().trim();
            double precio = Double.parseDouble(anadirView.getTxtPrecio().getText().trim());
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío.");
            }
            if (productoDAO.buscarPorCodigo(codigo) != null) {
                anadirView.mostrarMensaje("El código del producto ya existe.");
                return;
            }
            productoDAO.crear(new Producto(codigo, nombre, precio));
            anadirView.mostrarMensaje(mensajes.get("producto.success.creado"));
            anadirView.limpiarCampos();
        } catch (Exception ex) {
            anadirView.mostrarMensaje("Error en los datos: " + ex.getMessage());
        }
    }

    public void listarProductos() {
        DefaultTableModel model = listarView.getModelo();
        model.setRowCount(0);
        productoDAO.listarTodos().forEach(p -> model.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio()}));
    }

    public void cargarListadoFiltrado() {
        String criterio = listarView.getTxtBuscar().trim().toLowerCase();
        DefaultTableModel model = listarView.getModelo();
        model.setRowCount(0);
        productoDAO.listarTodos().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(criterio))
                .forEach(p -> model.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getPrecio()}));
    }

    public void cargarProductoParaActualizar() {
        try {
            int codigo = Integer.parseInt(actualizarView.getTxtCodigo().getText());
            Producto p = productoDAO.buscarPorCodigo(codigo);
            if (p != null) {
                actualizarView.getTxtCodigo().setEnabled(false);
                actualizarView.getTxtNombre().setText(p.getNombre());
                actualizarView.getTxtPrecio().setText(String.valueOf(p.getPrecio()));
            } else {
                actualizarView.mostrarMensaje("Producto no encontrado.");
            }
        } catch (NumberFormatException e) {
            actualizarView.mostrarMensaje("Ingrese un código numérico válido.");
        }
    }

    private void seleccionarProductoParaActualizar(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int fila = actualizarView.getTableProductos().getSelectedRow();
            if (fila != -1) {
                DefaultTableModel model = actualizarView.getTableModel();
                actualizarView.getTxtCodigo().setText(model.getValueAt(fila, 0).toString());
                actualizarView.getTxtCodigo().setEnabled(false);
                actualizarView.getTxtNombre().setText(model.getValueAt(fila, 1).toString());
                actualizarView.getTxtPrecio().setText(model.getValueAt(fila, 2).toString());
            }
        }
    }

    public void actualizarProducto() {
        try {
            int codigo = Integer.parseInt(actualizarView.getTxtCodigo().getText());
            String nombre = actualizarView.getTxtNombre().getText().trim();
            double precio = Double.parseDouble(actualizarView.getTxtPrecio().getText().trim());
            if (nombre.isEmpty()) {
                actualizarView.mostrarMensaje("El nombre no puede estar vacío.");
                return;
            }
            productoDAO.actualizar(new Producto(codigo, nombre, precio));
            actualizarView.mostrarMensaje(mensajes.get("producto.success.actualizado"));
            actualizarView.limpiarCampos();
        } catch (Exception ex) {
            actualizarView.mostrarMensaje("Error al actualizar el producto.");
        }
    }

    public void buscarProductoParaEliminar() {
        String filtro = (String) eliminarView.getComboFiltro().getSelectedItem();
        String texto = eliminarView.getTxtBusqueda().trim();
        DefaultTableModel model = eliminarView.getModelResultado();
        model.setRowCount(0);

        List<Producto> lista;
        if (mensajes.get("producto.eliminar.filtro.codigo").equals(filtro)) {
            try {
                int codigo = Integer.parseInt(texto);
                Producto p = productoDAO.buscarPorCodigo(codigo);
                lista = (p != null) ? List.of(p) : Collections.emptyList();
            } catch (NumberFormatException e) {
                lista = Collections.emptyList();
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
            eliminarView.mostrarMensaje(mensajes.get("producto.success.eliminado"));
            buscarProductoParaEliminar();
        } else {
            eliminarView.mostrarMensaje("Debe seleccionar un producto para eliminar.");
        }
    }
}