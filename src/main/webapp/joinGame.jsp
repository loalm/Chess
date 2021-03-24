<%@page import="chess.*"%>
<%@page import="chess.web.*"%>
<%@page import="java.util.*"%>
<%
	final String gameCreated = request.getQueryString();
	SessionWrapper.joinGame(session,Long.valueOf(gameCreated));
  response.sendRedirect(request.getRequestURI()+"/..");
%>
