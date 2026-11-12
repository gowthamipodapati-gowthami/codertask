-- Reference queries for your project demonstration.
-- These are read-only; use PreparedStatement for parameterized operations in Java.
USE codequest_db;

-- Show tables and row counts (run after 01 and 02).
SHOW TABLES;
SELECT COUNT(*) AS students FROM users;
SELECT COUNT(*) AS admins FROM admins;
SELECT COUNT(*) AS missions FROM missions;
SELECT COUNT(*) AS attempts FROM mission_submissions;

-- List registered students and progress.
SELECT id, username, email, xp, level, created_at FROM users ORDER BY id;

-- List mission titles and rewards (never send correct_answer to students).
SELECT id, title, category, difficulty, xp_reward FROM missions ORDER BY id;

-- Show attempts, feedback, and earned XP (for admin/demo inspection only).
SELECT s.id, u.username, m.title AS mission, s.selected_answer,
       m.correct_answer, s.is_correct, s.xp_awarded, s.submitted_at
FROM mission_submissions s
JOIN users u ON u.id = s.user_id
JOIN missions m ON m.id = s.mission_id
ORDER BY s.submitted_at DESC, s.id DESC;

-- Show global rankings based on XP, correct missions and username.
SELECT u.username, u.xp, u.level,
       COUNT(CASE WHEN s.is_correct=1 THEN 1 END) AS completed_missions
FROM users u LEFT JOIN mission_submissions s ON u.id=s.user_id
GROUP BY u.id, u.username, u.xp, u.level
ORDER BY u.xp DESC, completed_missions DESC, u.username ASC;

-- Personalized dashboard, use a bound student ID via PreparedStatement in Java:
-- SELECT u.username,u.xp,u.level, COUNT(CASE WHEN s.is_correct=1 THEN 1 END) completed
-- FROM users u LEFT JOIN mission_submissions s ON s.user_id=u.id
-- WHERE u.id=? GROUP BY u.id,u.username,u.xp,u.level;

-- Do not directly change XP or delete attempts during normal usage.
-- SubmissionDAO is responsible for the transaction and one-attempt constraint.
