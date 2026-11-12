<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,com.taskpro.model.LeaderboardEntry" %>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<% List<LeaderboardEntry> players=(List<LeaderboardEntry>)request.getAttribute("players"); %>
<div class="page-hero compact centered"><div class="eyebrow">GLOBAL RANKINGS</div><h1>🏆 Leaderboard</h1><p>Build your skills, earn experience, and rise through the ranks.</p></div>
<div class="card table-card"><div class="table-scroll"><table><thead><tr><th>Rank</th><th>Player</th><th>Level</th><th>Completed missions</th><th>Total XP</th></tr></thead><tbody>
<% if(players==null || players.isEmpty()) { %><tr><td class="empty-row" colspan="5">No players yet. Be the first to register!</td></tr><% } else { int rank=0; for(LeaderboardEntry p:players) { rank++; %>
<tr class="<%= rank<=3?"top-player":"" %>"><td><span class="rank"><%= rank==1?"🥇":rank==2?"🥈":rank==3?"🥉":"#"+rank %></span></td><td><strong><%= HtmlUtil.escape(p.username()) %></strong></td><td><span class="pill">LVL <%= p.level() %></span></td><td><%= p.completed() %></td><td class="accent"><strong><%= p.xp() %> XP</strong></td></tr>
<% } } %></tbody></table></div></div><p class="centered muted foot-note">Ranked by XP, then completed missions, then username.</p>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
