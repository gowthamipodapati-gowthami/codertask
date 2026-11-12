<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,com.taskpro.model.Mission" %>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<% List<Mission> missions = (List<Mission>)request.getAttribute("missions"); %>
<div class="page-hero compact"><div class="eyebrow">ADMIN · CONTENT LIBRARY</div><h1>Question management</h1><p>All programming missions are retrieved dynamically from the database.</p><a class="btn btn-primary" href="<%= base %>/admin/add-question">＋ Add a question</a></div>
<div class="card table-card"><div class="table-scroll"><table><thead><tr><th>ID</th><th>Question title</th><th>Category</th><th>Difficulty</th><th>XP</th></tr></thead><tbody>
<% if (missions == null || missions.isEmpty()) { %><tr><td colspan="5" class="empty-row">No questions yet. Create your first mission.</td></tr><% } else { for(Mission m:missions) { %>
<tr><td class="muted">#<%= m.id() %></td><td><strong><%= HtmlUtil.escape(m.title()) %></strong></td><td><span class="pill"><%= HtmlUtil.escape(m.category()) %></span></td><td><%= HtmlUtil.escape(m.difficulty()) %></td><td class="accent"><strong>+<%= m.xpReward() %> XP</strong></td></tr>
<% } } %></tbody></table></div></div>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
