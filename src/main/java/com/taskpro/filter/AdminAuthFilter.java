package com.taskpro.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns={"/admin/dashboard","/admin/add-question","/admin/questions","/admin/logout"})
public class AdminAuthFilter implements Filter {
    @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if (session == null || !(session.getAttribute("adminId") instanceof Long)) {
            resp.sendRedirect(req.getContextPath() + "/admin/login?loginRequired=1");
            return;
        }
        resp.setHeader("Cache-Control","no-store");
        chain.doFilter(request,response);
    }
}
