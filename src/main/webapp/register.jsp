<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/header.jspf" %>
<section class="auth-layout"><div class="auth-intro"><div class="eyebrow">NEW PLAYER</div><h1>Begin your coding journey.</h1><p>Create an account to take on programming challenges and earn your first XP.</p><div class="mini-info">Your journey starts at <strong>Level 1</strong> with <strong>0 XP</strong>.</div></div><div class="card auth-card"><h2>Create account</h2><p class="muted">Join the taskpro programming arena.</p>
<form action="<%= base %>/register" method="post" class="stack-form"><input type="hidden" name="csrf" value="<%= CsrfUtil.token(request) %>">
<label for="username">Username</label><input id="username" name="username" type="text" value="<%= HtmlUtil.escape(request.getAttribute("oldUsername")) %>" required minlength="3" maxlength="30" pattern="[A-Za-z0-9_]+" autocomplete="username" placeholder="e.g. codechamp">
<label for="email">Email address</label><input id="email" name="email" type="email" value="<%= HtmlUtil.escape(request.getAttribute("oldEmail")) %>" required maxlength="190" autocomplete="email" placeholder="you@example.com">
<label for="password">Password</label><input id="password" name="password" type="password" required minlength="8" maxlength="128" autocomplete="new-password" placeholder="At least 8 characters">
<label for="confirmPassword">Confirm password</label><input id="confirmPassword" name="confirmPassword" type="password" required minlength="8" maxlength="128" autocomplete="new-password" placeholder="Repeat password">
<button class="btn btn-primary btn-full" type="submit">Create student account →</button></form><p class="form-caption">Already registered? <a href="<%= base %>/login">Log in</a></p></div></section>
<%@ include file="/WEB-INF/includes/footer.jspf" %>
