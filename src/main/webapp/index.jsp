<%@page import="org.scribe.up.session.HttpUserSession"%>
<%@page import="org.scribe.up.provider.impl.TwitterProvider"%>
<%@page import="com.github.leleuj.ss.oauth.client.authentication.OAuthAuthenticationToken"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
index<br />
<a href="protected/index.jsp">protected/index</a>
<br />
<a href="/j_spring_security_logout">logout</a>
<br /><br />
authentication : <%=SecurityContextHolder.getContext().getAuthentication()%>
<br /><br />
<%
	TwitterProvider twitterProvider = (TwitterProvider) application.getAttribute("twitterProvider");
	String authUrl = twitterProvider.getAuthorizationUrl(new HttpUserSession(request));
%>
<a href="<%=authUrl%>">Authenticate at Twitter</a>
