drop schema if exists `aqukanali`;
create schema `aqukanali`;
USE `aqukanali`;

DROP TABLE IF EXISTS `Intention`;
DROP TABLE IF EXISTS `IntentionContent`;
DROP TABLE IF EXISTS `IntentionCompletedContent`;

create table `IntentionContent` (
	`IntentionId` int not null references intention.intenitonid,
    `isCompleted` bool not null,
    `IntentionContent` varchar(255) not null
);

CREATE TABLE `Intention` (
	`theDate` date not null,
	`IntentionId` int not null references IntentionContent(IntentionId),
	PRIMARY KEY (`theDate`)
);