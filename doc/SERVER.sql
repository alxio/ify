CREATE USER 'malinka'@'localhost' IDENTIFIED BY 'St3g0zaur';

GRANT ALL PRIVILEGES ON *.* TO 'malinka'@'localhost';

CREATE DATABASE IF NOT EXISTS `ify` DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci;

USE `ify`;

CREATE TABLE IF NOT EXISTS `grouppermissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `a` bit(1) NOT NULL,
  `d` bit(1) NOT NULL,
  `r` bit(1) NOT NULL,
  `x` bit(1) NOT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`name`,`username`),
  UNIQUE KEY `group_id` (`group_id`,`name`,`username`),
  UNIQUE KEY `name` (`name`,`username`),
  UNIQUE KEY `group_id_2` (`group_id`,`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci AUTO_INCREMENT=123 ;
 
CREATE TABLE IF NOT EXISTS `groups` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `name` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `name_2` (`name`,`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci AUTO_INCREMENT=95 ;
 
CREATE TABLE IF NOT EXISTS `parameters` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `booleanValue` bit(1) DEFAULT NULL,
  `device` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `doubleValue` double DEFAULT NULL,
  `integerValue` int(11) DEFAULT NULL,
  `lobValue` longblob,
  `name` varchar(25) COLLATE utf8_polish_ci NOT NULL,
  `recipe` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `stringValue` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `type` varchar(25) COLLATE utf8_polish_ci NOT NULL,
  `groupname` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_polish_ci DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1B57C1EADF8E90FE` (`username`,`user_id`),
  KEY `FK1B57C1EA329EA100` (`groupname`,`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci AUTO_INCREMENT=1 ;
 
CREATE TABLE IF NOT EXISTS `queue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data` longblob NOT NULL,
  `source_user_id` bigint(20) DEFAULT NULL,
  `target_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK66F19111934FF7A` (`target_user_id`),
  KEY `FK66F191126D4CC04` (`source_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci AUTO_INCREMENT=1 ;
 
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sys_created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `enabled` bit(1) NOT NULL,
  `firstName` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `lastName` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `rolesLob` longblob NOT NULL,
  `username` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `username_2` (`username`,`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci AUTO_INCREMENT=171 ;
 
ALTER TABLE `parameters`
  ADD CONSTRAINT `FK1B57C1EA329EA100` FOREIGN KEY (`groupname`, `group_id`) REFERENCES `groups` (`name`, `id`),
  ADD CONSTRAINT `FK1B57C1EADF8E90FE` FOREIGN KEY (`username`, `user_id`) REFERENCES `users` (`username`, `id`);
 
ALTER TABLE `queue`
  ADD CONSTRAINT `FK66F19111934FF7A` FOREIGN KEY (`target_user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FK66F191126D4CC04` FOREIGN KEY (`source_user_id`) REFERENCES `users` (`id`);
