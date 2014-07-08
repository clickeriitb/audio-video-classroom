<%@page import="DatabaseConnection.*" %>

<!DOCTYPE html>
<html>
<head>
<%
if(session.getAttribute("iid")==null){
	response.sendRedirect("/AVCRIOA/index.jsp");
}
else{
	out.println("<input type='hidden' id='i_id' value="+(String)session.getAttribute("iid")+" />");
}
%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" href="/AVCRIOA/css/istyle.css">
<style>
button {
	height:25px;
	font-size:18px;
}
</style>
<script type="text/javascript" src="/AVCRIOA/js/jquery.js"></script>
<script type="text/javascript" src="/AVCRIOA/js/instructor.js"></script>
<script type="text/javascript" src="http://10.2.80.46:1234/socket.io/socket.io.js"></script>
</head>
<body>
	<div class="divison" id="header"><div id="headertext">Audio Video Class Room Interaction</div></div>
	<div class="studentpane" id="panecontainer">
		<div class="datapane" id="datapane" style="margin-top:-20px;">
		<h2>Welcome <%=(String)session.getAttribute("iid") %>
		<a href="/AVCRIOA/logout.jsp" style='float:right;text-decoration:none'>Logout</a>
		<button id='en' onclick='activate()' style='height:30px;float:right;margin-right:5%;margin-top:2%;'>Disable</button></h2>
		<p id='sname' style='margin-left:50%;margin-top:3%;color:red;'></p>
	<div id='controls'>
		<button onclick='showVideoRequests()' id='svr' class="instrcontrols" style="background-image:url('/AVCRIOA/images/btnhover.png');color:white">VideoRequests</button>
		<button onclick='load()' id='tq' class="instrcontrols">TextDoubts</button>
		<button id='dbt' onclick='viewAudioDoubts()' class="instrcontrols">Saved Audio doubts</button>
		<button id='broadcast' onclick='openBroadCast()'>Broadcast</button>
		</div>
		<div id="videoRequests"></div>
		<div id='videoelement' style="left:25%;top:17%;height:81%;width:70%">
		<button style='float:right' id='disc' onclick='disconnect_std()'>Disconnect</button>
		<video controls autoplay width=100% height=90%></video>
		</div>
		<div id="textualRequests" >
		
		</div>
		<div id="audioRequests">
			
		
		</div>
		</div>
		
	</div>
	<!-- For Shadow Purposes -->	
	<div class="shadow"></div>
	<div class="divison" id="footer"><div id="footertext">AVCRIOA TEAM @ SI2013</div></div>
	<script type="text/javascript" src="/AVCRIOA/js/videoRequests.js"></script>
</body>
</html>
