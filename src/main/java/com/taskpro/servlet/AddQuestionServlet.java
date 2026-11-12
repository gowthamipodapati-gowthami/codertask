package com.taskpro.servlet;

import com.taskpro.dao.MissionDAO;
import com.taskpro.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value="/admin/add-question",initParams={@WebInitParam(name="maxQuestionLength",value="1000")})
public class AddQuestionServlet extends HttpServlet {
    private final MissionDAO dao=new MissionDAO();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        if("1".equals(req.getParameter("saved"))) req.setAttribute("success","Question created successfully!");
        show(req,resp);
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        req.setCharacterEncoding("UTF-8");
        if(!CsrfUtil.valid(req)) { WebUtil.message(req,"Form expired. Refresh and retry."); show(req,resp); return; }
        String title=ValidationUtil.trim(req.getParameter("title"));
        String description=ValidationUtil.trim(req.getParameter("description"));
        String category=ValidationUtil.trim(req.getParameter("category"));
        String difficulty=ValidationUtil.trim(req.getParameter("difficulty"));
        String a=ValidationUtil.trim(req.getParameter("optionA"));
        String b=ValidationUtil.trim(req.getParameter("optionB"));
        String c=ValidationUtil.trim(req.getParameter("optionC"));
        String d=ValidationUtil.trim(req.getParameter("optionD"));
        String correct=ValidationUtil.trim(req.getParameter("correctAnswer"));
        String reward=ValidationUtil.trim(req.getParameter("xpReward"));
        int maxLen=Integer.parseInt(getServletConfig().getInitParameter("maxQuestionLength")); // ServletConfig example
        int xp=0;
        try { xp=Integer.parseInt(reward); } catch(NumberFormatException ignored) { }
        if(title.isEmpty() || title.length()>160) WebUtil.message(req,"Title must be 1–160 characters.");
        else if(description.isEmpty() || description.length()>maxLen) WebUtil.message(req,"Description must be 1–"+maxLen+" characters.");
        else if(!ValidationUtil.category(category) || !ValidationUtil.difficulty(difficulty)) WebUtil.message(req,"Choose a valid category and difficulty.");
        else if(a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty()
            || a.length()>500 || b.length()>500 || c.length()>500 || d.length()>500) WebUtil.message(req,"Fill all four answer choices (up to 500 characters each).");
        else if(!ValidationUtil.answer(correct)) WebUtil.message(req,"Choose A, B, C, or D for the correct answer.");
        else if(xp<1 || xp>500) WebUtil.message(req,"XP reward must be between 1 and 500.");
        else {
            try {
                dao.create(title,description,category,difficulty,a,b,c,d,correct,xp);
                resp.sendRedirect(req.getContextPath()+"/admin/add-question?saved=1"); return;
            } catch(SQLException ex) {
                getServletContext().log("Create question database error",ex);
                WebUtil.message(req,"Unable to save question. Check the database connection.");
            }
        }
        show(req,resp);
    }
    private void show(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        WebUtil.forward(req,resp,"/WEB-INF/views/add-question.jsp");
    }
}
