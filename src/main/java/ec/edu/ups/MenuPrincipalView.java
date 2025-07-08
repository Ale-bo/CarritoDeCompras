package ec.edu.ups;

import ec.edu.ups.MiJDesktopPane;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.lang.reflect.Method;

public class MenuPrincipalView extends JFrame {

    private final MensajeInternacionalizacionHandler mensajeHandler;
    private final MiJDesktopPane desktopPane;

    private JMenuBar menuBar;
    private JMenu menuProducto, menuCarrito, menuUsuario, menuIdioma;

    private JMenuItem
            menuItemCrearProducto,
            menuItemEliminarProducto,
            menuItemActualizarProducto,
            menuItemBuscarProducto,
            menuItemCrearCarrito,
            menuItemListarCarritos,
            menuItemModificarCarrito,
            menuItemVerDetallesCarrito,
            menuItemEliminarCarrito,
            menuItemRegistrarUsuario,
            menuItemListarUsuarios,
            menuItemModificarUsuario,
            menuItemEliminarUsuario,
            menuItemCerrarSesion,
            menuItemIdiomaEspanol,
            menuItemIdiomaIngles,
            menuItemIdiomaFrances;

    public MenuPrincipalView(MensajeInternacionalizacionHandler mh, MiJDesktopPane desktop) {
        this.mensajeHandler = mh;
        this.desktopPane = desktop;
        initComponents();
        attachIdiomaListeners();
        setTitle(mh.get("app.titulo"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        setContentPane(desktopPane);
        menuBar = new JMenuBar();

        // — Producto
        menuProducto = new JMenu(mensajeHandler.get("menu.producto"));
        menuItemCrearProducto    = new JMenuItem(mensajeHandler.get("producto.crear"));
        menuItemEliminarProducto = new JMenuItem(mensajeHandler.get("producto.eliminar"));
        menuItemActualizarProducto = new JMenuItem(mensajeHandler.get("producto.actualizar"));
        menuItemBuscarProducto   = new JMenuItem(mensajeHandler.get("producto.buscar"));
        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemBuscarProducto);
        menuBar.add(menuProducto);

        // — Carrito
        menuCarrito = new JMenu(mensajeHandler.get("menu.carrito"));
        menuItemCrearCarrito       = new JMenuItem(mensajeHandler.get("carrito.crear"));
        menuItemListarCarritos     = new JMenuItem(mensajeHandler.get("carrito.buscar"));
        menuItemModificarCarrito   = new JMenuItem(mensajeHandler.get("carrito.modificar"));
        menuItemVerDetallesCarrito = new JMenuItem(mensajeHandler.get("carrito.view.listar.detalles"));
        menuItemEliminarCarrito    = new JMenuItem(mensajeHandler.get("carrito.eliminar"));
        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemListarCarritos);
        menuCarrito.add(menuItemModificarCarrito);
        menuCarrito.add(menuItemVerDetallesCarrito);
        menuCarrito.add(menuItemEliminarCarrito);
        menuBar.add(menuCarrito);

        // — Usuario
        menuUsuario = new JMenu(mensajeHandler.get("menu.usuario"));
        menuItemRegistrarUsuario = new JMenuItem(mensajeHandler.get("usuario.registrar"));
        menuItemListarUsuarios   = new JMenuItem(mensajeHandler.get("usuario.listar"));
        menuItemModificarUsuario = new JMenuItem(mensajeHandler.get("usuario.modificar"));
        menuItemEliminarUsuario  = new JMenuItem(mensajeHandler.get("usuario.eliminar"));
        menuItemCerrarSesion     = new JMenuItem(mensajeHandler.get("usuario.cerrarSesion"));
        menuUsuario.add(menuItemRegistrarUsuario);
        menuUsuario.add(menuItemListarUsuarios);
        menuUsuario.add(menuItemModificarUsuario);
        menuUsuario.add(menuItemEliminarUsuario);
        menuUsuario.addSeparator();
        menuUsuario.add(menuItemCerrarSesion);
        menuBar.add(menuUsuario);

        // — Idioma
        menuIdioma = new JMenu(mensajeHandler.get("menu.idioma"));
        menuItemIdiomaEspanol = new JMenuItem(mensajeHandler.get("menu.idioma.es"));
        menuItemIdiomaIngles  = new JMenuItem(mensajeHandler.get("menu.idioma.en"));
        menuItemIdiomaFrances = new JMenuItem(mensajeHandler.get("menu.idioma.fr"));
        menuIdioma.add(menuItemIdiomaEspanol);
        menuIdioma.add(menuItemIdiomaIngles);
        menuIdioma.add(menuItemIdiomaFrances);
        menuBar.add(menuIdioma);

        setJMenuBar(menuBar);
    }

