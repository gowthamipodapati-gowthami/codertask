# taskpro — CodeQuest programming learning game

**Complete Java 21 Maven WAR project for Eclipse, based on the 12-module Student Project Assignment.**

### Start here

1. Read **[REQUIREMENTS.md](REQUIREMENTS.md)** for the software you need.
2. Follow **[SETUP_ECLIPSE.md](SETUP_ECLIPSE.md)** line by line.
3. Run SQL scripts in order: `sql/01_schema.sql`, then `sql/02_seed_data.sql`.
4. Edit the MySQL username/password in `src/main/resources/db.properties`.
5. Import `pom.xml` through Eclipse > Existing Maven Projects, deploy to Tomcat 10.1.
6. Open `http://localhost:8080/taskpro/`.

### Demo admin account

- Username: `admin`
- Password: `Admin@12345`
- **For local evaluation only; replace before exposure to others.** The seeded password is hashed in MySQL.

### Naming

- Eclipse project and deployable WAR: **taskpro**
- Browser URL: **/taskpro/**
- Product/assignment theme: **CodeQuest**
- Database: **codequest_db** (retained from the PDF assignment)

### Included

- 12-module application: HTML, CSS, JSP scriptlets, Java Servlets, JDBC DAOs, MySQL.
- Maven `pom.xml`, Java 21 compiler config, Tomcat 10.1-compatible `jakarta.servlet` imports.
- Student and admin login systems, secure session fixation protection, PBKDF2 password hashing.
- SQL schema, sample missions, demo admin login, inspection queries.
- UI styles, role filters, form validation, CSRF protection, cookie example.
- Transactional XP updates and one-attempt-per-mission database constraint.
- Module map, manual test checklist, setup and troubleshooting docs.

### Security note

This is an educational application: use unique database credentials, HTTPS, suitable rate limits, proper secret storage, and replace the demo admin password for any public deployment.
