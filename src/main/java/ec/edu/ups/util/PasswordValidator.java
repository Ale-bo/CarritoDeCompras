package ec.edu.ups.util;

public class PasswordValidator {

    /**
     * Valida si una contraseña cumple con los requisitos de seguridad:
     * - Mínimo 6 caracteres.
     * - Al menos una letra mayúscula.
     * - Al menos una letra minúscula.
     * - Al menos uno de los caracteres especiales: '@', '_', '-'.
     *
     * @param password La contraseña a validar.
     * @return true si la contraseña es válida, false en caso contrario.
     */
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }

        // 1. Mínimo 6 caracteres
        if (password.length() < 6) {
            return false;
        }

        // 2. Al menos una letra mayúscula
        boolean hasUppercase = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
                break;
            }
        }
        if (!hasUppercase) {
            return false;
        }

        // 3. Al menos una letra minúscula
        boolean hasLowercase = false;
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLowercase = true;
                break;
            }
        }
        if (!hasLowercase) {
            return false;
        }

        // 4. Al menos uno de los caracteres especiales: '@', '_', '-'
        boolean hasSpecialChar = false;
        for (char c : password.toCharArray()) {
            if (c == '@' || c == '_' || c == '-') {
                hasSpecialChar = true;
                break;
            }
        }
        if (!hasSpecialChar) {
            return false;
        }

        return true; // La contraseña cumple con todos los requisitos
    }
}