    private void attachIdiomaListeners() {
        menuItemIdiomaEspanol.addActionListener(ev -> cambiarIdioma("es", "EC"));
        menuItemIdiomaIngles .addActionListener(ev -> cambiarIdioma("en", "US"));
        menuItemIdiomaFrances.addActionListener(ev -> cambiarIdioma("fr", "FR"));
    }

    public MiJDesktopPane getDesktopPane() {
        return desktopPane;
    }

    public void cambiarIdioma(String lang, String country) {
        mensajeHandler.setLenguaje(lang, country);

        // Menús
        menuProducto.setText(mensaje("menu.producto"));
        menuCarrito.setText(mensaje("menu.carrito"));
        menuUsuario.setText(mensaje("menu.usuario"));
        menuIdioma.setText(mensaje("menu.idioma"));

        // Ítems Producto
        menuItemCrearProducto.setText(mensaje("producto.crear"));
        menuItemEliminarProducto.setText(mensaje("producto.eliminar"));
        menuItemActualizarProducto.setText(mensaje("producto.actualizar"));
        menuItemBuscarProducto.setText(mensaje("producto.buscar"));

        // Ítems Carrito
        menuItemCrearCarrito.setText(mensaje("carrito.crear"));
        menuItemListarCarritos.setText(mensaje("carrito.buscar"));
        menuItemModificarCarrito.setText(mensaje("carrito.modificar"));
        menuItemVerDetallesCarrito.setText(mensaje("carrito.view.listar.detalles"));
        menuItemEliminarCarrito.setText(mensaje("carrito.eliminar"));

        // Ítems Usuario
        menuItemRegistrarUsuario.setText(mensaje("usuario.registrar"));
        menuItemListarUsuarios.setText(mensaje("usuario.listar"));
        menuItemModificarUsuario.setText(mensaje("usuario.modificar"));
        menuItemEliminarUsuario.setText(mensaje("usuario.eliminar"));
        menuItemCerrarSesion.setText(mensaje("usuario.cerrarSesion"));

        // Ítems Idioma
        menuItemIdiomaEspanol.setText(mensaje("menu.idioma.es"));
        menuItemIdiomaIngles.setText(mensaje("menu.idioma.en"));
        menuItemIdiomaFrances.setText(mensaje("menu.idioma.fr"));

        // Refrescar todos los JInternalFrame
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            try {
                Method m = frame.getClass().getMethod("actualizarIdioma");
                m.invoke(frame);
            } catch (Exception ignored) {}
        }
    }

    private String mensaje(String clave) {
        return mensajeHandler.get(clave);
    }

    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void deshabilitarMenusAdministrador() {
        menuItemCrearProducto.setEnabled(false);
        menuItemEliminarProducto.setEnabled(false);
        menuItemActualizarProducto.setEnabled(false);
        menuItemBuscarProducto.setEnabled(false);
        menuItemCrearCarrito.setEnabled(false);
        menuItemListarCarritos.setEnabled(false);
        menuItemModificarCarrito.setEnabled(false);
        menuItemVerDetallesCarrito.setEnabled(false);
        menuItemEliminarCarrito.setEnabled(false);
        menuItemRegistrarUsuario.setEnabled(false);
        menuItemListarUsuarios.setEnabled(false);
        menuItemModificarUsuario.setEnabled(false);
        menuItemEliminarUsuario.setEnabled(false);
    }

    // ——— Getters ———
    public JMenuItem getMenuItemCrearProducto()   { return menuItemCrearProducto; }
    public JMenuItem getMenuItemBuscarProducto()  { return menuItemBuscarProducto; }
    public JMenuItem getMenuItemActualizarProducto() { return menuItemActualizarProducto; }
    public JMenuItem getMenuItemEliminarProducto()  { return menuItemEliminarProducto; }
    public JMenuItem getMenuItemCrearCarrito()    { return menuItemCrearCarrito; }
    public JMenuItem getMenuItemListarCarritos()  { return menuItemListarCarritos; }
    public JMenuItem getMenuItemEliminarCarrito() { return menuItemEliminarCarrito; }
    public JMenuItem getMenuItemRegistrarUsuario(){ return menuItemRegistrarUsuario; }
    public JMenuItem getMenuItemListarUsuarios()  { return menuItemListarUsuarios; }
    public JMenuItem getMenuItemModificarUsuario(){ return menuItemModificarUsuario; }
    public JMenuItem getMenuItemEliminarUsuario() { return menuItemEliminarUsuario; }
    public JMenuItem getMenuItemCerrarSesion()    { return menuItemCerrarSesion; }
    public JMenuItem getMenuItemIdiomaEspanol()   { return menuItemIdiomaEspanol; }
    public JMenuItem getMenuItemIdiomaIngles()    { return menuItemIdiomaIngles; }
    public JMenuItem getMenuItemIdiomaFrances()   { return menuItemIdiomaFrances; }
}
