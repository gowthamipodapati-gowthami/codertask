package com.taskpro.servlet;

import com.taskpro.dao.LeaderboardDAO;
import com.taskpro.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

@WebServlet("/leaderboard")
public class LeaderboardServlet extends HttpServlet {
    private final LeaderboardDAO dao=new LeaderboardDAO();
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException {
        try { req.setAttribute("players",dao.all()); }
        catch(SQLException ex) {
            getServletContext().log("Leaderboard database error",ex);
            req.setAttribute("players",Collections.emptyList());
            req.setAttribute("error","Unable to load the leaderboard. Check MySQL connection.");
        }
        WebUtil.forward(req,resp,"/WEB-INF/views/leaderboard.jsp");
    }
}
