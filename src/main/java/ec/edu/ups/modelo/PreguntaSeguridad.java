package ec.edu.ups.modelo;

import java.io.Serializable; // Importar Serializable

/**
 * Clase que representa una Pregunta de Seguridad asociada a un Usuario.
 * Contiene el ID de la pregunta y la respuesta del usuario.
 * Implementa {@code Serializable} para permitir el almacenamiento persistente en archivos binarios.
 *
 * @author Ivanna Alexandra Nievecela Pérez
 * @version 1.0
 */
public class PreguntaSeguridad implements Serializable {
    private static final long serialVersionUID = 1L; // Recomendado para Serializable

    private int preguntaId; // Identificador único de la pregunta
    private String respuesta; // Respuesta del usuario a la pregunta

    /**
     * Constructor para crear una nueva instancia de PreguntaSeguridad.
     *
     * @param preguntaId El ID numérico de la pregunta (ej. 1 para "¿Cómo se llamaba tu primera mascota?").
     * @param respuesta La respuesta del usuario a la pregunta.
     */
    public PreguntaSeguridad(int preguntaId, String respuesta) {
        this.preguntaId = preguntaId;
        this.respuesta = respuesta;
    }

    // Getters con Javadoc
    /**
     * Obtiene el ID de la pregunta de seguridad.
     * @return El ID de la pregunta.
     */
    public int getPreguntaId() {
        return preguntaId;
    }

    /**
     * Obtiene la respuesta del usuario a la pregunta de seguridad.
     * @return La respuesta del usuario.
     */
    public String getRespuesta() {
        return respuesta;
    }
}