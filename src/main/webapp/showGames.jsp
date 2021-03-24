<%@page import="chess.*"%>
<%@page import="chess.web.*"%>
<%@page import="java.util.*"%>
<br>
Available games:
<hr>
<%
	for (Game game:Reception.getGames()) {
%>
<%=game%>
<a href="joinGame.jsp?<%=game.created%>"><button>Join</button></a>
<br>
<%
	}
%>
<hr>

<form action="createGame.jsp" method="POST">
  <input type="submit" value="Create new game!">
</form>

<form action="logout.jsp" method="POST">
  <input type="submit" value="Logout">
</form>

<script>
  setTimeout(function(){ location.href='/'},3000);
</script>
