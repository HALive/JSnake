/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    public static byte[] getSHA256HashBytes(byte[] data) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return digest.digest(data);
    }

    public static String getSHA256Hash(byte[] data) {
        return convertToString(getSHA256HashBytes(data));
    }

    public static String convertToString(byte[] data) {
        return String.format("%064x", new BigInteger(1, data));
    }
}
