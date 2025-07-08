package ec.edu.ups;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.Carrito.ActualizarCarritoView;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.InicioDeSesion.RecuperarContraseñaView;
import ec.edu.ups.vista.InicioDeSesion.RegistrarUsuarioView;
import ec.edu.ups.vista.Producto.AnadirProductoView;
import ec.edu.ups.vista.Producto.ActualizarProductoView;
import ec.edu.ups.vista.Producto.EliminarProductoView;
import ec.edu.ups.vista.Producto.ListarProductoView;
import ec.edu.ups.vista.Carrito.AnadirCarritoView;
import ec.edu.ups.vista.Carrito.ListarCarritoView;
import ec.edu.ups.vista.Carrito.EliminarCarritoView;
import ec.edu.ups.vista.Usuario.EliminarUsuarioView;
import ec.edu.ups.vista.Usuario.ListarUsuarioView;
import ec.edu.ups.vista.Usuario.ActualizarUsuarioView;

import javax.swing.*;
import javax.swing.JInternalFrame;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            MensajeInternacionalizacionHandler msgH = new MensajeInternacionalizacionHandler("es", "EC");

            UsuarioDAO usuarioDAO   = new UsuarioDAOMemoria();
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO   = new CarritoDAOMemoria();

            // MDI principal
            MiJDesktopPane desktop      = new MiJDesktopPane();
            MenuPrincipalView principal = new MenuPrincipalView(msgH, desktop);

            // Vistas de usuario
            LoginView loginView                  = new LoginView(msgH);
            RegistrarUsuarioView registrarView   = new RegistrarUsuarioView();
            ListarUsuarioView listarUserView     = new ListarUsuarioView(msgH);
            EliminarUsuarioView eliminarUserView = new EliminarUsuarioView(msgH);
            ActualizarUsuarioView modUserView    = new ActualizarUsuarioView(msgH);
            RecuperarContraseñaView recView      = new RecuperarContraseñaView(msgH);

            // Controlador de usuario
            UsuarioController usuarioCtrl = new UsuarioController(
                    usuarioDAO,
                    loginView,
                    registrarView,
                    listarUserView,
                    eliminarUserView,
                    modUserView,
                    recView,
                    principal,
                    msgH
            );

            // Vistas de producto
            AnadirProductoView addProdView     = new AnadirProductoView(msgH);
            ListarProductoView listProdView    = new ListarProductoView(msgH);
            ActualizarProductoView modProdView = new ActualizarProductoView(null, msgH);
            EliminarProductoView delProdView   = new EliminarProductoView(null, msgH);

            // Controlador de producto
            new ProductoController(
                    productoDAO,
                    msgH,
                    addProdView,
                    listProdView,
                    delProdView,
                    modProdView
            );

            // Vistas de carrito
            AnadirCarritoView addCartView       = new AnadirCarritoView(msgH);
            ListarCarritoView listCartView      = new ListarCarritoView(msgH);
            EliminarCarritoView delCartView     = new EliminarCarritoView(msgH);
            ActualizarCarritoView modCartView   = new ActualizarCarritoView(msgH);

            // Controlador de carrito
            new CarritoController(
                    carritoDAO,
                    msgH,
                    addCartView,
                    listCartView,
                    delCartView,
                    modCartView
            );

            // ——— PASO 2: Registro del WindowListener ANTES de mostrar el login ———
            loginView.setUsuarioController(usuarioCtrl);
            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario u = usuarioCtrl.getUsuarioActual();
                    if (u == null) {
                        System.exit(0);
                        return;
                    }
                    // Mostrar principal
                    principal.setVisible(true);
                    String tpl    = msgH.get("info.bienvenida");
                    String saludo = MessageFormat.format(tpl, u.getUsername());
                    principal.mostrarMensaje(saludo);
                    if (u.getRol() == Rol.USUARIO) {
                        principal.deshabilitarMenusAdministrador();
                    }
                    // — Menú Usuario —
                    principal.getMenuItemRegistrarUsuario()
                            .addActionListener(ev -> registrarView.setVisible(true));
                    principal.getMenuItemListarUsuarios()
                            .addActionListener(ev -> mostrarVentana(principal, listarUserView));
                    principal.getMenuItemModificarUsuario()
                            .addActionListener(ev -> mostrarVentana(principal, modUserView));
                    principal.getMenuItemEliminarUsuario()
                            .addActionListener(ev -> mostrarVentana(principal, eliminarUserView));
                    // — Menú Producto —
                    principal.getMenuItemCrearProducto()
                            .addActionListener(ev -> mostrarVentana(principal, addProdView));
                    principal.getMenuItemBuscarProducto()
                            .addActionListener(ev -> mostrarVentana(principal, listProdView));
                    principal.getMenuItemActualizarProducto()
                            .addActionListener(ev -> mostrarVentana(principal, modProdView));
                    principal.getMenuItemEliminarProducto()
                            .addActionListener(ev -> mostrarVentana(principal, delProdView));
                    // — Menú Carrito —
                    principal.getMenuItemCrearCarrito()
                            .addActionListener(ev -> mostrarVentana(principal, addCartView));
                    principal.getMenuItemListarCarritos()
                            .addActionListener(ev -> mostrarVentana(principal, listCartView));
                    principal.getMenuItemEliminarCarrito()
                            .addActionListener(ev -> mostrarVentana(principal, delCartView));
                    // — Menú Cerrar sesión e idiomas —
                    principal.getMenuItemCerrarSesion()
                            .addActionListener(ev -> {
                                principal.dispose();
                                main(null);
                            });
                    principal.getMenuItemIdiomaEspanol()
                            .addActionListener(ev -> principal.cambiarIdioma("es", "EC"));
                    principal.getMenuItemIdiomaIngles()
                            .addActionListener(ev -> principal.cambiarIdioma("en", "US"));
                    principal.getMenuItemIdiomaFrances()
                            .addActionListener(ev -> principal.cambiarIdioma("fr", "FR"));
                }
            });

            // Mostrar login
            loginView.setVisible(true);
        });
    }

    private static void mostrarVentana(MenuPrincipalView principal, JInternalFrame ventana) {
        if (!ventana.isVisible()) {
            principal.getDesktopPane().add(ventana);
            ventana.setVisible(true);
        }
        ventana.toFront();
    }
}
