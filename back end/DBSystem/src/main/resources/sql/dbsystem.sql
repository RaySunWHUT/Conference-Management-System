# Host: localhost  (Version 5.7.27)
# Date: 2019-12-26 17:31:29
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "building"
#

DROP TABLE IF EXISTS `building`;
CREATE TABLE `building` (
  `bId` int(11) NOT NULL AUTO_INCREMENT,
  `buildingName` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`bId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

#
# Data for table "building"
#

INSERT INTO `building` VALUES (1,'主楼'),(2,'航海楼'),(3,'东配楼'),(4,'西配楼'),(5,'小白楼');

#
# Structure for table "role"
#

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleId` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

#
# Data for table "role"
#

INSERT INTO `role` VALUES (1,'user'),(2,'admin');

#
# Structure for table "room"
#

DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `roomId` int(11) NOT NULL DEFAULT '0',
  `roomName` varchar(30) NOT NULL DEFAULT '',
  `bId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`roomId`),
  KEY `bId` (`bId`),
  KEY `roomName` (`roomName`),
  CONSTRAINT `room_ibfk_1` FOREIGN KEY (`bId`) REFERENCES `building` (`bId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

#
# Data for table "room"
#

INSERT INTO `room` VALUES (1,'1-302',1),(2,'1-304',1),(3,'1-301',1),(4,'2-101',2),(5,'2-103',2),(6,'2-107',2),(7,'3-203',3),(8,'3-207',3),(10,'3-408',3),(11,'3-309',3),(12,'3-412',3),(13,'4-307',4),(14,'4-417',4),(15,'4-503',4),(16,'4-512',4),(17,'5-301',5),(18,'5-303',5),(19,'5-401',5),(20,'5-309',5);

#
# Structure for table "summary"
#

DROP TABLE IF EXISTS `summary`;
CREATE TABLE `summary` (
  `sumId` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(30) NOT NULL DEFAULT '',
  `content` varchar(255) NOT NULL DEFAULT '',
  `updateTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`sumId`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4;

#
# Data for table "summary"
#

INSERT INTO `summary` VALUES (2,'result','Hello world.','2019-12-25 19:52:06'),(3,'sum3','This is the first content.','2019-12-25 19:22:07'),(4,'sum45','This is the first content 15886.','2019-12-26 14:34:52'),(28,'sum+','this is hhhhhh!!!','2019-12-26 14:29:56'),(31,'Hello','this is a good news!','2019-12-26 14:52:15'),(32,'Hello','APEC conference.','2019-12-26 16:42:41'),(33,'纪要1','内容1','2019-12-26 16:56:16');

#
# Structure for table "user"
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) NOT NULL DEFAULT '',
  `userAccount` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(20) NOT NULL DEFAULT '',
  `roleId` int(11) NOT NULL DEFAULT '0',
  `email` varchar(25) DEFAULT '',
  PRIMARY KEY (`userId`),
  KEY `roleId` (`roleId`),
  KEY `userAccount` (`userAccount`),
  CONSTRAINT `roleId` FOREIGN KEY (`roleId`) REFERENCES `role` (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

#
# Data for table "user"
#

INSERT INTO `user` VALUES (1,'Sun','275518','666',2,''),(2,'Ray','275519','000',1,'');

#
# Structure for table "conference"
#

DROP TABLE IF EXISTS `conference`;
CREATE TABLE `conference` (
  `confId` int(11) NOT NULL AUTO_INCREMENT,
  `confName` varchar(30) NOT NULL DEFAULT '',
  `startTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `endTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `roomName` varchar(30) NOT NULL DEFAULT '0',
  `state` int(2) NOT NULL DEFAULT '0' COMMENT '1: 正在进行; 2:已结束',
  `publisher` varchar(20) NOT NULL DEFAULT '0' COMMENT '发布人账号',
  `sumId` int(11) DEFAULT NULL,
  PRIMARY KEY (`confId`),
  KEY `publisher` (`publisher`),
  KEY `roomName` (`roomName`),
  KEY `sumId` (`sumId`),
  CONSTRAINT `conference_ibfk_2` FOREIGN KEY (`publisher`) REFERENCES `user` (`userAccount`),
  CONSTRAINT `conference_ibfk_4` FOREIGN KEY (`roomName`) REFERENCES `room` (`roomName`),
  CONSTRAINT `sumId` FOREIGN KEY (`sumId`) REFERENCES `summary` (`sumId`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;

#
# Data for table "conference"
#

INSERT INTO `conference` VALUES (5,'NIPS','2019-12-24 14:00:00','2019-12-24 16:00:00','2-103',0,'275518',4),(12,'ICML','2020-01-07 03:00:00','2020-01-07 07:00:00','1-304',1,'275518',NULL),(13,'CVPR','2019-12-30 03:00:00','2019-12-30 08:00:00','4-503',1,'275518',NULL),(15,'ECCV','2020-01-02 10:00:00','2020-01-02 15:00:00','5-309',1,'275518',NULL),(16,'ICCV','2020-01-01 03:00:00','2020-01-01 07:00:00','1-304',1,'275518',28),(20,'ICSCA','2019-08-21 09:00:00','2019-08-21 11:00:00','1-304',0,'275518',31),(21,'ICLR','2019-06-06 08:00:00','2019-06-06 10:00:00','1-304',0,'275518',32),(22,'ACM','2019-05-00 12:00:00','2019-05-01 15:00:00','1-304',0,'275518',NULL),(23,'MM','2019-05-02 12:00:00','2019-05-02 15:00:00','1-304',0,'275518',33),(24,'AEIC','2019-05-12 12:00:00','2019-05-12 15:00:00','1-304',0,'275518',NULL),(25,'ICSSS','2019-05-22 12:00:00','2019-05-22 15:00:00','1-304',0,'275518',NULL),(27,'Sun','2020-01-27 05:00:00','2020-01-27 17:00:00','3-408',1,'275518',NULL);
