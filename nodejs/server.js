/********************Server for Signalling purpose **********/
//Creating http server
var server = require('http').createServer(handler);
var app = server.listen(1234, function() {
console.log("Server is running on port 1234");
});
// creating the socket server on the port
var io = require('socket.io').listen(app);
function handler(req,res){
	res.end("Connected");
}

io.sockets.on('connection', function(socket) {

    console.log(' Connection established.');

    // When a user sends a SDP message this function broadcasts it to all users in the room
    socket.on('message', function(message) {
        console.log(' Received Message, broadcasting: ' + message);
        socket.broadcast.emit('message', message);
    });

    // When the user hangs up this function broadcast disconnected signal to all users in the room
    socket.on('disconnect', function() {
        // closing user connection
        console.log(" Peer disconnected.");
        socket.broadcast.emit('user disconnected');
    });

});

/**************************Broadcast Server************************/
/*
var port = 2345; // use port:80 for non-localhost tests

var app_broadcast = require('express')(),
    broadcast_server = require('http').createServer(app_broadcast),
    io2 = require('socket.io').listen(broadcast_server);

broadcast_server.listen(port);
*/
var server2 = require('http').createServer();
var app2 = server2.listen(2345, function() {
console.log("Broadcast Server is running on port 2345");
});
// creating the socket server on the port
var io2 = require('socket.io').listen(app2);
io2.sockets.on('connection', function (socket) {
    if (!io2.connected) io2.connected = true;

    socket.on('new-channel', function (data) {
        onNewNamespace(data.channel);
    });
});

function onNewNamespace(channel) {
    io2.of('/' + channel).on('connection', function (socket) {
        if (io2.isConnected) {
            io2.isConnected = false;
            socket.emit('connect', true);
        }

        socket.on('message', function (data) {
		console.log("Channel no.:" + data.channel);
		console.log("Connection Established");
            	socket.broadcast.emit('message', data);
        });
    });
}


