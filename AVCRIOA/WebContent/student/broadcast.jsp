<%@page import="DatabaseConnection.*" %>
<!DOCTYPE html>
<html>
<head>
<% 

String studentID=(String)session.getAttribute("studentID");
if(studentID==null){
	response.sendRedirect("/AVCRIOA/student/index.jsp");
}
out.println("<input type=hidden id='studentid' value="+studentID+" />");
%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" href="/AVCRIOA/css/style.css">
<script type="text/javascript" src="/AVCRIOA/js/jquery.js"></script>
<script type="text/javascript" src="/AVCRIOA/js/student.js"></script>
<script type="text/javascript" src="http://10.2.80.46:2345/socket.io/socket.io.js"></script>

</head>
<body>
	<div class="divison" id="header"><div id="headertext">Audio Video Class Room Interaction on Aakash</div></div>
	<!--	Displaying student optionspane                -->
	<div class="studentpane" id="panecontainer">
		<div class="datapane" id="datapane">
		
		<div id="broadcastpane"><!-- Broadcasting -->
		<video id="inst_video" controls autoplay></video><br>
		<div id="info">Connected to:<p id="conTo" style='position:absolute;margin-top:-20px;margin-left:12%;color:red'></p><br><br><br>
		<!-- <button id="disconnect" style='position:absolute;visibility:visible;top:5%;margin-left:80%;z-index:2;' onclick="discon_channel()">Disconnect</button> -->
		<!-- <p  style="font-size:20px;margin-top:-30px;color:green;">Online lectures</p>
		 -->
		 <div id="lectures">Online Lectures..</div>
		<div id="right"><!-- Broadcasters list -->
		
		</div>
		<input type="hidden" id="i_id" value="student" /> 
		</div>
		
		</div>
		</div>
	<!--Following divison contains buttons to manipulate -->
		<div class="optionspane" id="optionspane">
			<br><br>
			<img src="/AVCRIOA/images/broadcast.png"  style='display:none' id="broadcastimg"><br>
			<button id="disconnect" style='visibility:hidden;font-size:18px;width:100%' onclick="discon_channel()">Disconnect</button><br><br>
			<button onclick='goback1()' style='font-size:18px;width:100%;'>Back</button>
		</div>
	</div>
	<!-- For Shadow Purposes -->	
	<div class="shadow"></div>
	<div class="divison" id="footer"><div id="footertext">AVCRIOA TEAM @ SI2013</div></div>
	<script type='text/javascript' src="/AVCRIOA/js/broadcast.js"></script>
</body>
</html>

