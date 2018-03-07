-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 25, 2016 at 09:20 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dbtest`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_members`
--

CREATE TABLE IF NOT EXISTS `tbl_members` (
  `user_id` int(5) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `position` varchar(25) NOT NULL,
  `office` varchar(25) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `tbl_members`
--

INSERT INTO `tbl_members` (`user_id`, `first_name`, `last_name`, `email`, `position`, `office`) VALUES
(1, 'Ashton', 'Bradshaw', 'ashtonbrad@mail.com', 'Software Engineer', 'Florida'),
(2, 'Garrett', 'Winters', 'garrettwin@mail.com', 'Sales Assistant', 'Singapore'),
(3, 'Jackson', 'Silva', 'jacksilva@mail.com', 'Support Engineer', 'New York'),
(4, 'Jenette', 'Caldwell', 'jenettewell@mail.com', 'Director', 'London'),
(5, 'Rhona', 'Walton', 'rhonawalt@mail.com', 'System Architect', 'San Francisco');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
