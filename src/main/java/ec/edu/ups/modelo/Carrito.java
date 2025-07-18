package ec.edu.ups.modelo;

import java.io.Serializable; // Importar Serializable
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que representa un Carrito de Compras en el sistema.
 * Contiene un identificador único, la fecha de creación y una lista de ítems de carrito.
 * Implementa {@code Serializable} para permitir el almacenamiento persistente en archivos binarios.
 *
 * @author Ivanna Alexandra Nievecela Pérez
 * @version 1.0
 */
public class Carrito implements Serializable {
    private static final long serialVersionUID = 1L; // Recomendado para Serializable

    private final double IVA = 0.12; // Valor del IVA fijo

    private static int contador = 1; // Contador estático para generar códigos de carrito únicos

    private int codigo;
    private GregorianCalendar fechaCreacion;
    private List<ItemCarrito> items;

    /**
     * Constructor por defecto para la clase Carrito.
     * Asigna un código único, inicializa la lista de ítems y la fecha de creación.
     */
    public Carrito() {
        codigo = contador++; // Asigna un código único e incrementa el contador
        items = new ArrayList<>();
        fechaCreacion = new GregorianCalendar(); // Fecha y hora actuales
    }

    /**
     * Obtiene el código único del carrito.
     * @return El código del carrito.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código único del carrito.
     * @param codigo El nuevo código del carrito.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la fecha y hora de creación del carrito.
     * @return Un objeto {@code GregorianCalendar} con la fecha de creación.
     */
    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece la fecha y hora de creación del carrito.
     * @param fechaCreacion El nuevo objeto {@code GregorianCalendar} para la fecha de creación.
     */
    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Agrega un producto al carrito con una cantidad específica.
     * Si el producto ya existe en el carrito, la cantidad se añade.
     * @param producto El objeto {@code Producto} a agregar.
     * @param cantidad La cantidad del producto a agregar.
     */
    public void agregarProducto(Producto producto, int cantidad) {
        // Verificar si el producto ya existe en el carrito
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad); // Actualizar cantidad
                return;
            }
        }
        // Si no existe, añadir un nuevo ItemCarrito
        items.add(new ItemCarrito(producto, cantidad));
    }

    /**
     * Elimina un producto del carrito por su código.
     * @param codigoProducto El código del producto a eliminar.
     */
    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    /**
     * Vacía completamente el carrito, eliminando todos los ítems.
     */
    public void vaciarCarrito() {
        items.clear();
    }

    /**
     * Obtiene la lista de ítems de carrito actuales.
     * @return Una {@code List} de objetos {@code ItemCarrito}.
     */
    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    /**
     * Verifica si el carrito está vacío.
     * @return true si el carrito no contiene ítems, false en caso contrario.
     */
    public boolean estaVacio() {
        return items.isEmpty();
    }

    /**
     * Calcula el subtotal del carrito (suma de precios * cantidad de cada ítem, sin IVA).
     * @return El subtotal del carrito.
     */
    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getSubtotal(); // ItemCarrito ya calcula su subtotal
        }
        return subtotal;
    }

    /**
     * Calcula el monto del IVA para el subtotal del carrito.
     * @return El monto del IVA.
     */
    public double calcularIVA() {
        return calcularSubtotal() * IVA;
    }

    /**
     * Calcula el total final del carrito (subtotal + IVA).
     * @return El total final del carrito.
     */
    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

    /**
     * Retorna una representación en cadena del objeto Carrito.
     * @return Una cadena que describe el carrito, incluyendo IVA, código, fecha e ítems.
     */
    @Override
    public String toString() {
        return "Carrito{" +
                "IVA=" + IVA +
                ", codigo=" + codigo +
                ", fechaCreacion=" + fechaCreacion.getTime() +
                ", items=" + items +
                '}';
    }
}