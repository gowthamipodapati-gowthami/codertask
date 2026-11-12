package com.taskpro.servlet;

import com.taskpro.model.SubmissionResult;
import com.taskpro.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/mission-result")
public class MissionResultServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        Object obj=req.getSession().getAttribute("lastResult");
        if(!(obj instanceof SubmissionResult)) { resp.sendRedirect(req.getContextPath()+"/missions"); return; }
        req.getSession().removeAttribute("lastResult");
        req.setAttribute("result",obj);
        WebUtil.forward(req,resp,"/WEB-INF/views/mission-result.jsp");
    }
}
