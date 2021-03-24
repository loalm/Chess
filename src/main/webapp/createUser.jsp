<%@page import="chess.*"%>
<%@page import="chess.web.*"%>
<%@page import="java.util.*"%>

<%
	String newUsername = request.getParameter("userName");
	SessionWrapper.setUsername(session,newUsername);
	response.sendRedirect(request.getRequestURI()+"/..");
%>


