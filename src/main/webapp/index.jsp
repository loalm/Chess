<%@page import="chess.*"%>
<%@page import="chess.web.*"%>
<%@page import="java.util.*"%>
<html>
<head>
<title>Love's Chess</title>
<link rel="stylesheet" type="text/css" href="chess3.css">
<script src="chess3.js"></script>
<meta name="viewport" content="width=device-width, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<script src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
</head>
<body>
  <% String userName = SessionWrapper.getUsername(session); 
  if(userName==null) { 
    %>
    <%@include file="welcome.jsp"%>
    <%
    return;
  }
  %>
  You: <%=userName%> 
  <% if(SessionWrapper.getMyColor(session)!=null) { %>
  (<%=SessionWrapper.getMyColor(session) %>)
  <% } %>
    <%
      Date createTime = new Date(session.getCreationTime());
    	Game theGame = SessionWrapper.getGame(session);
    	if (theGame==null) { %>
        <%@include file="showGames.jsp"%>
        <%
        return;
    	}
      %>
  <form action="" method="POST" style="display:none;">
    Love's Chess <input name="from" id="from" autofocus="autofocus"> <input
      name="to" id="to"> <input type="submit">
      <%
    	String from = request.getParameter("from");
    	String to = request.getParameter("to");
    	if (from!=null&&from.length()==2&&to!=null&&to.length()==2 && theGame.isReady()) {
    		try {
    			theGame.move(from,to);
    		} catch (Exception e) {
    			out.println(e.getMessage());
    		}
    	}
    %>
  </form>
  <%=theGame.toHTML(session)%>
    <form action="logout.jsp" method="POST">
      <input type="submit" value="Logout">
    </form>
</body>
</html>


