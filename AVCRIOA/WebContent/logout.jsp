<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
if(session.getAttribute("iid")!=null){
session.removeAttribute("iid");
response.sendRedirect("/AVCRIOA/index.jsp");
}
if(session.getAttribute("studentID")!=null){
	session.removeAttribute("studentID");
	out.println("<center><h1> You have been successfully logged out!!<br>Please restart AVCRIOA app to continue</h1></center>");
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Log out</title>
</head>
<body>

</body>
</html>