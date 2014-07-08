$(function(){
$("#audioRequests").load("/AVCRIOA/GetAudioQuest");

$(".instrcontrols").click(function(){
	$(".instrcontrols").css({"background-image":"url('/AVCRIOA/images/button1.png')","color":"black"});
	$(this).css({"background-image":"url('/AVCRIOA/images/btnhover.png')","box-shadow":"2px 2px 2px black","color":"white"});

});
});
function activate(){
	var x=document.getElementById("en").innerHTML;
	if(x=="Enable"){
		document.getElementById("en").innerHTML="Disable";
		hideAll(1);
	}
	else if(x=="Disable"){
		document.getElementById("en").innerHTML="Enable";
		hideAll(0);
	}
}
var bool=true;
function showVideoRequests(){
	bool=false;
	$("#audioRequests").hide();
	$("#textualRequests").hide();
	$("#videoRequests").show();
	$("#videoelement").show();

}
function viewAudioDoubts(){
	hideAll(0);
	$("#audioRequests").show();
}
function viewTextRequests(){
	hideAll(0);
	$("#textualRequests").show();
}
function hideAll(param){
	if(param==0){
		$("#videoRequests").hide();
		$("#videoelement").hide();
		$("#textualRequests").hide();
		$("#audioRequests").hide();
	}
	else if(param==1){
		$("#videoRequests").show();
		$("#videoelement").show();
	}
}

function load(){
	bool=true;
	hideAll(0);
	$("#textualRequests").show();
	var x=document.getElementById("i_id").value;
	if(x!=null){
	$.post("/AVCRIOA/textRetrievalInstructor",{iid:x},function(data,status){
		document.getElementById("textualRequests").innerHTML=data;
	});
	}
	load2();
}
function load2(){
	var x=document.getElementById("i_id").value;
	if(x!=null){
	$.post("/AVCRIOA/textRetrievalInstructor",{iid:x},function(data,status){
		document.getElementById("textualRequests").innerHTML=data;
	});
	}
	bool&&setTimeout(load2,2000);
}
function done(sno){
	$.post("/AVCRIOA/questionsStatus",{number:sno},function(data,status){
	});
}
function addListeners(audiocount){
	for (var j=1;j<audiocount;j++){
		ad = document.getElementById('aud'+j);
	    if (typeof window.addEventListener === 'function'){
	        (function (sudoad) {
	        	sudoad.addEventListener('loadeddata',function(){
	            	k="audstat"+sudoad.getAttribute("id").substring(3);
		        	document.getElementById(k).innerHTML="Loaded..";
	            });
	    
	            sudoad.addEventListener('playing', function(){
	            	k="audstat"+sudoad.getAttribute("id").substring(3);
	            	document.getElementById(k).innerHTML="Playing..";
	            });
	            sudoad.addEventListener('ended',function(){
	            	k="audstat"+sudoad.getAttribute("id").substring(3);
		        	document.getElementById(k).innerHTML="Completed..";
		        	sudoad.src=sudoad.src;
	            });
	            sudoad.addEventListener('pause',function(){
	            	k="audstat"+sudoad.getAttribute("id").substring(3);
		        	document.getElementById(k).innerHTML="Paused..";
	            });
	         sudoad.addEventListener('loadstart',function(){
	            	k="audstat"+sudoad.getAttribute("id").substring(3);
		        	document.getElementById(k).innerHTML="Loading..";
	            });
	         sudoad.addEventListener('error',function(){
	         		k="audstat"+sudoad.getAttribute("id").substring(3);
		        	document.getElementById(k).innerHTML="Error";
	         });
	     
	         
	        })(ad);
	    }
	}
}
function openBroadCast(){
	document.location.href="/AVCRIOA/instructor/broadcast.jsp";
}
function deleteAudio(course,sno){
	$.get("/AVCRIOA/RemoveAudio?course="+course+"&sno="+sno,function(){console.log("Successfully Removed..!");});
	$("#audioRequests").load("/AVCRIOA/GetAudioQuest");
}