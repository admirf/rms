package utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class used to has a string into md5
 */
public class Hasher {
    public static String toMD5(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
