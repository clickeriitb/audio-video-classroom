 var instructor="";
function submitText(){
	var x=document.getElementById("question").value;
	if(x.length<=0){
		alert("Please enter your question...");
		return false;
	}
	else if(x.length>1000){
		alert("Maximum length of allowed question exceeded(1000)");
		return false;
	}
	else{
		var studID=document.getElementById("studentid").value;
		$.post('/AVCRIOA/textDoubts',{question:x,studentID:studID,iid:instructor},function(data,status){
			if(status=="success"){
				alert("Your question has been submitted...");
				document.getElementById("question").value="";
			}
			else if(data=="error"){
				alert("Question not submitted, please try again");
			}
		});
	}
}
var course="";
function setcourse(){
	 course=document.getElementById("course").value;
	 if(localstream){
	 getBackRequest();
	 }
	hideAll();
	if(course!='none'){
	$.post("/AVCRIOA/setCourse",{courseID:course},function(data,status){
		if(status=="success"){
			instructor=data.split("\n")[0];
			checkStatus();
			$("#videopane").show();
			$("#audioimg").show();
			$("#textimg").show();
			$("#broadcastimg").show();
			}
		else{
			alert("Course not assigned,please try again");
		}
	});
	}
}
function hideAll(){
	$("#videopane").hide();
	$("#audiopane").hide();
	$("#textpane").hide();
	$("#audioimg").hide();
	$("#videoimg").hide();
	$("#textimg").hide();
	$("#broadcastimg").hide();
}