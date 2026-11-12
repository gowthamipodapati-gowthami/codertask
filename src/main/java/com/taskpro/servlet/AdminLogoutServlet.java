package com.taskpro.servlet;

import com.taskpro.util.CsrfUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/logout")
public class AdminLogoutServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!CsrfUtil.valid(req)) { resp.sendError(403,"Invalid form token"); return; }
        HttpSession session=req.getSession(false);
        if(session!=null) session.invalidate();
        resp.sendRedirect(req.getContextPath()+"/index.html");
    }
}
