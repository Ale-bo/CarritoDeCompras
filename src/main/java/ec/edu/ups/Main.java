package ec.edu.ups;

import ec.edu.ups.controlador.*;
import ec.edu.ups.dao.*;
import ec.edu.ups.dao.impl.*;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.Carrito.*;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.InicioDeSesion.*;
import ec.edu.ups.vista.Producto.*;
import ec.edu.ups.vista.Usuario.*;
import javax.swing.*;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;

public class Main {
    public static void main(String[] args) {
        // Asegura que la UI se ejecute en el hilo de despacho de eventos de Swing
        EventQueue.invokeLater(() -> {
            // --- 1. INICIALIZACIÓN DE COMPONENTES ---
            MensajeInternacionalizacionHandler msgH = new MensajeInternacionalizacionHandler("es", "EC");

            // DAOs (Capa de Acceso a Datos)
            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();

            // Vistas Principales y de Login
            MiJDesktopPane desktop = new MiJDesktopPane();
            MenuPrincipalView principal = new MenuPrincipalView(msgH, desktop);
            LoginView loginView = new LoginView(msgH);
            RegistrarUsuarioView registrarFrameView = new RegistrarUsuarioView(); // JFrame para registro inicial

            // Vistas Internas de Usuario (JInternalFrame)
            RegistroView registrarInternalView = new RegistroView(msgH);
            ListarUsuarioView listarUserView = new ListarUsuarioView(msgH);
            EliminarUsuarioView eliminarUserView = new EliminarUsuarioView(msgH);
            ActualizarUsuarioView modUserView = new ActualizarUsuarioView(msgH);

            // Vistas Internas de Producto
            AnadirProductoView addProdView = new AnadirProductoView(msgH);
            ListarProductoView listProdView = new ListarProductoView(msgH);
            ActualizarProductoView modProdView = new ActualizarProductoView(msgH);
            EliminarProductoView delProdView = new EliminarProductoView(msgH);

            // Vistas Internas de Carrito
            AnadirCarritoView addCartView = new AnadirCarritoView(msgH);
            ListarCarritoView listCartView = new ListarCarritoView(msgH);
            EliminarCarritoView delCartView = new EliminarCarritoView(msgH);
            ActualizarCarritoView modCartView = new ActualizarCarritoView(msgH);

            // --- 2. CREACIÓN DE CONTROLADORES ---
            UsuarioController usuarioCtrl = new UsuarioController(usuarioDAO, loginView, registrarFrameView, listarUserView, eliminarUserView, modUserView, principal, msgH, registrarInternalView);
            ProductoController productoCtrl = new ProductoController(productoDAO, msgH, addProdView, listProdView, delProdView, modProdView);
            CarritoController carritoCtrl = new CarritoController(carritoDAO, productoDAO, msgH, addCartView, listCartView, delCartView, modCartView);

            // --- 3. ENLACE DE VISTAS CON SUS CONTROLADORES ---
            loginView.setUsuarioController(usuarioCtrl);
            addProdView.setProductoController(productoCtrl);
            listProdView.setProductoController(productoCtrl);
            delProdView.setProductoController(productoCtrl);
            modProdView.setProductoController(productoCtrl);
            addCartView.setCarritoController(carritoCtrl);
            listCartView.setCarritoController(carritoCtrl);
            delCartView.setCarritoController(carritoCtrl);
            modCartView.setCarritoController(carritoCtrl);

            // --- 4. LÓGICA DE FLUJO DE LA APLICACIÓN ---
            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario u = usuarioCtrl.getUsuarioActual();
                    if (u == null) { // Si se cierra la ventana de login sin autenticar
                        System.exit(0);
                        return;
                    }

                    // Configura y muestra la ventana principal
                    principal.setVisible(true);
                    principal.mostrarMensaje(MessageFormat.format(msgH.get("info.bienvenida"), u.getUsername()));

                    if (u.getRol() == Rol.USUARIO) {
                        principal.deshabilitarMenusAdministrador();
                    }

                    // --- 5. CONFIGURACIÓN DE LOS MENÚS ---
                    // Menú Producto
                    principal.getMenuItemCrearProducto().addActionListener(e1 -> mostrarVentana(principal, addProdView));
                    principal.getMenuItemBuscarProducto().addActionListener(e1 -> mostrarVentana(principal, listProdView));
                    principal.getMenuItemActualizarProducto().addActionListener(e1 -> mostrarVentana(principal, modProdView));
                    principal.getMenuItemEliminarProducto().addActionListener(e1 -> mostrarVentana(principal, delProdView));

                    // Menú Carrito
                    principal.getMenuItemCrearCarrito().addActionListener(e1 -> mostrarVentana(principal, addCartView));
                    principal.getMenuItemListarCarritos().addActionListener(e1 -> mostrarVentana(principal, listCartView));
                    principal.getMenuItemModificarCarrito().addActionListener(e1 -> mostrarVentana(principal, modCartView));
                    principal.getMenuItemEliminarCarrito().addActionListener(e1 -> mostrarVentana(principal, delCartView));

                    // Menú Usuario
                    principal.getMenuItemRegistrarUsuario().addActionListener(e1 -> mostrarVentana(principal, registrarInternalView));
                    principal.getMenuItemListarUsuarios().addActionListener(e1 -> mostrarVentana(principal, listarUserView));
                    principal.getMenuItemModificarUsuario().addActionListener(e1 -> mostrarVentana(principal, modUserView));
                    principal.getMenuItemEliminarUsuario().addActionListener(e1 -> mostrarVentana(principal, eliminarUserView));

                    // Cerrar Sesión
                    principal.getMenuItemCerrarSesion().addActionListener(e1 -> {
                        principal.dispose();
                        main(null); // Reinicia la aplicación para volver al login
                    });
                }
            });

            // Inicia la aplicación mostrando la ventana de login
            loginView.setVisible(true);
        });
    }

    /**
     * Método auxiliar para mostrar una ventana interna (JInternalFrame).
     * Si la ventana no es visible, la añade al panel principal y la muestra.
     * Si ya es visible, simplemente la trae al frente.
     */
    private static void mostrarVentana(MenuPrincipalView p, JInternalFrame v) {
        if (!v.isVisible()) {
            p.getDesktopPane().add(v);
            v.setVisible(true);
        }
        try {
            v.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            System.err.println("Error al seleccionar la ventana: " + e.getMessage());
        }
        v.toFront();
    }
}