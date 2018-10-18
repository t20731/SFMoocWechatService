# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.11)
# Database: T2
# Generation Time: 2018-08-15 13:00:51 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table points
# ------------------------------------------------------------

DROP TABLE IF EXISTS `points`;

CREATE TABLE `points` (
  `user_id` varchar(100) NOT NULL,
  `session_id` int(11) NOT NULL,
  `checkin` int(11) DEFAULT '0',
  `host` int(11) DEFAULT '0',
  `exam` int(11) DEFAULT '0',
  `lottery` int(11) DEFAULT '0',
  PRIMARY KEY (`user_id`,`session_id`),
  KEY `session_id` (`session_id`),
  CONSTRAINT `points_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `points_ibfk_2` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table session
# ------------------------------------------------------------

DROP TABLE IF EXISTS `session`;

CREATE TABLE `session` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `season` varchar(10) NOT NULL,
  `owner` varchar(100) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `owner` (`owner`),
  CONSTRAINT `session_ibfk_1` FOREIGN KEY (`owner`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;

INSERT INTO `session` (`id`, `season`, `owner`, `date`)
VALUES
	(1,'S1','Vicky','2018-07-31'),
	(3,'S1','郁郁','2018-08-14'),
	(4,'S1','Kevin','2018-08-21'),
	(5,'S1','lingzhen','2018-08-28'),
	(6,'S1','Richard','2018-09-04'),
	(7,'S1','Shawn','2018-09-11'),
	(8,'S1','Sherry','2018-09-18'),
	(9,'S1','小寒','2018-09-25'),
	(10,'S1','Sally','2018-10-02'),
	(11,'S1','阿米','2018-10-09'),
	(12,'S1','Rita','2018-10-16');

/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `gender` smallint(2) NOT NULL,
  `status` smallint(2) NOT NULL,
  `avatarUrl` varchar(255) DEFAULT '',
  `initial` int(11) DEFAULT '0',
  `department` varchar(100) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `nickname`, `gender`, `status`, `avatarUrl`, `initial`, `department`)
VALUES
	('Kevin','Kevin',1,1,'',4,'UI Common'),
	('lingzhen','lingzhen',2,1,'',8,'UI Common'),
	('Richard','Richard',1,1,'',7,'UI Common'),
	('Rita','Rita',2,1,'',11,'UI Common'),
	('Sally','Sally',2,1,'',12,'UI Common'),
	('Shawn','Shawn',1,1,'',16,'UI Common'),
	('Sherry','Sherry',2,1,'',12,'UI Common'),
	('Vicky','Vicky',2,1,'',22,'UI Common'),
	('小寒','小寒',2,1,'',7,'UI Common'),
	('郁郁','郁郁',2,1,'',10,'UI Common'),
	('阿米','阿米',2,1,'',15,'UI Common');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
