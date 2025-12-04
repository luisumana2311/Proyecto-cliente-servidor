package Util;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author landr
 */
public class BCryptUtil {

    public static String hashPassword(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String plain, String hashed) {
        if (plain == null || hashed == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plain, hashed);
        } catch (Exception ex) {
            return false;
        }
    }
}
