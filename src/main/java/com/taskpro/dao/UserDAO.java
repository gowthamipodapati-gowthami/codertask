package com.taskpro.dao;

import com.taskpro.model.User;
import com.taskpro.util.DBConnection;
import java.sql.*;

public class UserDAO {
    public boolean register(String username, String email, String hash) throws SQLException {
        String sql = "INSERT INTO users (username,email,password_hash,xp,level) VALUES (?,?,?,0,1)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username); ps.setString(2, email); ps.setString(3, hash);
            return ps.executeUpdate() == 1;
        }
    }

    public String[] findCredential(String identity) throws SQLException {
        String sql = "SELECT id,username,password_hash FROM users WHERE username=? OR email=? LIMIT 1";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, identity); ps.setString(2, identity);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? new String[] {rs.getString("id"), rs.getString("username"), rs.getString("password_hash")} : null;
            }
        }
    }

    public User findDashboard(long userId) throws SQLException {
        String sql = "SELECT u.id,u.username,u.email,u.xp,u.level,"
            + "(SELECT COUNT(*) FROM mission_submissions s WHERE s.user_id=u.id AND s.is_correct=1) completed "
            + "FROM users u WHERE u.id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? new User(rs.getLong("id"),rs.getString("username"),rs.getString("email"),
                    rs.getInt("xp"),rs.getInt("level"),rs.getInt("completed")) : null;
            }
        }
    }
}
