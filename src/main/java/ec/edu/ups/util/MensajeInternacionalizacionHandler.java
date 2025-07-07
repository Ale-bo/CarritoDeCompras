    package ec.edu.ups.util;

    import java.util.Locale;
    import java.util.ResourceBundle;
    import java.util.MissingResourceException;

    public class MensajeInternacionalizacionHandler {

        private ResourceBundle bundle;
        private Locale locale;

        // Constructor
        public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
            this.locale = new Locale(lenguaje, pais);
            // Cambiar "mensajes" a tu nombre base de archivos de propiedades
            this.bundle = ResourceBundle.getBundle("mensajes", locale);
        }

        // Método para obtener la clave desde el archivo de propiedades
        public String get(String key) {
            try {
                return bundle.getString(key); // Intentar obtener la clave del archivo de recursos
            } catch (MissingResourceException e) {
                // Si no se encuentra la clave, mostrar un mensaje en consola y devolver la clave
                System.err.println("Error: La clave '" + key + "' no fue encontrada en el archivo de propiedades.");
                return key; // Retorna la clave si no se encuentra en el archivo
            }
        }

        // Método para cambiar el idioma en tiempo de ejecución
        public void setLenguaje(String lenguaje, String pais) {
            this.locale = new Locale(lenguaje, pais);
            this.bundle = ResourceBundle.getBundle("mensajes", locale);

        }

        // Método para obtener el Locale
        public Locale getLocale() {
            return locale;
        }
    }
