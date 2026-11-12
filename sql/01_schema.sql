-- TASKPRO database schema (assignment requires database name codequest_db).
-- Compatible with MySQL 8.x and newer. Run this file FIRST.
CREATE DATABASE IF NOT EXISTS codequest_db
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE codequest_db;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(190) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    xp INT UNSIGNED NOT NULL DEFAULT 0,
    level INT UNSIGNED NOT NULL DEFAULT 1,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_users_username (username),
    UNIQUE KEY uk_users_email (email)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS admins (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_admins_username (username)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS missions (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(160) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(10) NOT NULL,
    difficulty VARCHAR(10) NOT NULL,
    option_a VARCHAR(500) NOT NULL,
    option_b VARCHAR(500) NOT NULL,
    option_c VARCHAR(500) NOT NULL,
    option_d VARCHAR(500) NOT NULL,
    correct_answer CHAR(1) NOT NULL,
    xp_reward SMALLINT UNSIGNED NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX ix_missions_category (category),
    CONSTRAINT chk_missions_category CHECK (category IN ('Java','SQL','DSA')),
    CONSTRAINT chk_missions_difficulty CHECK (difficulty IN ('Easy','Medium','Hard')),
    CONSTRAINT chk_missions_answer CHECK (correct_answer IN ('A','B','C','D')),
    CONSTRAINT chk_missions_xp CHECK (xp_reward BETWEEN 1 AND 500)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS mission_submissions (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    mission_id BIGINT UNSIGNED NOT NULL,
    selected_answer CHAR(1) NOT NULL,
    is_correct BOOLEAN NOT NULL,
    xp_awarded SMALLINT UNSIGNED NOT NULL DEFAULT 0,
    submitted_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_one_attempt (user_id, mission_id),
    INDEX ix_submissions_mission_id (mission_id),
    CONSTRAINT fk_submissions_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_submissions_mission FOREIGN KEY (mission_id) REFERENCES missions(id) ON DELETE RESTRICT,
    CONSTRAINT chk_submissions_answer CHECK (selected_answer IN ('A','B','C','D'))
) ENGINE=InnoDB;
