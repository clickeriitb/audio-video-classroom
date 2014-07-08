/*
 * References:
 * http://www.html5rocks.com/en/tutorials/webrtc/basics/
 */
//Initialize variables
var session_name= "";
var defaultChannel= null;
var localstream = null;
var localvideo = null;
var temp = null;
var sid = null;
var connectedTo = null;
var peer = null;
var config = null;
var privateChannel = null;
var pr_config = null;
var disconn_btn=null;
var isStudent=false;
//goBack
function goback(){
	document.location.href="/AVCRIOA/instructor/index.jsp";
}
//get broadcaster id
session_name = document.getElementById('i_id').value;

if(session_name=='student')
{
	isStudent=true;
}

if(isStudent)
{
	window.onload = reload;
	disconn_btn=document.getElementById('disconnect');
}

//get local video element
localvideo = document.getElementById('inst_video');

//define mediaConstraints
var mediaConstraints = {'mandatory': {
	'OfferToReceiveAudio':true, 
	'OfferToReceiveVideo':true }};

//function to open new socket connection
//(specifically new channel on the same socket)
function openSignallingChannel(config)
{
	var SIGNALLING_SERVER = "http://10.2.80.46:2345/";
	var channel;
	if(config)
	{
		channel = config.channel;
	}
	else
	{
		channel = 'default-channel';
	}
	console.log("1. channel: "+ channel);
	io.connect(SIGNALLING_SERVER).emit('new-channel', {channel:channel});
	var socket = io.connect(SIGNALLING_SERVER+channel);
	socket.channel = channel;
	console.log("2. socket.channel: "+ socket.channel);
	return socket;
}

//initially open default socket for 
//every user to get connected to server
defaultChannel = openSignallingChannel();
//defaultChannel.emit('message',{channel:'123'});

//on receiving messages from server
defaultChannel.on('message',onMessage);

function getRandom()
{
	return Math.floor(Math.random()*1000);
}

//to get the names of the broadcaster who are online..
function reload()
{
	$.post('/AVCRIOA/broadcastLoad',{session:session_name},function(data,status){
		document.querySelector("#right").innerHTML=data;
	});
	setTimeout(reload,1000);
}

//function to create Peer connection
function createPeerConnection()
{
	console.log("Creating Peer Connection...");
	RTCPeerConnection = webkitRTCPeerConnection || mozRTCPeerConnection;
	var pc_config = {"iceServers":[]};
	try
	{
		peer = new RTCPeerConnection(pc_config);
	}
	catch(e)
	{
		console.log("Failed to create peer connection, exception: "+e.message);
	}
	
	//sending ice candidate to other peers...
	peer.onicecandidate = function(evt)
	{
		if(evt.candidate)
		{
			console.log("Sending ice candidate....");
			console.log(evt.candidate);
			privateChannel.json.send({type:'candidate',
									candidate:evt.candidate.candidate});
		}
		else
		{
			console.log("End of candidates...");
		}
	};
	
	if(!isStudent)
	{
		console.log("Adding local stream..");
		console.log("localstream: "+ localstream);
		peer.addStream(localstream);
	}
	else
	{
		peer.addEventListener("addstream",onRemoteStreamAdded,false);
		peer.addEventListener("removestream",onRemoteStreamRemoved,false);
		function onRemoteStreamAdded(event)
		{
			console.log("Adding Remote Stream..");
			localvideo.src = window.URL.createObjectURL(event.stream);
			console.log("Remote Stream: "+event.stream);
		}
		function onRemoteStreamRemoved(event)
		{
			console.log("Remove Remote Stream..");
			event.stream.stop();
			localvideo.src = "";
		}
	}
}

//send offer sdp
function sendOffer(sdp)
{
	peer.setLocalDescription(sdp);
	console.log("Sending sdp..");
	console.log(sdp);
	privateChannel.json.send(sdp);
	
}

//if sending offer sdp is failed
function offerfailed()
{
	console.log("Creating Offer failed..");
}

//function to send answer
function sendAnswer(sdp)
{
	console.log("Sending Answer..");
	peer.setLocalDescription(sdp);
	privateChannel.json.send(sdp);
}

//sending answer failed
function answerfailed()
{
	console.log("Creating answer failed..");
}

//on getting broadcasted message from server 
//on default channel, this function is executed.
function onMessage(evt)
{
	if(evt.session_id===session_name)
	{
		console.log("Socket channel: "+evt.channel);
		
		if(privateChannel!=null)
		{
			if(privateChannel.channel!=evt.channel){
				console.log("Unequal channels...");
				pr_config = {channel:evt.channel};
				privateChannel = openSignallingChannel(pr_config);
				privateChannel.on('message',onMessage1);
				//console.log('Broadcaster: Creating Peer Connection...');
				
				createPeerConnection();
				console.log("creating offer...");
				peer.createOffer(sendOffer,offerfailed,mediaConstraints);
			}
			else
			{
				console.log("Equal channels...");
				//peer.createOffer(sendOffer,offerfailed,mediaConstraints);
			}
			
		}
		else
		{
			pr_config = {channel:evt.channel};
			privateChannel = openSignallingChannel(pr_config);
			privateChannel.on('message',onMessage1);
			createPeerConnection();
			console.log("creating offer...");
			peer.createOffer(sendOffer,offerfailed,mediaConstraints);
		}
		
	}
}

