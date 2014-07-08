<%@page import="DatabaseConnection.*" %>
<%
if(session.getAttribute("studentID")==null){
	response.sendError(401,"Request unauthorized..");
}	
else{
%>
<!DOCTYPE html>
<html>
<head>
<% 

String studentID=(String)session.getAttribute("studentID");
System.out.println(studentID);
if(studentID==null){
	response.sendRedirect("/AVCRIOA/index.jsp");
}
else{
System.out.println("Student :"+studentID+" opened from ip:"+request.getRemoteAddr());
out.println("<input type=hidden id='studentid' value="+studentID+" />");

%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" href="/AVCRIOA/css/style.css">
<script type="text/javascript" src="/AVCRIOA/js/jquery.js"></script>
<script type="text/javascript" src="/AVCRIOA/js/student.js"></script>
<script type="text/javascript" src="/AVCRIOA/js/record.js"></script>
<script type="text/javascript" src="http://10.2.80.46:1234/socket.io/socket.io.js"></script>
<script>
$(window).bind('beforeunload', function() {
	checkStatus();
} );
</script>
</head>
<body>
	<div class="divison" id="header"><div id="headertext">Audio Video Class Room Interaction on Aakash</div></div>
	<!--	Displaying student optionspane                -->
	<div class="studentpane" id="panecontainer">
		<div class="datapane" id="datapane">
		<a href="/AVCRIOA/logout.jsp" style='float:right;text-decoration:none' onclick='checkStatus()'>Logout</a>
		<div id="selectpane">
		Select course&nbsp;&nbsp;&nbsp;<select id='course' onchange='setcourse()'>
		<option value='none'>Select</option>
		<option value='CS101'>CSE101</option>
		<option value='CS102'>CSE102</option>
		</select>
		</div>
		<div id="textpane">
			<div id="textform">
			<div id="stat">Enter your question...</div>
			<textarea  name="question" id="question" placeholder="Write your doubt here..."></textarea>
			<input type="submit" id="submitText" value="Ask this doubt" onclick="return submitText()">
			</div>
		</div>
		<!-- Audio Recording -->
		<div class="audiopane" id="audiopane">	
				<br/><br/>
				<div id="stat">Speak out your doubt..&nbsp;&nbsp;	
				</div><br/>
				<audio id="audio" controls id="audiocontrol"></audio>&nbsp;&nbsp;
				<span id="duration" style="padding:5px;">05:00</span>
				<br/>
				<div id="uploadaudiostatus">&nbsp;&nbsp;&nbsp;<br/></div>
				<input onclick="startRecording()" id="sr" type="button" class="studentbtn" value="Start Recording" style="left:4%">&nbsp;&nbsp;&nbsp;&nbsp;
				<input onclick="stopRecording()" id="str" type="button" class="studentbtn" value="Stop Recording">&nbsp;&nbsp;&nbsp;&nbsp;
				<input type=button id="pio" onclick="playAudio()" value="Preview" disabled>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type=button id="fd" onclick="submitAudio()" value="Forward" disabled>
			<script>
			audio=document.querySelector("audio");
			audio.addEventListener('loadedmetadata',function(){console.log("Data Loaded..!");
			document.getElementById("duration").innerHTML="Duration :"+Math.round(audio.duration*100)/100+" sec";
			})
			</script>
		
		</div>
		<div id="videopane">
			<video id="videoelement" controls autoplay></video>	
			<div id="status">Status</div>
			<input type="button" class="studentbtn" id="askquest" value="Ask" onclick="captureLocalStream()">
			<input type="button" class="studentbtn" id="getback" value="GetBack" onclick="getBackRequest()">
			</div>
		</div>
	<!--Following divison contains buttons to manipulate -->
		<div class="optionspane" id="optionspane">
		<br/><br/>
		<!--  	<img src="/AVCRIOA/images/record.png" id="audioimg"><br>
			<img src="/AVCRIOA/images/video.png" id="videoimg"><br>
			<img src="/AVCRIOA/images/text.jpg" id="textimg"><br>-->
			<div id="audioimg"></div><br>
			<div id="broadcastimg"></div><br>
			<div id="videoimg"></div><br>
			<div id="textimg"></div>
		</div>
	</div>
	<!-- For Shadow Purposes -->	
	<div class="shadow"></div>
	<div class="divison" id="footer"><div id="footertext">AVCRIOA TEAM @ SI-2013</div></div>
	<script type="text/javascript" src="/AVCRIOA/js/student_connection.js"></script>
	<script type="text/javascript" src="/AVCRIOA/js/textForm.js"></script>	
</body>
</html>
<%
}
}
%>