# Functional test checklist

Use `sql/03_useful_queries.sql` to verify live MySQL data after each action.

- [ ] Home page navigation: Register, Student Login, Admin Login, Missions, Leaderboard.
- [ ] Student can register using a unique username and email.
- [ ] Duplicate username and duplicate email are rejected.
- [ ] Required fields, email format, password length, and confirmation are validated.
- [ ] Student starts with `xp=0`, `level=1`.
- [ ] Student logs in using username **or** email, but not an incorrect password.
- [ ] Visiting `/dashboard`, `/missions`, or `/submit-answer` anonymously redirects to student login.
- [ ] Admin cannot access student-only routes without a student session (and vice versa).
- [ ] Admin login works with SQL seeded admin credentials, creates admin session.
- [ ] Admin dashboard, Add Question, and View Questions are admin-only.
- [ ] Admin creates a question; it appears immediately in View Questions and student Missions.
- [ ] Java / SQL / DSA category filtering works; preference cookie remembers last selected category.
- [ ] Mission cards show four answers **without correct_answer** in SQL result or HTML.
- [ ] Correct submission stores a submission row and adds only configured XP.
- [ ] Wrong submission stores an incorrect row and awards 0 XP.
- [ ] A second attempt on the same mission is rejected (unique key + transaction).
- [ ] At total XP >=100, displayed Level increases according to `floor(xp/100)+1`.
- [ ] Student dashboard displays completed correct missions and progress bar.
- [ ] Leaderboard sorts by XP descending and shows top-three indicators.
- [ ] Logout invalidates session; protected URLs then redirect to login.
- [ ] Expired/missing CSRF token cannot post an answer or create a question.
- [ ] If MySQL is down, public and protected pages show a useful error message without raw SQL details.

## Example XP math

- First mission: 35 XP => Level 1, progress 35%.
- Second mission: 50 XP => total 85 XP, Level 1, progress 85%.
- Third mission: 20 XP => total 105 XP, Level 2, progress 5%.
- Wrong answers: +0 XP and mission is locked from reattempts.
