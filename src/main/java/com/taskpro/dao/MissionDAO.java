package com.taskpro.dao;

import com.taskpro.model.Mission;
import com.taskpro.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MissionDAO {
    public void create(String title, String description, String category, String difficulty,
                       String a, String b, String c, String d, String correct, int xp) throws SQLException {
        String sql = "INSERT INTO missions (title,description,category,difficulty,option_a,option_b,option_c,option_d,correct_answer,xp_reward) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,title); ps.setString(2,description); ps.setString(3,category); ps.setString(4,difficulty);
            ps.setString(5,a); ps.setString(6,b); ps.setString(7,c); ps.setString(8,d);
            ps.setString(9,correct); ps.setInt(10,xp);
            ps.executeUpdate();
        }
    }

    public List<Mission> list(String category, Long studentId) throws SQLException {
        boolean filtered = category != null && !category.isEmpty() && !"All".equals(category);
        String sql = "SELECT m.id,m.title,m.description,m.category,m.difficulty,"
            + "m.option_a,m.option_b,m.option_c,m.option_d,m.xp_reward,"
            + (studentId == null ? "0" : "CASE WHEN s.id IS NULL THEN 0 ELSE 1 END") + " attempted "
            + "FROM missions m "
            + (studentId == null ? "" : "LEFT JOIN mission_submissions s ON s.mission_id=m.id AND s.user_id=? ")
            + (filtered ? "WHERE m.category=? " : "") + "ORDER BY m.id DESC";
        List<Mission> result = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int n=1;
            if (studentId != null) ps.setLong(n++, studentId);
            if (filtered) ps.setString(n, category);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) result.add(new Mission(rs.getLong("id"),rs.getString("title"),rs.getString("description"),
                    rs.getString("category"),rs.getString("difficulty"),rs.getString("option_a"),rs.getString("option_b"),
                    rs.getString("option_c"),rs.getString("option_d"),rs.getInt("xp_reward"),rs.getBoolean("attempted")));
            }
        }
        return result;
    }
}
