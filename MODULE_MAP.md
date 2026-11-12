# Assignment modules mapped to source files

| # | Assignment requirement | Implementation |
|---|---|---|
| 1 | Home UI / navigation / dark CSS | `src/main/webapp/index.html`, `css/style.css`, JSP shared header/footer |
| 2 | Student registration / validation / unique accounts | `register.jsp`, `RegisterServlet`, `UserDAO`, `DBConnection` |
| 3 | Student login / session / logout | `login.jsp`, `LoginServlet`, `LogoutServlet`, `StudentAuthFilter` |
| 4 | Admin login/dashboard / role session / config/context | `admin-login.jsp`, `AdminLoginServlet`, `AdminDashboardServlet`, `AdminLogoutServlet`, `AdminAuthFilter` |
| 5 | Database / JDBC / SQL schemas | `sql/01_schema.sql`, `db.properties`, `DBConnection`, `dao/*` |
| 6 | Admin create question | `WEB-INF/views/add-question.jsp`, `AddQuestionServlet`, `MissionDAO` |
| 7 | Admin view questions via RequestDispatcher / scriptlets | `WEB-INF/views/view-questions.jsp`, `ViewQuestionsServlet` |
| 8 | Dashboard and progress | `WEB-INF/views/dashboard.jsp`, `DashboardServlet`, `UserDAO`, `ProgressUtil` |
| 9 | Mission lists / categories / 4 options | `WEB-INF/views/missions.jsp`, `MissionServlet`, `MissionDAO` |
| 10 | Evaluate answers / XP / one attempt / transaction | `WEB-INF/views/mission-result.jsp`, `SubmitAnswerServlet`, `MissionResultServlet`, `SubmissionDAO` |
| 11 | Global leaderboard | `WEB-INF/views/leaderboard.jsp`, `LeaderboardServlet`, `LeaderboardDAO` |
| 12 | Integration / tests / cookies / sessions / errors | `TESTING.md`, `CsrfUtil`, `StudentAuthFilter`, `AdminAuthFilter`, all servlets; category cookie, session invalidation |

Protected JSPs are placed under `WEB-INF/views/` so users cannot bypass the role-checking Servlet by opening a `.jsp` directly. This is an intentional security improvement while preserving the required JSP page names.

The original PDF's app brand is CodeQuest; the Eclipse/Maven project name is **taskpro** as requested.
