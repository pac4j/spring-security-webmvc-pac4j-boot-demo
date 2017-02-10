<%@ page import="org.pac4j.jwt.profile.JwtGenerator" %>
<%@ page import="org.pac4j.core.profile.CommonProfile" %>
<%@ page import="org.pac4j.demo.spring.Constants" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.pac4j.springframework.security.authentication.Pac4jAuthentication" %>
<h1>Generate JWT token</h1>
<a href="..">Back</a><br />
<br /><br />
<%
    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String token = "";
    if (auth != null && auth instanceof Pac4jAuthentication) {
        final Pac4jAuthentication pac4jAuth = (Pac4jAuthentication) auth;
        final CommonProfile profile = pac4jAuth.getProfile();
        final JwtGenerator generator = new JwtGenerator(Constants.JWT_SALT);
        token = generator.generate(profile);
    }
%>
token: <%=token%><br />
