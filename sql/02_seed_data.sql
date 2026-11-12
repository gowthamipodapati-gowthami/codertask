-- DEMO DATA ONLY: Change the seeded administrator password before any real deployment.
-- Default demo admin username: admin
-- Default demo admin password: Admin@12345
-- The password is stored as a salted PBKDF2-HMAC-SHA256 hash (not plaintext).
USE codequest_db;

INSERT IGNORE INTO admins (username, password_hash)
VALUES ('admin', 'pbkdf2:210000:ME/HrwBN2iA3Qw29dEdeCg==:zROky9Y99mXCOKpxDtzPGVOrS9FXdjk0KPQc+RYx7xU=');

-- Twelve example multiple-choice programming missions (4 per category)
-- New student accounts still start at 0 XP and Level 1.
INSERT INTO missions (title, description, category, difficulty, option_a, option_b, option_c, option_d, correct_answer, xp_reward) VALUES
('Java primitive type', 'Which Java primitive data type stores true or false?', 'Java', 'Easy', 'String', 'boolean', 'Boolean', 'char', 'B', 20),
('Java entry point', 'Which method is commonly used as the entry point for a standard Java application?', 'Java', 'Easy', 'start()', 'run()', 'main(String[] args)', 'init()', 'C', 20),
('Java inheritance', 'Which keyword is used when a Java class inherits from another class?', 'Java', 'Medium', 'implements', 'extends', 'inherits', 'superclass', 'B', 35),
('Java collection duplicates', 'Which Java collection interface does not permit duplicate elements?', 'Java', 'Medium', 'List', 'Queue', 'Set', 'Deque', 'C', 35),
('SQL filter rows', 'Which SQL clause filters individual rows before grouping?', 'SQL', 'Easy', 'WHERE', 'ORDER BY', 'HAVING', 'GROUP BY', 'A', 20),
('SQL count rows', 'Which SQL aggregate function returns the number of rows?', 'SQL', 'Easy', 'TOTAL()', 'COUNT()', 'SUMROWS()', 'SIZE()', 'B', 20),
('SQL join matches', 'Which JOIN returns rows when the join condition matches in both tables?', 'SQL', 'Medium', 'CROSS JOIN', 'FULL JOIN', 'INNER JOIN', 'RIGHT JOIN', 'C', 35),
('SQL unique values', 'Which SQL keyword removes duplicate rows from the query result?', 'SQL', 'Medium', 'DISTINCT', 'UNIQUE_ONLY', 'REMOVE', 'ONLY', 'A', 35),
('DSA queue order', 'Which order is followed by a standard queue?', 'DSA', 'Easy', 'LIFO', 'FIFO', 'Random', 'Sorted', 'B', 20),
('DSA binary search', 'Binary search on a sorted array has which time complexity in the worst case?', 'DSA', 'Medium', 'O(n)', 'O(n log n)', 'O(log n)', 'O(1)', 'C', 35),
('DSA stack operation', 'Which operation removes the top item from a stack?', 'DSA', 'Easy', 'pop', 'enqueue', 'peek', 'append', 'A', 20),
('DSA merge sort', 'What is the worst-case time complexity of merge sort?', 'DSA', 'Hard', 'O(n²)', 'O(n log n)', 'O(log n)', 'O(1)', 'B', 50);
