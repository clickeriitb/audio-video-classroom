//This will be run when document loaded fully
$(function(){
	$("#audioimg").click(function(){
		$("#videopane").fadeOut("slow");
		$("#textpane").fadeOut("slow");
		$("#audiopane").fadeIn("slow");
		$("#videoimg").show();
		$("#textimg").show();
		$("#audioimg").hide();
	});
	$("#videoimg").click(function(){
		
		$("#textpane").fadeOut("slow");
		$("#audiopane").fadeOut("slow");
		$("#videoimg").hide();
		$("#textimg").show();
		$("#audioimg").show();
		$("#videopane").fadeIn("slow");
		});
	$("#textimg").click(function(){
		$("#videopane").fadeOut("slow");
		$("#audiopane").fadeOut("slow");
		$("#textimg").hide();
		$("#videoimg").show();
		$("#audioimg").show();
		$("#textpane").fadeIn("slow");
	});
	$("#broadcastimg").click(function(){
		document.location.href="/AVCRIOA/student/broadcast.jsp";
	});
	$("#getback").fadeOut("slow");
	
	try{
		console.log(webkitAudioContext);
	}
	catch(e){
		console.log("Error Message"+e.message);
	}
window.URL = window.URL || window.webkitURL;
navigator.getUserMedia  = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
});

function goback1(){
	document.location.href="/AVCRIOA/student/index.jsp";
}
var interval;
var maxmin=5;
var recorder;
var recIndex=0;
fileName=document.getElementById('studentid').value;
var select=null;

var onSuccessAudio = function(s) {
	try{
		var context = new webkitAudioContext();
	}
	catch(e){
		console.log("Your browser doesn't support WebAudio API");
	}
	var mediaStreamSource = context.createMediaStreamSource(s);
	recorder = new Recorder(mediaStreamSource);
	recorder.record();
	countdown();		
}
var onFailAudio = function(e) {
	console.log('Rejected!', e);
};


var onFail = function(e) {
	console.log('Rejected!', e);
};

function countdown(){
		var min=maxmin;
		var sec=00;
		interval=setInterval(function(){

		if(sec==0){
			if(min==0){
				stopRecording();
				return;
			}
			else{
				min--;
				sec=60;
			}
		}
		sec--;
	document.getElementById("duration").innerHTML=""+((min<10)?"0"+min:min)+ " : "+((sec<10)?"0"+sec:sec);
	},1000);
}

function clearing(){
	console.log("interval cleared..!");
	clearInterval(interval);

}
function submitAudio(){
	console.log("Submitting Audio...!");
	var form=new FormData();
//		Appending file to form Data..!
	form.append("file1",data,fileName);
	audio=document.querySelector("audio");
	select=document.getElementById("course");
	xmlhttp=new XMLHttpRequest();
	var dur=Math.round(audio.duration*100)/100;
	xmlhttp.open('POST','/AVCRIOA/UploadAudio?course='+select.value+'&dur='+dur,true);
	xmlhttp.send(form);
	
	xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4&&xmlhttp.status==200){
			document.getElementById("uploadaudiostatus").innerHTML="File has been submitted Successfully..!";
		}
		else if(xmlhttp.readyState==4){
			document.getElementById("uploadaudiostatus").innerHTML="Error! While Uploading the file..!";	
			Recorder.forceDownload(data,fileName);
		}
		
	};
	
	document.getElementById("uploadaudiostatus").innerHTML="Uploading...!";
			
}

function playAudio(){
	audio=document.querySelector("audio");
	if(audio.src==""||audio.src==null){
		alert("Not yet recorded anything..!");
	}
	else{
		audio.play();
	}
}

function startRecording() {
	try{
		console.log(webkitAudioContext);
	}
	catch(e){
		console.log(e.message);
		alert("Web Audio API is not supported in your browser..!");
		return;
	}
	document.getElementById("uploadaudiostatus").innerHTML="&nbsp;&nbsp;<br>";
	if (navigator.getUserMedia) {
		navigator.getUserMedia({audio: true}, onSuccessAudio, onFailAudio);
	} else {
		console.log('navigator.getUserMedia not present');
	}
}
function stopRecording() {
	console.log("Stopping recording..!<br/>");
	document.getElementById('pio').disabled=false;
	document.getElementById('fd').disabled=false;
	clearing();
	recorder.stop();
	recorder.exportWAV( function(blob){
		data=blob;
		audio.src=window.URL.createObjectURL(blob);
	});
}

