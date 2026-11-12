package com.taskpro.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/** Simple synchronizer-token CSRF defense for form POST requests. */
public final class CsrfUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private CsrfUtil() {}
    public static String token(HttpServletRequest req) {
        HttpSession session = req.getSession();
        synchronized (session) {
            String value = (String) session.getAttribute("csrfToken");
            if (value == null) {
                byte[] bytes = new byte[32];
                RANDOM.nextBytes(bytes);
                value = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
                session.setAttribute("csrfToken", value);
            }
            return value;
        }
    }
    public static boolean valid(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) return false;
        String expected = (String) session.getAttribute("csrfToken");
        String supplied = req.getParameter("csrf");
        return expected != null && supplied != null && MessageDigest.isEqual(
            expected.getBytes(StandardCharsets.UTF_8), supplied.getBytes(StandardCharsets.UTF_8));
    }
}
