package com.taskpro.servlet;

import com.taskpro.dao.MissionDAO;
import com.taskpro.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

@WebServlet("/admin/questions")
public class ViewQuestionsServlet extends HttpServlet {
    private final MissionDAO dao=new MissionDAO();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        try { req.setAttribute("missions",dao.list(null,null)); }
        catch(SQLException ex) {
            getServletContext().log("List question error",ex);
            req.setAttribute("missions",Collections.emptyList());
            req.setAttribute("error","Unable to load questions from MySQL.");
        }
        WebUtil.forward(req,resp,"/WEB-INF/views/view-questions.jsp");
    }
}
