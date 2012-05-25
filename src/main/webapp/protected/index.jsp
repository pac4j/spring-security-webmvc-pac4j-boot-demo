<%@page import="com.github.leleuj.ss.oauth.client.authentication.OAuthAuthenticationToken"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<h1>protected/index</h1>
<br />
<% OAuthAuthenticationToken token = (OAuthAuthenticationToken)SecurityContextHolder.getContext().getAuthentication(); %>
name : <%=token.getName()%><br />
authentication : <%=token%><br />
user profile : <%=token.getUserProfile()%><br />
