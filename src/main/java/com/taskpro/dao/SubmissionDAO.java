package com.taskpro.dao;

import com.taskpro.model.SubmissionResult;
import com.taskpro.util.DBConnection;
import com.taskpro.util.ProgressUtil;
import java.sql.*;

/** Serializes each user's submissions and atomically updates submission/XP/level. */
public class SubmissionDAO {
    public SubmissionResult submit(long userId, long missionId, String selected) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int oldXp;
                try (PreparedStatement lock = conn.prepareStatement("SELECT xp FROM users WHERE id=? FOR UPDATE")) {
                    lock.setLong(1,userId);
                    try (ResultSet rs = lock.executeQuery()) {
                        if (!rs.next()) throw new SQLException("Student not found");
                        oldXp = rs.getInt("xp");
                    }
                }
                try (PreparedStatement exists = conn.prepareStatement(
                    "SELECT id FROM mission_submissions WHERE user_id=? AND mission_id=?")) {
                    exists.setLong(1,userId); exists.setLong(2,missionId);
                    try (ResultSet rs = exists.executeQuery()) {
                        if (rs.next()) { conn.rollback(); return SubmissionResult.duplicateResult(); }
                    }
                }
                String correct;
                String title;
                int reward;
                try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT title,correct_answer,xp_reward FROM missions WHERE id=?")) {
                    ps.setLong(1,missionId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) throw new SQLException("Mission not found");
                        correct = rs.getString("correct_answer");
                        title = rs.getString("title");
                        reward = rs.getInt("xp_reward");
                    }
                }
                boolean isCorrect = selected.equals(correct);
                int awarded = isCorrect ? reward : 0;
                int newXp = oldXp + awarded;
                int level = ProgressUtil.levelFor(newXp);
                try (PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO mission_submissions (user_id,mission_id,selected_answer,is_correct,xp_awarded) VALUES (?,?,?,?,?)")) {
                    insert.setLong(1,userId); insert.setLong(2,missionId); insert.setString(3,selected);
                    insert.setBoolean(4,isCorrect); insert.setInt(5,awarded); insert.executeUpdate();
                }
                if (isCorrect) {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE users SET xp=?,level=? WHERE id=?")) {
                        ps.setInt(1,newXp); ps.setInt(2,level); ps.setLong(3,userId); ps.executeUpdate();
                    }
                }
                conn.commit();
                return new SubmissionResult(isCorrect,false,title,selected,correct,awarded,newXp,level,
                    isCorrect ? "Correct! XP awarded." : "Incorrect answer. No XP awarded. This mission is now attempted.");
            } catch (SQLException | RuntimeException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}
