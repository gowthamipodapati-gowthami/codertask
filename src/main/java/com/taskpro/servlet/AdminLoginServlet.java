package com.taskpro.servlet;

import com.taskpro.dao.AdminDAO;
import com.taskpro.util.*;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value="/admin/login",initParams={@WebInitParam(name="adminLoginMessage",value="Administrator access only")})
public class AdminLoginServlet extends HttpServlet {
    private final AdminDAO dao=new AdminDAO();
    private String adminMessage;
    @Override public void init(ServletConfig config) throws ServletException {
        super.init(config);
        adminMessage = config.getInitParameter("adminLoginMessage"); // ServletConfig demonstration
    }
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        show(req,resp);
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (!CsrfUtil.valid(req)) { WebUtil.message(req,"Form expired. Refresh and retry."); show(req,resp); return; }
        String name=ValidationUtil.trim(req.getParameter("username"));
        String password=req.getParameter("password");
        if (name.isEmpty() || password==null || password.isEmpty()) {
            WebUtil.message(req,"Enter administrator username and password."); show(req,resp); return;
        }
        try {
            String[] cred=dao.findCredential(name);
            if (cred!=null && PasswordUtil.verify(password,cred[2])) {
                WebUtil.startSession(req,"admin",Long.parseLong(cred[0]),cred[1]);
                resp.sendRedirect(req.getContextPath()+"/admin/dashboard"); return;
            }
            WebUtil.message(req,"Incorrect admin credentials.");
        } catch(SQLException ex) {
            getServletContext().log("Admin login database error",ex);
            WebUtil.message(req,"Database unavailable. Check MySQL settings.");
        }
        show(req,resp);
    }
    private void show(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        req.setAttribute("adminMessage",adminMessage);
        WebUtil.forward(req,resp,"/admin-login.jsp");
    }
}
