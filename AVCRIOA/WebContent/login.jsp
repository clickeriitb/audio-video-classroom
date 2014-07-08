<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!--
 	This page is done for demo purpose only. It will open the student
 	Module in Desktop. 
 	This file is not needed for accessing from apk 
  --> 
<%
session.setAttribute("studentID","1387");
response.sendRedirect("/AVCRIOA/student/index.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>