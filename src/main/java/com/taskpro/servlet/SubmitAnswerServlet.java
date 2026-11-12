package com.taskpro.servlet;

import com.taskpro.dao.SubmissionDAO;
import com.taskpro.model.SubmissionResult;
import com.taskpro.util.CsrfUtil;
import com.taskpro.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/submit-answer")
public class SubmitAnswerServlet extends HttpServlet {
    private final SubmissionDAO dao=new SubmissionDAO();
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        req.setCharacterEncoding("UTF-8");
        if(!CsrfUtil.valid(req)) { error(req,resp,"Form expired. Refresh and retry."); return; }
        String choice=ValidationUtil.trim(req.getParameter("answer"));
        long missionId;
        try { missionId=Long.parseLong(req.getParameter("missionId")); }
        catch(Exception ex) { error(req,resp,"Invalid mission ID."); return; }
        if(missionId<=0 || !ValidationUtil.answer(choice)) { error(req,resp,"Choose one of A, B, C or D."); return; }
        try {
            long userId=(Long)req.getSession(false).getAttribute("studentId");
            SubmissionResult result=dao.submit(userId,missionId,choice);
            req.getSession().setAttribute("lastResult",result);
            resp.sendRedirect(req.getContextPath()+"/mission-result");
        } catch(SQLException ex) {
            getServletContext().log("Answer submission database error",ex);
            error(req,resp,"Unable to save answer. Check the database and try again.");
        }
    }
    private void error(HttpServletRequest req,HttpServletResponse resp,String message) throws IOException {
        req.getSession().setAttribute("flashError",message);
        resp.sendRedirect(req.getContextPath()+"/missions");
    }
}
