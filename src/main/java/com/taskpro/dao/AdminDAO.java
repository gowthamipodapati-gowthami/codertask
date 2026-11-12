package com.taskpro.dao;

import com.taskpro.util.DBConnection;
import java.sql.*;

public class AdminDAO {
    public String[] findCredential(String identity) throws SQLException {
        String sql = "SELECT id,username,password_hash FROM admins WHERE username=? LIMIT 1";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, identity);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? new String[] {rs.getString("id"),rs.getString("username"),rs.getString("password_hash")} : null;
            }
        }
    }
}
