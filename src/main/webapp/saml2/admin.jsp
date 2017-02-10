<%@page import="org.pac4j.springframework.security.authentication.Pac4jAuthentication"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<h1>admin area</h1>
<a href="..">Back</a><br />
<br /><br />
<% Pac4jAuthentication token = (Pac4jAuthentication) SecurityContextHolder.getContext().getAuthentication(); %>
profiles: <%=token.getProfiles()%><br />
