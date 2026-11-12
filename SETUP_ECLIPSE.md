# Step-by-step Eclipse setup (Java 21)

## 1. Install / check JDK 21

- Install Java **JDK 21**, and open Command Prompt:
  ```bat
  java -version
  javac -version
  ```
- Both should report **21**.
- Open **Eclipse IDE for Enterprise Java and Web Developers**.
- In Eclipse: `Window > Preferences > Java > Installed JREs > Add... > Standard VM`.
- Choose your **JDK 21 directory**, mark it as the default.

## 2. Download and configure Tomcat 10.1

- Install/extract **Apache Tomcat 10.1.x**.
- Open Eclipse `Window > Preferences > Server > Runtime Environments > Add...`.
- Choose `Apache > Apache Tomcat v10.1`, select the Tomcat installation directory, and assign **JDK 21**.
- In the `Servers` tab, create a new Tomcat v10.1 Server if necessary.
- **Do not use Tomcat 9**, because this project uses `jakarta.servlet.*`.

## 3. Set up the MySQL database

- Install and start **MySQL Server**. MySQL Workbench is helpful.
- Open a MySQL Workbench SQL tab as a user who can create a database.
- Open and run `sql/01_schema.sql` completely.
- Then open and run `sql/02_seed_data.sql` completely.
- Optional: use `sql/03_useful_queries.sql` to inspect the data.
- Database name is **codequest_db** as specified by your PDF (project name is still taskpro).
- Demo administrator: username **admin**, password **Admin@12345**; local demonstration only.

## 4. Configure JDBC

- Edit `src/main/resources/db.properties`:
  ```properties
  db.url=jdbc:mysql://localhost:3306/codequest_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
  db.user=root
  db.password=YOUR_LOCAL_MYSQL_PASSWORD
  ```
- If the MySQL root account has no password locally, leave `db.password=` empty.
- MySQL 3306 is the default port; change it if your server uses a different port.
- For a non-local deployment, configure an app-specific MySQL account and TLS.
- Alternatively, environment variables `TASKPRO_DB_URL`, `TASKPRO_DB_USER`, and `TASKPRO_DB_PASSWORD` override these values.

## 5. Import the extracted ZIP folder into Eclipse

- Extract `taskpro.zip` to a folder such as `C:\\Projects\\taskpro`.
- In Eclipse: `File > Import > Maven > Existing Maven Projects > Next`.
- Choose the extracted `taskpro` folder with `pom.xml`.
- Select `pom.xml` and click `Finish`.
- If it shows dependency errors, right-click the project > `Maven > Update Project...` > tick `Force Update of Snapshots/Releases` > `OK`. Maven needs internet on the first build.
- Verify `Project > Properties > Java Compiler` is set to `21`.
- Verify `Project > Properties > Targeted Runtimes` has `Apache Tomcat v10.1` checked.
- If `Run on Server` is missing, ensure you installed the **Enterprise Java** edition of Eclipse and Maven Web Tools (`m2e-wtp`).

## 6. Run the project

- Start MySQL Server.
- In Eclipse, right-click `taskpro` > `Run As > Run on Server`.
- Choose `Tomcat v10.1 Server at localhost` > `Finish`.
- Eclipse deploys the application, and Tomcat usually starts on port 8080.
- Open: **http://localhost:8080/taskpro/**
- If the URL is different, check the server HTTP port and the deployed project context root.

## 7. First test

1. Open `http://localhost:8080/taskpro/` and click `Register`.
2. Register a student. Confirm new account has 0 XP and Level 1.
3. Login as student, then open Dashboard and Missions.
4. Answer one mission correctly and check XP and level; you cannot answer that mission twice.
5. Logout. Go to Admin Login and sign in as `admin` / `Admin@12345`.
6. Create a Java/SQL/DSA question through the admin form.
7. View Questions, then logout; re-login as student and solve the new question.
8. Open Leaderboard; verify ordering and counts are updated.

## Troubleshooting

| Symptom | Fix |
|---|---|
| `The import jakarta.servlet cannot be resolved` | Use Tomcat 10.1 and update Maven project; Maven should resolve `jakarta.servlet-api:6.0.0`. |
| `No suitable driver` or `ClassNotFoundException com.mysql.cj.jdbc.Driver` | Update Maven dependencies, check WAR contains MySQL Connector/J. |
| `Access denied for user 'root'` | Correct `db.user` and `db.password` in `db.properties`; restart/redeploy. |
| `Unknown database 'codequest_db'` | Run `sql/01_schema.sql` first. |
| `Connection refused` | Start MySQL and check port 3306. |
| `/taskpro` returns HTTP 404 | Check that Tomcat deployed the WAR and the context root is `taskpro`. |
| Admin login fails | Run `sql/02_seed_data.sql` and enter the exact demo username/password. |
| `Port 8080 already in use` | Change the Tomcat HTTP port in Eclipse `Servers` view. |
| JSP compile error or `javax.servlet` conflict | Use Tomcat **10.1**, not Tomcat 9; delete any old `javax.servlet` JARs. |
| MySQL connection works but missions are empty | Run `sql/02_seed_data.sql` or create missions from the admin dashboard. |

## Optional command-line build

With JDK 21 and Maven 3.9+ installed:
```bat
cd C:\\Projects\\taskpro
mvn clean test package
```
The resulting deployable file is `target/taskpro.war`. Copy it into Tomcat's `webapps` folder if not deploying from Eclipse.

## 8. Change the demo admin password (recommended)

- After `mvn package`, open a **Windows Command Prompt** in your `taskpro` project folder and run `java -cp target/classes com.taskpro.util.PasswordHashTool`. A normal terminal is required because Eclipse’s internal console may not support hidden password input.
- Enter a new password when prompted. The tool prints a prepared MySQL `UPDATE admins ...` statement with a new salted PBKDF2 hash.
- Execute that statement in MySQL Workbench to replace the local demo admin credential. Do not put a plaintext admin password in the SQL file.
