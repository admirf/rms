package utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by admir on 10.12.2016..
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
