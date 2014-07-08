/*
 * References:
 * http://www.html5rocks.com/en/tutorials/webrtc/basics/
 */
/* Student*/
//Declarations
var peer=null;
var localstream=null;
var socket=null;
var mediaConstraints = {'mandatory': {
									'OfferToReceiveAudio':true, 
									'OfferToReceiveVideo':true }};
//Getting required document elements
var studentid=document.getElementById('studentid').value;
var localvideo=document.querySelector('video');
var ins_id="";
//Capturing the local stream

function captureLocalStream(){
	$("#askquest").hide();
	document.getElementById('status').innerHTML="";
	//Making vendor prefix free
	navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || window.navigator.mozGetUserMedia || navigator.msGetUserMedia;
	if(!navigator.getUserMedia){
		alert("No browser support");
	}
	else{
    window.URL = window.URL || window.webkitURL;
    navigator.getUserMedia({video: true, audio: true},onGotSuccess, onError);
    function onGotSuccess(stream) {
        localstream = stream;
		localvideo.src = window.URL.createObjectURL(stream);
        localvideo.play();
        localvideo.muted=true;
        while(!localstream){}
        
        $("#getback").fadeIn("slow");
        $.post('/AVCRIOA/loadData',{id:studentid,iid:instructor,courseID:course},function(data,status){
        	if(data.search("#")){
        		var n=data.split("#");
        		var p=n[0];
        		n=n[1].split("\n");
        		ins_id=n[0];
        		console.log("ins_id"+n[0]);
        		document.getElementById('status').innerHTML=p;
        	}
        	else
        	document.getElementById('status').innerHTML=data;
        	
        });
  
    }
    function onError(err) {
        return;
    	}
	}
	
}

//Establishing Socket Connection
createSocketConnection();
function createSocketConnection(){
	socket=io.connect("http://10.2.80.46:1234");
	socket.on('connect',onConnect);
	socket.on('message',onMessage);
	socket.on('disconnect',onDisconnect);
}
function onDisconnect(){
	//confirm("Are you sure?");
	
}
function onConnect(){
	console.log("Successfully connected to server!!");
	
}
function createAnswerFailed(){
	console.log("Creating answer failed...");
}
var bool=false;
function showVideo(){
	$("#textpane").hide();
	$("#audiopane").hide();
	$("#videoimg").hide();
	$("#videopane").show();
	$("#audioimg").show();
	$("#textimg").show();
}
function onMessage(evt){
	if (evt.stype==studentid+ins_id ){
		createPeerConnection();
		document.getElementById('status').innerHTML="Connected to "+ins_id;
		showVideo();
		$("#getback").hide();
		}
	if(evt.check==studentid){
		bool=true;
	}
	if (evt.type === 'offer' && bool) {
	      console.log("Offer Received from peer...");
	      try{
	      peer.setRemoteDescription(new RTCSessionDescription(evt));
	      console.log('Creating and sending answer...');
	      peer.createAnswer(sendAnswer,createAnswerFailed, mediaConstraints);
	      }catch(err){
	    	  console.log(err.message);
	      }
	      finally{
	    	  bool=false;
	      }
	    }
	    else if (evt.type === 'candidate' &&evt.iid===ins_id) {
	      console.log('Received ICE candidate from peer...');
	      try{
	      var candidate = new RTCIceCandidate({sdpMLineIndex:evt.sdpMLineIndex, sdpMid:evt.sdpMid,candidate:evt.candidate});
	      peer.addIceCandidate(candidate);
	      }catch(Exception){console.log(Exception);}
	    }
	
	    else if (evt.type === 'bye'+studentid+ins_id) {
			socket.emit('message',{type:studentid,iid:ins_id,val:0});
			//socket.disconnect();
			localstream.stop();
			//createSocketConnection();
			localvideo.src="";
			$("#getback").hide();
			$("#askquest").show();
			document.getElementById('status').innerHTML="Disconnected";
			peer=null;
	    }
		 
}
function checkStatus(){
	socket.emit('message',{type:studentid,iid:instructor,val:0});
}

function sendAnswer(sdp) {
    peer.setLocalDescription(sdp);
    console.log("Sending: SDP");
    socket.emit('message',{check:ins_id});
    socket.json.send(sdp);//Sending SDP information as JavaScript Object Notation
  }
function getBackRequest(){
	document.getElementById('status').innerHTML="";
	socket.emit('message',{type:studentid,iid:ins_id,gbr:'hide',val:2});
	localstream.stop();
	localvideo.src="";
	$("#getback").hide();
	$("#askquest").fadeIn("slow");
	}
function createPeerConnection() {
    console.log("Creating peer connection");
    RTCPeerConnection = webkitRTCPeerConnection || mozRTCPeerConnection||RTCPeerConnection;
    var pc_config = {"iceServers":[]};
    try {
      peer = new RTCPeerConnection(pc_config);
    } catch (e) {
      console.log("Failed to create PeerConnection, exception: " + e.message);
    }
    // send any ice candidates to the other peer
    peer.onicecandidate = function (evt) {
      if (evt.candidate) {
        console.log('Sending ICE candidate...');
        socket.json.send({type: "candidate",
        				  iid:ins_id,
        				  sdpMLineIndex: evt.candidate.sdpMLineIndex,
            			  sdpMid: evt.candidate.sdpMid,
        				  candidate: evt.candidate.candidate});
      } else {
        console.log("End of candidates.");
      }
    };
    console.log('Adding local stream...');
    peer.addStream(localstream);
	
  }
