package com.taskpro.dao;

import com.taskpro.model.LeaderboardEntry;
import com.taskpro.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardDAO {
    public List<LeaderboardEntry> all() throws SQLException {
        String sql = "SELECT u.id,u.username,u.xp,u.level,COUNT(CASE WHEN s.is_correct=1 THEN 1 END) completed "
            + "FROM users u LEFT JOIN mission_submissions s ON s.user_id=u.id "
            + "GROUP BY u.id,u.username,u.xp,u.level ORDER BY u.xp DESC, completed DESC, u.username ASC";
        List<LeaderboardEntry> entries = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while(rs.next()) entries.add(new LeaderboardEntry(rs.getLong("id"),rs.getString("username"),
                rs.getInt("xp"),rs.getInt("level"),rs.getInt("completed")));
        }
        return entries;
    }
}
