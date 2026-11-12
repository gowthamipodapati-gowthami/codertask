package com.taskpro.servlet;

import com.taskpro.dao.UserDAO;
import com.taskpro.model.User;
import com.taskpro.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private final UserDAO dao=new UserDAO();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        long id=(Long)req.getSession(false).getAttribute("studentId");
        try {
            User user=dao.findDashboard(id);
            if(user==null) {
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath()+"/login");return;
            }
            req.setAttribute("student",user);
        } catch(SQLException ex) {
            getServletContext().log("Dashboard database error",ex);
            req.setAttribute("error","Unable to load your progress. Check database connection.");
        }
        WebUtil.forward(req,resp,"/WEB-INF/views/dashboard.jsp");
    }
}
