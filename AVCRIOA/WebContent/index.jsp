<%@page import="DatabaseConnection.*,java.io.*" %>
<%
String s=(String)session.getAttribute("errmessage");
if(s!=null){
out.println("<p style='position:absolute;left:65%;top:50%;z-index:1;color:red'>"+s+"</p>");
session.removeAttribute("errmessage");
}
%>
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" href="/AVCRIOA/css/istyle.css">
<script type="text/javascript" src="/AVCRIOA/js/jquery.js"></script>
<script type="text/javascript">
function validate(){
	var x=document.getElementById("p").value;
	var y=document.getElementById("u").value;
	if(x.length<=0 || y.length<=0){
		alert("Please enter your username/password");
		return false;
	}
}
</script>
</head>
<body>
	<div class="divison" id="header"><div id="headertext">Audio Video Class Room Interaction</div></div>
	<!--	Displaying student optionspane                -->
	<div class="studentpane" id="panecontainer">
		<div class="datapane" id="datapane" style='left:10%'>
			<div id="aboutus"><p id='abt'>&nbsp;&nbsp;About</p><br>
			<br>
			<p id="desc">
			&nbsp;&nbsp;&nbsp; Audio Video Class Room Interaction on Aakash aims at establishing a healthy and interactive classroom environment
			between instructor and students. The interaction can go on by the means of video requests, text doubts, recorded audio doubts
			and also enables the broadcasting of the instructor's video to multiple students.
			</p>
			</div>
			<div id="login" >
				<form action="loginValidation" method="post">
				<table style='width:100%;'>
				<tr><th colspan=2 style="font-size:25px;">Login</th></tr>
				<tr><td>Username:</td><td><input type='text' id='u' name='uname' autofocus title="Alphanumeric max length 20" pattern="[a-zA-Z0-9._]{1,20}"></td></tr>
				<tr><td>Password:</td><td><input type='password' id="p" name='passwd'></td></tr>
				<tr><td><br/></td></tr>
				<tr><td></td><td><input type=submit  onclick='return validate()' value="Login" style="height:25px;font-size:18px;">&nbsp;&nbsp;<input type=reset value="Clear" style="height:25px;font-size:18px;"></td></tr>
				<tr><td colspan=2 id='err'></td></tr>
				</table>
				</form>
			</div>	
		</div>
	
	</div>
	<!-- For Shadow Purposes -->	
	<div class="shadow"></div>
	<div class="divison" id="footer"><div id="footertext">AVCRIOA TEAM @ SI2013</div></div>
</body>
</html>
