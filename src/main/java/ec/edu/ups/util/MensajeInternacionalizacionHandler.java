package ec.edu.ups.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MensajeInternacionalizacionHandler {

    private ResourceBundle bundle;
    private Locale locale;

    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        setLenguaje(lenguaje, pais);
    }

    public String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            System.err.println("Error: La clave '" + key + "' no fue encontrada en el archivo de propiedades.");
            return "!!" + key + "!!"; // Retorna la clave para identificarla fácilmente en la UI
        }
    }

    public void setLenguaje(String lenguaje, String pais) {
        this.locale = new Locale.Builder()
                .setLanguage(lenguaje)
                .setRegion(pais)
                .build();
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    public Locale getLocale() {
        return locale;
    }
}