//on getting broadcasted message from server 
//on private channel, this function is executed.
function onMessage1(evt)
{
	console.log("Hello.. in message1()");
	if(evt.type==='offer')
	{
		try
		{
			console.log("Offer Received...");
			peer.setRemoteDescription(new RTCSessionDescription(evt));
			console.log("Creating Answer");
			peer.createAnswer(sendAnswer,answerfailed,mediaConstraints);
		}
		catch(e)
		{
			console.log("Offer not received, Exception: "+e.message);
		}
		
	}
	else if(evt.type==='answer')
	{	
		try
		{
			console.log("Received answer..");
			console.log("Set Remote Description..");
			peer.setRemoteDescription(new RTCSessionDescription(evt));
		}
		catch(e)
		{
			console.log("Answer not received, Exception: "+e.message);
		}
	}
	else if(evt.type==='candidate')
	{
		try
		{
			console.log("Received candidate..");
			var candidate = new RTCIceCandidate({candidate:evt.candidate});
			peer.addIceCandidate(candidate);
		}
		catch(e)
		{
			console.log("No candidate received, Exception: "+e.message);
		}
	}
	else if(evt.type==='bye'+session_name)
	{
		console.log("Bye bye Session name: "+session_name);
		privateChannel.disconnect();
		peer.close();
		peer = null;
		//localvideo.src="";
		if(peer==null)
			{
			console.log("Disconnected....");
			}
	
	}
}


//Broadcasting function: called by broadcaster
function Broadcast()
{
	
	//get broadcaster id
	session_name = document.getElementById('i_id').value;
	isBroadcaster = true;
	if(session_name.length<=0)
	{
		alert('please enter session name!!');
	}
	else
	{
		navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || window.navigator.mozGetUserMedia || navigator.msGetUserMedia;
		window.URL = window.URL || window.webkitURL;
		navigator.getUserMedia({video: true, audio: true},onGotSuccess, onError);
		function onGotSuccess(stream) 
		{
		    localstream = stream;
			localvideo.src = window.URL.createObjectURL(stream);
		    localvideo.play();
		    localvideo.muted=true;
		    while(!localstream){}
		    
		    $.post("/AVCRIOA/broadcastStore",{s_id:session_name,status:1},function(data,status){
		    	document.getElementById('status').innerHTML= data;
		    });	
		    console.log('3. session id:'+session_name);
		}
		function onError(err) {
	       
	        return;
	    }
	}
}



function DisCon()
{
	console.log("I m in disconnect button...");
	localstream.stop();
	localvideo.src = "";
	$.post('/AVCRIOA/broadcastUpdate',{session_id:session_name},function(data,status)
			{
				console.log("Disconnected..");
				document.getElementById("status").innerHTML=data;
				localvideo.src="Broadcaster disconnected..";
				reload();
			});
}

//join the room of broadcaster
function call(e)
{
	e.disabled=true;
	connectedTo = document.getElementById('conTo');
	temp = connectedTo.innerHTML;
	connectedTo.innerHTML = e.innerHTML;
	sid = connectedTo.innerHTML;
	
	//generate default id for each participant
	var userToken = getRandom();
	
	/*Make one disconnect button*/
	if(temp=="")
	{
		if(disconn_btn)
		{
			disconn_btn.style.visibility="visible";
		}
	}
	else
	{
		if(disconn_btn)
		{
			disconn_btn.style.visibility="hidden";
		}
	}
	//for switching between two broadcasters
	if(peer)
	{
		if(temp!=sid)
		{
			
		}
	
	}
	//send unique user token to the broadcaster
	console.log("Unique participant token: "+userToken);
	defaultChannel.emit('message', {channel:userToken, session_id:sid});
	
	/*create new channel(open new socket connection) which 
	  is used to exchange ICE candidate 
	  and establishing particular peer connection between 
	  broadcaster and this unique user.*/
	
	//send channel name to distinguish it from other channels
	config = {channel:userToken};	
	privateChannel = openSignallingChannel(config);
	privateChannel.on('message',onMessage1);
	createPeerConnection();
	//privateChannel.emit('message',{channel:userToken});
	
}

//for disconnecting a particular broadcaster
function discon_channel()
{
	console.log("session_name: "+sid);
	connectedTo.innerHTML='';
	privateChannel.emit('message',{type:'bye'+sid});
}
