package com.taskpro.servlet;

import com.taskpro.dao.UserDAO;
import com.taskpro.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO dao = new UserDAO();
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebUtil.forward(req,resp,"/login.jsp");
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (!CsrfUtil.valid(req)) { WebUtil.message(req,"Form expired. Refresh and retry."); show(req,resp); return; }
        String identity = ValidationUtil.trim(req.getParameter("identity"));
        String password = req.getParameter("password");
        req.setAttribute("oldIdentity",identity);
        if (identity.isEmpty() || password == null || password.isEmpty()) {
            WebUtil.message(req,"Enter your username/email and password."); show(req,resp); return;
        }
        try {
            String[] cred = dao.findCredential(identity);
            if (cred != null && PasswordUtil.verify(password,cred[2])) {
                WebUtil.startSession(req,"student",Long.parseLong(cred[0]),cred[1]);
                resp.sendRedirect(req.getContextPath()+"/dashboard");
                return;
            }
            WebUtil.message(req,"Invalid username/email or password.");
        } catch (SQLException ex) {
            getServletContext().log("Student login database error",ex);
            WebUtil.message(req,"Database unavailable. Check MySQL connection.");
        }
        show(req,resp);
    }
    private void show(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        WebUtil.forward(req,resp,"/login.jsp");
    }
}
