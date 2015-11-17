<%@page import="org.pac4j.http.client.indirect.*"%>
<%
   FormClient formClient = (FormClient) application.getAttribute("formClient");
%>
<form action="<%=formClient.getCallbackUrl()%>" method="POST">
	<input type="text" name="username" value="" />
	<p />
	<input type="password" name="password" value="" />
	<p />
	<input type="submit" name="submit" value="Submit" />
</form>
