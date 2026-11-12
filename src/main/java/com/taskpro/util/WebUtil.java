package com.taskpro.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public final class WebUtil {
    private WebUtil() {}
    public static void forward(HttpServletRequest req, HttpServletResponse resp, String jsp) throws ServletException, IOException {
        req.getRequestDispatcher(jsp).forward(req, resp);
    }
    public static HttpSession startSession(HttpServletRequest req, String role, long id, String username) {
        HttpSession existing = req.getSession(false);
        if (existing != null) existing.invalidate();
        HttpSession session = req.getSession(true);
        session.setMaxInactiveInterval(30 * 60);
        session.setAttribute(role + "Id", id);
        session.setAttribute(role + "Name", username);
        return session;
    }
    public static void message(HttpServletRequest req, String message) { req.setAttribute("error", message); }
}
