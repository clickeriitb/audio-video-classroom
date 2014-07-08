README
======

Audio and video class room interaction on Aakash
================================================

Objective
---------

To design and implement interactive class room application for **Audio and Video Communication in Aakash Tablet** using HTML 5 and WebRTC.

Introduction
------------

Interactive class room environment between student and teacher for sharing their doubts / queries through audio and video in the tablets. Enable the feature of audio and video in a table using HTML 5 and WebRTC, and stream the media data from tablet to communicate with web server.

Steps to Run Project:

1)Import the AVCRIOA package into eclipse

2)Configure the tomcat server copy the jars from "libraries" folder

3)Run the nodejs server file which is in nodejs folder with the name "server.js"

	-->Navigate to the folder where server.js file placed

	--> type the command $node server.js

4)Change the Server IP in the following files with the ip on which the above file is running
	
	/AVCRIOA/student/broadcast.jsp		at line number 17

	/AVCRIOA/student/index.jsp		at line number 28

	/AVCRIOA/instructor/broadcast.jsp	at line number 28

	/AVCRIOA/instructor/index.jsp		at line number 24

	/AVCRIOA/js/student-connection.js	at line number 63

	/AVCRIOA/js/videoRequests.js		at line number 27

	/AVCRIOA/js/broadcast.js		at line number 49

5) Open "context.xml" file under /Web-Content/META-INF/" and change the user name and password and mysql driver port if needed 

6) Dump the "avcrioa.sql" into database with the name avcrioa

5) Save and Run the project.

