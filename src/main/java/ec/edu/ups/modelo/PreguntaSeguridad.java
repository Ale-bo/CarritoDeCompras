package ec.edu.ups.modelo;

public class PreguntaSeguridad {
    private int preguntaId;
    private String respuesta;

    public PreguntaSeguridad(int preguntaId, String respuesta) {
        this.preguntaId = preguntaId;
        this.respuesta = respuesta;
    }

    public int getPreguntaId() {
        return preguntaId;
    }

    public String getRespuesta() {
        return respuesta;
    }
}