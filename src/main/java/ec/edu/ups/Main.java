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
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.MessageFormat;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MensajeInternacionalizacionHandler msgH = new MensajeInternacionalizacionHandler("es", "EC");
            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();

            MiJDesktopPane desktop = new MiJDesktopPane();
            MenuPrincipalView principal = new MenuPrincipalView(msgH, desktop);

            LoginView loginView = new LoginView(msgH);
            RegistrarUsuarioView registrarView = new RegistrarUsuarioView();
            ListarUsuarioView listarUserView = new ListarUsuarioView(msgH);
            EliminarUsuarioView eliminarUserView = new EliminarUsuarioView(msgH);
            ActualizarUsuarioView modUserView = new ActualizarUsuarioView(msgH);
            RecuperarContraseñaView recView = new RecuperarContraseñaView(msgH);

            AnadirProductoView addProdView = new AnadirProductoView(msgH);
            ListarProductoView listProdView = new ListarProductoView(msgH);
            ActualizarProductoView modProdView = new ActualizarProductoView(msgH);
            EliminarProductoView delProdView = new EliminarProductoView(msgH);

            AnadirCarritoView addCartView = new AnadirCarritoView(msgH);
            ListarCarritoView listCartView = new ListarCarritoView(msgH);
            EliminarCarritoView delCartView = new EliminarCarritoView(msgH);
            ActualizarCarritoView modCartView = new ActualizarCarritoView(msgH);

            UsuarioController usuarioCtrl = new UsuarioController(usuarioDAO, loginView, registrarView, listarUserView, eliminarUserView, modUserView, recView, principal, msgH);
            ProductoController productoCtrl = new ProductoController(productoDAO, msgH, addProdView, listProdView, delProdView, modProdView);
            CarritoController carritoCtrl = new CarritoController(carritoDAO, productoDAO, msgH, addCartView, listCartView, delCartView, modCartView);

            loginView.setUsuarioController(usuarioCtrl);
            delProdView.setProductoController(productoCtrl);
            modProdView.setProductoController(productoCtrl);
            delCartView.setCarritoController(carritoCtrl);
            modCartView.setCarritoController(carritoCtrl);

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario u = usuarioCtrl.getUsuarioActual();
                    if (u == null) {
                        System.exit(0);
                        return;
                    }
                    principal.setVisible(true);
                    principal.mostrarMensaje(MessageFormat.format(msgH.get("info.bienvenida"), u.getUsername()));
                    if (u.getRol() == Rol.USUARIO) {
                        principal.deshabilitarMenusAdministrador();
                    }
                    principal.getMenuItemCrearProducto().addActionListener(ev -> mostrarVentana(principal, addProdView));
                    principal.getMenuItemBuscarProducto().addActionListener(ev -> mostrarVentana(principal, listProdView));
                    principal.getMenuItemActualizarProducto().addActionListener(ev -> mostrarVentana(principal, modProdView));
                    principal.getMenuItemEliminarProducto().addActionListener(ev -> mostrarVentana(principal, delProdView));
                    principal.getMenuItemListarUsuarios().addActionListener(ev -> mostrarVentana(principal, listarUserView));
                    principal.getMenuItemCerrarSesion().addActionListener(ev -> {
                        principal.dispose();
                        main(null);
                    });
                }
            });
            loginView.setVisible(true);
        });
    }

    private static void mostrarVentana(MenuPrincipalView p, JInternalFrame v) {
        if (!v.isVisible()) {
            p.getDesktopPane().add(v);
            v.setVisible(true);
        }
        v.toFront();
    }
}
