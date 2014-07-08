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

%>
<script type="text/javascript">
	window.onload=function(){
		var session_name=document.getElementById('i_id').value;
		$.post('/AVCRIOA/broadcastUpdate',{session_id:session_name},function(data,status)
				{
					document.getElementById('status').innerHTML=data;
				});
	}
</script>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" href="/AVCRIOA/css/istyle.css">
<script type="text/javascript" src="/AVCRIOA/js/jquery.js"></script>
<script type="text/javascript" src="/AVCRIOA/js/instructor.js"></script>
<script type="text/javascript" src="http://10.2.80.46:2345/socket.io/socket.io.js"></script>
</head>
<body>
	<div class="divison" id="header"><div id="headertext">Audio Video Class Room Interaction</div></div>
	<div class="studentpane" id="panecontainer">
		<div class="datapane" id="datapane" style="top:-2%">
		<h2 style="left:10%;">Welcome <%=(String)session.getAttribute("iid") %></h2>
		<p id='sname' style='margin-left:21%;margin-top:3%;color:red'></p>
		
		 <button onclick="goback()" style='position:absolute;left:90%;margin-top:-5%'>Back</button>
		<div id="input">
			Session name:<p style='font-family:georgia; font-size:20px; color:blue;'><%=(String)session.getAttribute("iid") %></p>
			<button id="broadcast" style='height:30px;font-size:14px' onclick="Broadcast()">Broadcast</button>
			<button id="disconnect" style='height:30px;font-size:14px' onclick="DisCon()">Disconnect</button>
			<br><br>
			<b>Status: </b>
			<p id="status" style='font-size:15px;left:60px; color:red; top:180px'>hello....</p>
		</div>
		<div id='broadcastvideo' >
		<video  id='inst_video' controls autoplay width=100% height=90%></video>
		</div>
	</div>
	</div>
	<!-- For Shadow Purposes -->	
	<div class="shadow"></div>
	<div class="divison" id="footer"><div id="footertext">AVCRIOA TEAM @ SI2013</div></div>
	<script type="text/javascript" src="/AVCRIOA/js/broadcast.js"></script>
</body>
</html>
<%
}
%>