package com.taskpro.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/** PBKDF2 password hashing: pbkdf2:iterations:salt-base64:hash-base64. */
public final class PasswordUtil {
    private static final int ITERATIONS = 210_000;
    private static final int KEY_BITS = 256;
    private static final SecureRandom RNG = new SecureRandom();
    private PasswordUtil() {}

    public static String hash(String password) {
        byte[] salt = new byte[16];
        RNG.nextBytes(salt);
        byte[] derived = derive(password.toCharArray(), salt, ITERATIONS);
        return "pbkdf2:" + ITERATIONS + ":" + Base64.getEncoder().encodeToString(salt)
               + ":" + Base64.getEncoder().encodeToString(derived);
    }

    public static boolean verify(String password, String stored) {
        if (password == null || stored == null) return false;
        try {
            String[] pieces = stored.split(":", -1);
            if (pieces.length != 4 || !"pbkdf2".equals(pieces[0])) return false;
            int iterations = Integer.parseInt(pieces[1]);
            if (iterations < 100_000 || iterations > 1_000_000) return false;
            byte[] salt = Base64.getDecoder().decode(pieces[2]);
            byte[] expected = Base64.getDecoder().decode(pieces[3]);
            if (salt.length < 16 || expected.length != KEY_BITS / 8) return false;
            return MessageDigest.isEqual(expected, derive(password.toCharArray(), salt, iterations));
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    private static byte[] derive(char[] password, byte[] salt, int iterations) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, KEY_BITS);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception ex) {
            throw new IllegalStateException("Password hashing unavailable", ex);
        } finally {
            spec.clearPassword();
        }
    }
}
