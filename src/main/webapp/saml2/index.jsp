<%@page import="org.pac4j.springframework.security.authentication.Pac4jAuthentication"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<h1>protected area</h1>
<a href="..">Back</a><br />
<br />
<a href="admin.jsp">Admin area</a>
<br /><br />
<% Pac4jAuthentication token = (Pac4jAuthentication) SecurityContextHolder.getContext().getAuthentication(); %>
profiles: <%=token.getProfiles()%><br />
