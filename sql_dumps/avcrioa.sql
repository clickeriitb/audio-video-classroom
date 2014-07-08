-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jul 02, 2013 at 05:59 PM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `avcrioa`
--

-- --------------------------------------------------------

--
-- Table structure for table `audio`
--

CREATE TABLE IF NOT EXISTS `audio` (
  `sno` int(11) NOT NULL AUTO_INCREMENT,
  `student` varchar(20) NOT NULL,
  `course` varchar(10) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `file` mediumblob NOT NULL,
  `duration` float NOT NULL,
  PRIMARY KEY (`sno`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `audio`
--

INSERT INTO `audio` (`sno`, `student`, `course`, `time`, `file`, `duration`) VALUES

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE IF NOT EXISTS `course` (
  `courseID` varchar(20) NOT NULL,
  `courseName` varchar(50) NOT NULL,
  `courseDesc` varchar(100) NOT NULL,
  PRIMARY KEY (`courseID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `instructor`
--

CREATE TABLE IF NOT EXISTS `instructor` (
  `instructorID` varchar(20) NOT NULL,
  `instructorName` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`instructorID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `instructor`
--

INSERT INTO `instructor` (`instructorID`, `instructorName`, `password`) VALUES
('instructo1', 'instructor1', 'instructor1'),
('instructor', 'instructor', 'instructor'),
('instructor2', 'instructor2', 'instructor2');

-- --------------------------------------------------------

--
-- Table structure for table `instructorBroadcast`
--

CREATE TABLE IF NOT EXISTS `instructorBroadcast` (
  `id` varchar(20) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `timeStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `instructorBroadcast`
--

INSERT INTO `instructorBroadcast` (`id`, `status`, `timeStamp`) VALUES
('rajavel', 0, '2013-06-25 17:25:38'),
('rajavel', 0, '2013-06-25 17:26:37'),
('rajavel', 0, '2013-06-25 17:27:22'),
('rajavel', 0, '2013-06-25 17:31:14'),
('rajavel', 0, '2013-06-25 17:31:23'),
('rajavel', 0, '2013-06-25 17:31:31'),
('rajavel', 0, '2013-06-27 17:23:45'),
('rajavel', 0, '2013-06-28 07:02:15'),
('instructor', 0, '2013-06-28 10:12:39'),
('instructor', 0, '2013-06-29 05:59:47'),
('instructor', 0, '2013-06-29 07:08:47'),
('instructor', 0, '2013-07-01 16:38:02'),
('instructor', 0, '2013-07-01 16:40:40'),
('instructor', 0, '2013-07-02 05:56:58'),
('instructor', 0, '2013-07-02 05:58:08'),
('instructor', 0, '2013-07-02 05:59:21'),
('instructor', 0, '2013-07-02 06:56:20');

-- --------------------------------------------------------

--
-- Table structure for table `instructorcourse`
--

CREATE TABLE IF NOT EXISTS `instructorcourse` (
  `instructorID` varchar(20) NOT NULL,
  `courseID` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `instructorcourse`
--

INSERT INTO `instructorcourse` (`instructorID`, `courseID`) VALUES
('instructor', 'CS101'),
('instructor1', 'CS103'),
('instructo2', 'CS102');

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE IF NOT EXISTS `questions` (
  `sno` int(11) NOT NULL AUTO_INCREMENT,
  `studentID` varchar(20) NOT NULL,
  `instructorID` varchar(20) NOT NULL,
  `question` varchar(1000) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `timeStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sno`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=47 ;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`sno`, `studentID`, `instructorID`, `question`, `status`, `timeStamp`) VALUES
(39, '3401', 'rajavel', 'This is text question from 3401', 1, '2013-06-27 18:25:30'),
(40, '1386', 'instructor', 'What is webrtc?\n', 1, '2013-06-28 08:58:41'),
(41, '1386', 'instructor', 'Webrtc?\n', 0, '2013-06-28 10:05:20'),
(43, '3401', 'instructor', 'What is AVCRIOA??', 0, '2013-07-01 16:50:44'),
(44, '3429', 'instructor', 'What is broadcasting?', 0, '2013-07-01 16:50:42'),
(45, '3401', 'instructor', 'Which protocol is used for live streaming in webrtc?', 1, '2013-07-02 05:55:33'),
(46, '3401', 'instructor', 'Avcrioa', 0, '2013-07-02 06:52:59');

-- --------------------------------------------------------

--
-- Table structure for table `requests`
--

CREATE TABLE IF NOT EXISTS `requests` (
  `studentID` varchar(20) NOT NULL,
  `courseID` varchar(20) NOT NULL,
  `requestStatus` tinyint(1) NOT NULL DEFAULT '1',
  `timeStamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `requests`
--

INSERT INTO `requests` (`studentID`, `courseID`, `requestStatus`, `timeStamp`) VALUES
('rv', 'CS101', 0, '2013-06-24 16:18:48'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3290', 'CS101', 0, '2013-07-02 05:50:47'),
('3429', 'CS101', 0, '2013-07-01 16:21:38'),
('3429', 'CS101', 0, '2013-07-01 16:22:56'),
('3290', 'CS101', 0, '2013-07-02 05:50:47'),
('3290', 'CS101', 0, '2013-07-02 05:50:47'),
('3429', 'CS101', 0, '2013-07-01 16:32:49'),
('3290', 'CS101', 0, '2013-07-02 05:50:47'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3290', 'CS101', 0, '2013-07-02 05:50:47'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:22:37'),
('3401', 'CS101', 0, '2013-07-02 06:51:40'),
('3290', 'CS101', 0, '2013-07-02 06:52:09'),
('1387', 'CS101', 0, '2013-07-02 15:58:13');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `studentID` varchar(20) NOT NULL,
  `studentName` varchar(50) NOT NULL,
  `macAddress` varchar(25) NOT NULL,
  `courseID` varchar(20) NOT NULL,
  PRIMARY KEY (`studentID`,`macAddress`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`studentID`, `studentName`, `macAddress`, `courseID`) VALUES
('1111', 'emulator', '00:92:cc:00:4f:05', 'CS101'),
('1386', 'ashwini', '00:92:cf:00:10:d2', 'CS101'),
('1387', 'Ravi', '00:92:c5:00:a9:e0', 'CS101'),
('1399', '', '00:92:c6:00:07:f8', ''),
('3290', 'student3290', '00:92:c9:00:66:04', 'CS101'),
('3382', 'student3382', '00:92:c3:d7:4d:3e', 'CS101'),
('3401', 'student3401', '00:92:c9:00:6c:a9', 'CS101'),
('3429', 'student3429', '00:0a:eb:69:ba:e5', 'CS102');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;