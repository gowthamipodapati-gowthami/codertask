# Requirements — taskpro (CodeQuest assignment)

## Recommended local software

1. **Java SE JDK 21** (not only the JRE); configure Eclipse compiler/JRE to version 21.
2. **Eclipse IDE for Enterprise Java and Web Developers**, with Maven integration and web tools.
3. **Apache Tomcat 10.1.x** runtime (Jakarta Servlet 6.0, Jakarta Pages/JSP 3.1).
4. **MySQL Server 8.0 or 8.4+**; MySQL Workbench is optional but convenient.
5. **Apache Maven 3.9+** (Eclipse commonly includes Maven integration).
6. Chrome, Edge, or Firefox browser; an internet connection **when Maven downloads dependencies**.

## Libraries handled by Maven

- `jakarta.servlet:jakarta.servlet-api:6.0.0` (Tomcat provides it).
- `com.mysql:mysql-connector-j:9.5.0` (JDBC driver packaged in WAR).
- JUnit Jupiter `5.11.4` (test scope only).

## Stack enforced by assignment

- HTML/CSS + JSP **scriptlets** (no JSTL and no JSP Expression Language).
- Java Servlets, JDBC `PreparedStatement`, MySQL.
- Student and Admin sessions, login/logout, cookies, ServletConfig and ServletContext.
- Passwords stored as salted PBKDF2-HMAC-SHA256 hashes.
- MySQL database name is **`codequest_db`** because that exact name is specified in the original assignment. Eclipse/Maven project name, WAR name, and URL context path are **`taskpro`**.

## Project behavior

- New students start with 0 XP and Level 1.
- Correct mission: +its configured XP reward. Incorrect mission: +0 XP.
- Every 100 total XP increases the level by 1: `level = floor(xp/100)+1`.
- **One answer attempt per student per mission**, including wrong answers.
- Admin can create and view stored missions; students can solve and track progress.
- The global leaderboard is public, and refreshes from the live DB on each load.

## Limitations

- This is a student learning project, not a hardened production identity platform.
- The included sample administrator password is for local demonstration **only**. Change it before sharing an accessible deployment.
- Tomcat/MySQL/JDK installations are external requirements, not bundled in this ZIP.
