package ec.edu.ups.modelo;

import java.io.Serializable; // Importar Serializable

/**
 * Clase que representa un ítem dentro de un Carrito de Compras.
 * Un ítem de carrito incluye un producto y la cantidad deseada de ese producto.
 * Implementa {@code Serializable} para permitir el almacenamiento persistente en archivos binarios.
 *
 * @author Ivanna Alexandra Nievecela Pérez
 * @version 1.0
 */
public class ItemCarrito implements Serializable {
    private static final long serialVersionUID = 1L; // Recomendado para Serializable

    private Producto producto; // El producto asociado a este ítem
    private int cantidad;      // La cantidad de este producto en el carrito

    /**
     * Constructor por defecto para ItemCarrito.
     */
    public ItemCarrito() {
    }

    /**
     * Constructor para crear una nueva instancia de ItemCarrito.
     *
     * @param producto El objeto {@code Producto} de este ítem.
     * @param cantidad La cantidad de este producto en el carrito.
     */
    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Getters y Setters con Javadoc
    /**
     * Establece el producto asociado a este ítem de carrito.
     * @param producto El nuevo objeto {@code Producto}.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Establece la cantidad del producto en este ítem de carrito.
     * @param cantidad La nueva cantidad.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el producto asociado a este ítem de carrito.
     * @return El objeto {@code Producto}.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Obtiene la cantidad del producto en este ítem de carrito.
     * @return La cantidad.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Calcula el subtotal de este ítem (precio del producto * cantidad).
     * @return El subtotal del ítem.
     */
    public double getSubtotal() {
        if (producto != null) {
            return producto.getPrecio() * cantidad;
        }
        return 0.0;
    }

    /**
     * Retorna una representación en cadena del objeto ItemCarrito.
     * @return Una cadena que describe el producto, la cantidad y su subtotal.
     */
    @Override
    public String toString() {
        return producto.toString() + " x " + cantidad + " = $" + getSubtotal();
    }

}
