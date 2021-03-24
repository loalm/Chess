<%@page import="chess.*"%>
<%@page import="chess.web.*"%>
<%@page import="java.util.*"%>
Welcome new user!
<br>
<br>
Please type your name:
<form action="createUser.jsp">
  <input type="text" name="userName" autofocus="autofocus" required="required"
    pattern=".{<%=SessionWrapper.MIN_LEN_USERNAME%>,10}"
    title="<%=SessionWrapper.MIN_LEN_USERNAME%> to 10 characters"> <br>
  <input type="submit">
</form>

Available games:
<hr>
<%
	for(Game game:Reception.getGames()) {
%>
<%=game%>
<br>
<%
	}
%>
<hr>

<script>
  setTimeout(function() {
    location.href = '/'
  }, 20000);
</script>

