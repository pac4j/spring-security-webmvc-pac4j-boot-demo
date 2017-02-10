<%@page import="org.pac4j.springframework.security.authentication.Pac4jAuthentication"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.pac4j.core.profile.CommonProfile" %>
<%@ page import="java.util.List" %>
<h1>Not protected area</h1>
<a href="..">Back</a><br />
<br /><br />
<%Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    List<CommonProfile> profiles = null;
    if (auth != null && auth instanceof Pac4jAuthentication) {
        Pac4jAuthentication token = (Pac4jAuthentication) auth;
        profiles = token.getProfiles();
    }
%>
profiles: <%=profiles%>
