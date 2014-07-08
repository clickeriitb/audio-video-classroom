/*
 * References:
 * http://www.html5rocks.com/en/tutorials/webrtc/basics/
 */
/*Teacher Portal*/

//updating the requests continuously
//window.onload=reload;
function reload(){
	$.post("/AVCRIOA/listRequests",{iid:instructor_id},function(data,success){
		document.getElementById('videoRequests').innerHTML=data;
		});
	setTimeout(reload,1000);
	}
createSocketConnection();
reload();
//Declarations and getting Required elements
var peer=null;
var sid,temp;
var mediaConstraints = {'mandatory': {
    'OfferToReceiveAudio':true, 
    'OfferToReceiveVideo':true }};
var studentvid=document.querySelector('video');
var instructor_id=document.getElementById('i_id').value;
//Establishing socket connection
function createSocketConnection(){
	socket=io.connect("http://10.2.80.46:1234");
	socket.on('connect',onConnect);
	socket.on('message',onMessage);
}
function onConnect(){
	console.log("Successfully connected to server...");
}
function call(e){
	document.getElementById("sname").innerHTML=e.innerHTML;
	document.getElementById("disc").disabled=false;
	temp=sid;
	sid=e.innerHTML;
	if (peer){
		//disconnecting already existing peer
		if(sid!=temp){
					socket.emit('message',{type:'bye'+temp+instructor_id});
					peer.close();
					peer=null;
					}
		}
  createPeerConnection();
  socket.emit('message',{stype:sid+instructor_id});
  peer.createOffer(sendOffer, offerfailed, mediaConstraints);	
}
function offerfailed(){
	console.log("Creating offer failed...");
}
function sendOffer(sdp){
	peer.setLocalDescription(sdp);
    console.log("Sending: SDP");
    console.log(sdp);
    socket.emit('message',{check:sid});
    socket.json.send(sdp);
}
function createPeerConnection() {
    console.log("Creating peer connection");
    RTCPeerConnection = webkitRTCPeerConnection || mozRTCPeerConnection;
    var pc_config = {"iceServers":[]};
    try {
      peer = new RTCPeerConnection(pc_config);
    } catch (e) {
      console.log("Failed to create PeerConnection, exception: " + e.message);
    }
    // sending ice candidates to the other peer
    peer.onicecandidate = function (evt) {
      if (evt.candidate) {
        console.log('Sending ICE candidate...');
        console.log(evt.candidate);
        socket.json.send({type: "candidate",
        				  iid:instructor_id,
        			      sdpMLineIndex: evt.candidate.sdpMLineIndex,
        			      sdpMid: evt.candidate.sdpMid,
                          candidate: evt.candidate.candidate});
      } else {
        console.log("End of candidates.");
      }
    };
    
    peer.addEventListener("addstream", onRemoteStreamAdded, false);
    peer.addEventListener("removestream", onRemoteStreamRemoved, false);
    function onRemoteStreamAdded(event) {
      console.log("Added remote stream");
	   studentvid.src = window.URL.createObjectURL(event.stream);
    }
    function onRemoteStreamRemoved(event) {
      console.log("Remove remote stream");
    //  $.post("update.jsp",{id:sid,iid:instructor_id,val:0},function(data,status){});
      studentvid.src = "";
    }
	
  }
function disconnect_std(){
	var x=document.getElementById("sname").innerHTML;
	$.post("/AVCRIOA/updateRequests",{id:x,iid:instructor_id,val:0},function(data,status){
	});
	peer.close();
	peer=null;
	document.getElementById("sname").innerHTML="Disconnected";
	document.getElementById('disc').disabled=true;
	socket.emit('message',{type:'bye'+x+instructor_id});
}
var bool=false;
function onMessage(evt){
	if(evt.check==instructor_id){
		bool=true;
	}
	if (evt.type === 'answer' && bool) {
		try{
	      console.log('Received answer...');
	      console.log('Setting remote session description...' );
	      peer.setRemoteDescription(new RTCSessionDescription(evt));
		}catch(exception){console.log(exception);}
		finally{bool=false;}
	    } else if (evt.type === 'candidate'&& evt.iid===instructor_id) {
	      console.log('Received ICE candidate...');
	      try{
	      var candidate = new RTCIceCandidate({sdpMLineIndex:evt.sdpMLineIndex, sdpMid:evt.sdpMid, candidate:evt.candidate});
	      console.log(candidate);
	      peer.addIceCandidate(candidate);
	      }catch(Exception){console.log(Exception);}
	    }
		else if(evt.type !=null){
			if(evt.gbr==='hide' && evt.iid===instructor_id){
				
				$.post("/AVCRIOA/updateRequests",{id:evt.type,iid:instructor_id,val:evt.val},function(data,status){
				});
				}
			else if(evt.iid===instructor_id){
			$.post("/AVCRIOA/updateRequests",{id:evt.type,iid:instructor_id,val:evt.val},function(data,status){
			});
				}
			}
}