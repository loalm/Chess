<%@page import="chess.*"%>
<%@page import="chess.web.*"%>
<%@page import="java.util.*"%>

<%
	SessionWrapper.createGame(session);
	response.sendRedirect(request.getRequestURI()+"/..");
%>


