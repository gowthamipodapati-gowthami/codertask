<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,com.taskpro.model.Mission" %>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<% List<Mission> missions=(List<Mission>)request.getAttribute("missions"); String selected=(String)request.getAttribute("category"); %>
<div class="page-hero compact"><div class="eyebrow">YOUR QUEST BOARD</div><h1>Programming missions</h1><p>Choose carefully: you get exactly <strong>one attempt</strong> per mission, even if your answer is wrong.</p></div>
<div class="tabs" role="navigation" aria-label="Mission categories"><% for(String cat : new String[]{"All","Java","SQL","DSA"}) { %><a class="tab <%= cat.equals(selected)?"active":"" %>" href="<%= base %>/missions?category=<%= cat %>"><%= cat %></a><% } %></div>
<% if(missions==null || missions.isEmpty()) { %><div class="card empty-state"><div class="feature-icon">∅</div><h2>No missions available</h2><p>Ask the admin to add questions or choose another category.</p></div><% } else { %>
<div class="mission-grid"><% for(Mission m:missions) { %><article class="card mission-card"><div class="mission-meta"><span class="pill"><%= HtmlUtil.escape(m.category()) %></span><span class="pill difficulty-<%= HtmlUtil.escape(m.difficulty().toLowerCase(java.util.Locale.ROOT)) %>"><%= HtmlUtil.escape(m.difficulty()) %></span><span class="xp-chip">⚡ <%= m.xpReward() %> XP</span></div><h2><%= HtmlUtil.escape(m.title()) %></h2><p class="question-text"><%= HtmlUtil.escape(m.description()) %></p>
<% if(m.attempted()) { %><div class="attempted-label">✓ Attempt submitted · This mission is locked</div><% } else { %>
<form method="post" action="<%= base %>/submit-answer" class="answer-form"><input type="hidden" name="csrf" value="<%= CsrfUtil.token(request) %>"><input type="hidden" name="missionId" value="<%= m.id() %>">
<label class="answer-option"><input type="radio" name="answer" value="A" required><span class="letter">A</span><span><%= HtmlUtil.escape(m.optionA()) %></span></label>
<label class="answer-option"><input type="radio" name="answer" value="B"><span class="letter">B</span><span><%= HtmlUtil.escape(m.optionB()) %></span></label>
<label class="answer-option"><input type="radio" name="answer" value="C"><span class="letter">C</span><span><%= HtmlUtil.escape(m.optionC()) %></span></label>
<label class="answer-option"><input type="radio" name="answer" value="D"><span class="letter">D</span><span><%= HtmlUtil.escape(m.optionD()) %></span></label>
<button type="submit" class="btn btn-primary btn-full">Submit answer →</button></form><% } %></article><% } %></div><% } %>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
