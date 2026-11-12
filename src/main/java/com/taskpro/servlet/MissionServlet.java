package com.taskpro.servlet;

import com.taskpro.dao.MissionDAO;
import com.taskpro.util.ValidationUtil;
import com.taskpro.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

@WebServlet("/missions")
public class MissionServlet extends HttpServlet {
    private final MissionDAO dao=new MissionDAO();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        String category=req.getParameter("category");
        if(category==null) {
            if(req.getCookies()!=null) for(Cookie cookie:req.getCookies()) {
                if("taskpro_category".equals(cookie.getName())) category=cookie.getValue();
            }
        }
        if(!ValidationUtil.category(category)) category="All";
        else if(req.getParameter("category")!=null) {
            Cookie pref=new Cookie("taskpro_category",category);
            pref.setHttpOnly(true); pref.setSecure(req.isSecure()); pref.setAttribute("SameSite","Lax");
            pref.setPath(req.getContextPath().isEmpty()?"/":req.getContextPath());
            pref.setMaxAge(7*24*60*60);
            resp.addCookie(pref); // benign preference cookie demonstration
        }
        req.setAttribute("category",category);
        Object flash=req.getSession().getAttribute("flashError");
        if(flash!=null) { req.setAttribute("error",flash); req.getSession().removeAttribute("flashError"); }
        try { req.setAttribute("missions",dao.list(category,(Long)req.getSession().getAttribute("studentId"))); }
        catch(SQLException ex) {
            getServletContext().log("Mission database error",ex);
            req.setAttribute("missions",Collections.emptyList());
            req.setAttribute("error","Unable to load missions. Check MySQL connection.");
        }
        WebUtil.forward(req,resp,"/WEB-INF/views/missions.jsp");
    }
}
