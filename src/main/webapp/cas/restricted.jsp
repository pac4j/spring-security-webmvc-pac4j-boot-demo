<%@page import="org.pac4j.springframework.security.authentication.ClientAuthenticationToken"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<h1>restricted area</h1>
<a href="index.jsp">To 'regular' protected page</a><br />
<a href="..">Back</a><br />
<br /><br />
<% ClientAuthenticationToken token = (ClientAuthenticationToken) SecurityContextHolder.getContext().getAuthentication(); %>
profile : <%=token.getUserProfile()%><br />
