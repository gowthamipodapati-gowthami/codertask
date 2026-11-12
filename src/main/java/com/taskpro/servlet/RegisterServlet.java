package com.taskpro.servlet;

import com.taskpro.dao.UserDAO;
import com.taskpro.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO dao = new UserDAO();
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebUtil.forward(req,resp,"/register.jsp");
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (!CsrfUtil.valid(req)) { WebUtil.message(req,"Form expired. Refresh the page and try again."); show(req,resp); return; }
        String name = ValidationUtil.trim(req.getParameter("username"));
        String email = ValidationUtil.trim(req.getParameter("email")).toLowerCase(java.util.Locale.ROOT);
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirmPassword");
        req.setAttribute("oldUsername",name); req.setAttribute("oldEmail",email);
        if (!ValidationUtil.username(name)) WebUtil.message(req,"Username must contain 3–30 letters, digits, or underscores.");
        else if (!ValidationUtil.email(email)) WebUtil.message(req,"Enter a valid email address.");
        else if (!ValidationUtil.password(password)) WebUtil.message(req,"Password must be 8–128 characters.");
        else if (!password.equals(confirm)) WebUtil.message(req,"Passwords do not match.");
        else {
            try {
                dao.register(name,email,PasswordUtil.hash(password));
                resp.sendRedirect(req.getContextPath()+"/login?registered=1");
                return;
            } catch (SQLIntegrityConstraintViolationException ex) {
                WebUtil.message(req,"Username or email is already registered.");
            } catch (SQLException ex) {
                getServletContext().log("Registration database error",ex);
                WebUtil.message(req,"Database unavailable. Check MySQL and db.properties.");
            }
        }
        show(req,resp);
    }
    private void show(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        WebUtil.forward(req,resp,"/register.jsp");
    }
}
