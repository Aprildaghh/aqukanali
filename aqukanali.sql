drop schema if exists `aqukanali`;
create schema `aqukanali`;
USE `aqukanali`;

DROP TABLE IF EXISTS `Intention`;
DROP TABLE IF EXISTS `IntentionContent`;

CREATE TABLE `intention` (
	`id` int auto_increment not null,
	`intention_date` date not null,
	PRIMARY KEY (`id`)
);

create table `content` (
	`id` int not null auto_increment,
    `content_completion` bool not null,
    `intention_content` varchar(1024) not null,
    `intention_id` int not null,
    primary key(`id`),
    key `FK_INTENTION_idx` (`intention_id`),
    constraint `FK_INTENTION`
    foreign key (`intention_id`)
    references `intention`(`id`)
);

insert into aqukanali.intention (id, intention_date) values (1, date('2002-07-15'));
insert into aqukanali.content (id, content_completion, intention_content, intention_id) values (1, 1, "Born", 1);
insert into aqukanali.content (id, content_completion, intention_content, intention_id) values (2, 0, "Cry", 1);
