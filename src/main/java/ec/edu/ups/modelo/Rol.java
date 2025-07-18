package ec.edu.ups.modelo;

import java.io.Serializable; // Importar Serializable

/**
 * Enumeración que define los roles de usuario dentro del sistema.
 * Los roles posibles son ADMINISTRADOR y USUARIO.
 * Implementa {@code Serializable} para permitir el almacenamiento persistente en archivos binarios.
 *
 * @author Ivanna Alexandra Nievecela Pérez
 * @version 1.0
 */
public enum Rol implements Serializable {
    ADMINISTRADOR, // Rol con permisos administrativos
    USUARIO        // Rol con permisos de usuario estándar
}