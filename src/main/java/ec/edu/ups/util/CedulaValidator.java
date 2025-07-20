package ec.edu.ups.util;

public class CedulaValidator {

    public static boolean isValidCedula(String cedula) {

        if (cedula == null || cedula.length() != 10) {
            return false;
        }

        if (!cedula.matches("\\d{10}")) {
            return false;
        }

        int provincia = Integer.parseInt(cedula.substring(0, 2));

        if (provincia < 1 || provincia > 24) {
            return false;
        }

        int digitoVerificador = Integer.parseInt(cedula.substring(9, 10));

        int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int valor = Integer.parseInt(cedula.substring(i, i + 1)) * coeficientes[i];
            if (valor >= 10) {
                valor -= 9;
            }
            suma += valor;
        }

        int residuo = suma % 10;
        int digitoCalculado = (residuo == 0) ? 0 : (10 - residuo);

        // Compara el dígito calculado con el dígito verificador.
        return digitoCalculado == digitoVerificador;
    }
}
