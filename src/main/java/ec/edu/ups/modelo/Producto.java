package ec.edu.ups.modelo;

import java.io.Serializable; // Importar Serializable

/**
 * Clase que representa un Producto disponible en el carrito de compras.
 * Implementa {@code Serializable} para permitir el almacenamiento persistente en archivos binarios.
 *
 * @author Ivanna Alexandra Nievecela Pérez
 * @version 1.0
 */
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L; // Recomendado para Serializable

    private int codigo;
    private String nombre;
    private double precio;

    /**
     * Constructor obsoleto o de uso limitado. No recomendado para uso general.
     * @param codigo Código del producto como String (debería ser int).
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     * @deprecated Este constructor puede causar errores de tipo si el código no es un entero. Use {@link #Producto(int, String, double)}.
     */
    @Deprecated
    public Producto(String codigo, String nombre, double precio) {
        // La implementación aquí es incorrecta ya que el código es int.
        // Se mantiene para compatibilidad con código existente que pueda usarlo.
        this.nombre = nombre;
        this.precio = precio;
        try {
            this.codigo = Integer.parseInt(codigo);
        } catch (NumberFormatException e) {
            System.err.println("Advertencia: Código de producto no numérico en constructor String: " + codigo);
            this.codigo = -1; // O manejar de otra forma
        }
    }

    /**
     * Constructor para crear una nueva instancia de Producto.
     *
     * @param codigo El código único del producto.
     * @param nombre El nombre del producto.
     * @param precio El precio unitario del producto.
     */
    public Producto(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    // Getters y Setters con Javadoc
    /**
     * Establece el código del producto.
     * @param codigo El nuevo código del producto.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Establece el nombre del producto.
     * @param nombre El nuevo nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el precio del producto.
     * @param precio El nuevo precio del producto.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el código del producto.
     * @return El código del producto.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el nombre del producto.
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el precio del producto.
     * @return El precio del producto.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Retorna una representación en cadena del objeto Producto.
     * @return Una cadena que contiene el código, nombre y precio del producto.
     */
    @Override
    public String toString() {
        return codigo + " - " + nombre + " - $" + precio;
    }
}