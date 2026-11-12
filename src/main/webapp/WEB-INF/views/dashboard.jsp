<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.taskpro.model.User,com.taskpro.util.ProgressUtil" %>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<% User student=(User)request.getAttribute("student"); %>
<div class="page-hero"><div class="eyebrow">YOUR PLAYER PROFILE</div><h1>Welcome back, <%= HtmlUtil.escape(session.getAttribute("studentName")) %> 👋</h1><p>Track your progress, challenge yourself, and climb the rankings.</p></div>
<% if(student != null) { %>
<div class="stat-grid"><div class="card stat-card"><span>TOTAL EXPERIENCE</span><strong><%= student.xp() %> <small>XP</small></strong><p>Your accumulated points</p></div><div class="card stat-card"><span>CURRENT LEVEL</span><strong><%= student.level() %> <small>LVL</small></strong><p>100 XP per level</p></div><div class="card stat-card"><span>MISSIONS COMPLETED</span><strong><%= student.completedMissions() %></strong><p>Correct answers only</p></div></div>
<div class="card progress-card"><div class="section-between"><div><div class="eyebrow">LEVEL PROGRESS</div><h2>Level <%= student.level() %> → <%= student.level()+1 %></h2></div><span class="big-percent"><%= ProgressUtil.progressPercent(student.xp()) %>%</span></div><div class="progress-bar" role="progressbar" aria-valuenow="<%= ProgressUtil.progressPercent(student.xp()) %>" aria-valuemin="0" aria-valuemax="100"><div class="progress-fill" style="width:<%= ProgressUtil.progressPercent(student.xp()) %>%"></div></div><p class="muted"><strong><%= ProgressUtil.xpUntilNextLevel(student.xp()) %> XP</strong> to your next level.</p></div>
<% } %>
<div class="card-grid two-col"><a href="<%= base %>/missions" class="card link-card"><span class="feature-icon">⚡</span><h3>Explore missions</h3><p>Java, SQL, and DSA practice are ready for you.</p><span class="link-cta">Solve now →</span></a><a href="<%= base %>/leaderboard" class="card link-card"><span class="feature-icon">★</span><h3>Global leaderboard</h3><p>See how your XP compares with other students.</p><span class="link-cta">Check rankings →</span></a></div>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
