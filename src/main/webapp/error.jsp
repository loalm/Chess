<%@page isErrorPage="true"%>
<%for(StackTraceElement element:exception.getStackTrace()) {%>
<%=element %>
<% } %>
