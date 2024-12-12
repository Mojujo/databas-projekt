package utility;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtil {

    // Hash password input with BCrypt integration
    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    // Verifies password using default BCrypt algorithm // Unique to all stored hash passwords
    public static boolean checkPassword(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }
}
