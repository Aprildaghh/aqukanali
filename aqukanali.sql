drop schema if exists `aqukanali`;
create schema `aqukanali`;
USE `aqukanali`;

DROP TABLE IF EXISTS `Intention`;
DROP TABLE IF EXISTS `IntentionContent`;
DROP TABLE IF EXISTS `IntentionCompletedContent`;

create table `IntentionContent` (
	`IntentionId` int not null,
    `IntentionContent` varchar(255) not null
);

create table `IntentionCompletedContent` (
	`IntentionCompletedId` int not null,
    `isCompleted` bool not null
);

CREATE TABLE `Intention` (
	`theDate` date not null,
	`IntentionId` int not null references IntentionContent(IntentionId),
	`IntentionCompletedId` int not null references IntentionCompletedContent(IntentionCompletedId),
	PRIMARY KEY (`theDate`)
);