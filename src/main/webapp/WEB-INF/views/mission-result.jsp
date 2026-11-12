<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.taskpro.model.SubmissionResult" %>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<% SubmissionResult result=(SubmissionResult)request.getAttribute("result"); %>
<section class="result-wrap"><div class="card result-card"><div class="result-symbol <%= result.correct()?"success":"failed" %>"><%= result.correct()?"✓":result.duplicate()?"!":"×" %></div><div class="eyebrow">MISSION FEEDBACK</div><h1><%= result.duplicate()?"Already attempted":result.correct()?"Mission cleared!":"Good effort!" %></h1><p><%= HtmlUtil.escape(result.message()) %></p>
<% if(!result.duplicate()) { %><h3><%= HtmlUtil.escape(result.missionTitle()) %></h3><div class="result-details"><div>Selected option <strong><%= HtmlUtil.escape(result.selectedAnswer()) %></strong></div><div>Correct option <strong><%= HtmlUtil.escape(result.correctAnswer()) %></strong></div><div>XP earned <strong>+<%= result.awardedXp() %></strong></div><div>Total XP <strong><%= result.totalXp() %></strong></div><div>Current level <strong><%= result.level() %></strong></div></div><% } %>
<div class="button-group centered"><a href="<%= base %>/missions" class="btn btn-primary">More missions →</a><a href="<%= base %>/dashboard" class="btn btn-outline">My dashboard</a></div></div></section>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
