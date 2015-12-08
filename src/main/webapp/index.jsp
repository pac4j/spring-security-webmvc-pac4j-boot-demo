<%@page import="org.pac4j.core.exception.RequiresHttpAction"%>
<%@page import="org.pac4j.springframework.security.authentication.*"%>
<%@page import="org.springframework.security.core.*"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="org.pac4j.core.context.*"%>
<%@page import="org.pac4j.core.profile.*"%>
<%@page import="org.pac4j.http.client.indirect.*"%>
<%@page import="org.pac4j.oauth.client.*"%>
<%@page import="org.pac4j.cas.client.*"%>
<%@page import="org.pac4j.saml.client.*"%>
<%@ page import="org.pac4j.core.client.Clients" %>
<%
	WebContext context = new J2EContext(request, response);
	Clients clients = (Clients) application.getAttribute("clients");
	FacebookClient fbClient = (FacebookClient) clients.findClient("FacebookClient");
	TwitterClient twClient = (TwitterClient) clients.findClient("TwitterClient");
	FormClient formClient = (FormClient) clients.findClient("FormClient");
	IndirectBasicAuthClient baClient = (IndirectBasicAuthClient) clients.findClient("IndirectBasicAuthClient");
	CasClient casClient = (CasClient) clients.findClient("CasClient");
	SAML2Client saml2Client = (SAML2Client) clients.findClient("SAML2Client");
%>
<h1>index</h1>
<a href="facebook/index.jsp">Protected url by Facebook : facebook/index.jsp</a> (use a real account)<br />
<a href="twitter/index.jsp">Protected url by Twitter : twitter/index.jsp</a> (use a real account)<br />
<a href="google/index.jsp">Protected url by Google : google/index.jsp</a> (use a real account)<br />
<a href="form/index.jsp">Protected url by form authentication : form/index.jsp</a> (use login = pwd)<br />
<a href="basicauth/index.jsp">Protected url by basic auth : basicauth/index.jsp</a> (use login = pwd)<br />
<a href="cas/index.jsp">Protected url by CAS : cas/index.jsp</a> (use login = pwd)<br />
<a href="saml/index.jsp">Protected url by SAML : saml/index.jsp</a> (use testpac4j at gmail.com / Pac4jtest)<br />
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
<br /><br />
<hr />
<%
try {
%>
<a href="<%=fbClient.getRedirectAction(context, false).getLocation()%>">Authenticate with Facebook</a><br />
<a href="<%=twClient.getRedirectAction(context, false).getLocation()%>">Authenticate with Twitter</a><br />
<a href="<%=formClient.getRedirectAction(context, false).getLocation()%>">Authenticate with form</a><br />
<a href="<%=baClient.getRedirectAction(context, false).getLocation()%>">Authenticate with basic auth</a><br />
<a href="<%=casClient.getRedirectAction(context, false).getLocation()%>">Authenticate with CAS</a><br />
<a href="<%=saml2Client.getRedirectAction(context, false).getLocation()%>">Authenticate with SAML</a><br />
<%
} catch (RequiresHttpAction e) {
	// should not happen
}
%>
