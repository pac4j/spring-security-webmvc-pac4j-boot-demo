<%@page import="org.pac4j.springframework.security.authentication.*"%>
<%@page import="org.springframework.security.core.*"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.pac4j.core.profile.*"%>
<h1>index</h1>
<a href="facebook/index.jsp">Protected url by Facebook : facebook/index.jsp</a> (use a real account)<br />
<a href="twitter/index.jsp">Protected url by Twitter : twitter/index.jsp</a> (use a real account)<br />
<a href="google/index.jsp">Protected url by Google : google/index.jsp</a> (use a real account)<br />
<a href="form/index.jsp">Protected url by form authentication : form/index.jsp</a> (use login = pwd)<br />
<a href="basicauth/index.jsp">Protected url by basic auth : basicauth/index.jsp</a> (use login = pwd)<br />
<a href="cas/index.jsp">Protected url by CAS : cas/index.jsp</a> (use login = pwd)<br />
<a href="saml/index.jsp">Protected url by SAML : saml/index.jsp</a> (use testpac4j / pac4jtest)<br />
<br />
<a href="/logout">logout</a>
<br /><br />
<%Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication();
UserProfile profile = null;
if (auth != null && auth instanceof ClientAuthenticationToken) {
    ClientAuthenticationToken token = (ClientAuthenticationToken) auth;
    profile = token.getUserProfile();
}
%>
profile : <%=profile%>
