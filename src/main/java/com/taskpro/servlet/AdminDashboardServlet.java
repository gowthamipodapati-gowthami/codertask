package com.taskpro.servlet;

import com.taskpro.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        req.setAttribute("appName",getServletContext().getInitParameter("appName")); // ServletContext demonstration
        WebUtil.forward(req,resp,"/WEB-INF/views/admin-dashboard.jsp");
    }
}
