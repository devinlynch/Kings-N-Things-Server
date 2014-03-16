CREATE TABLE `kingsnthings`.`sent_message` (
  `message_id` VARCHAR(150) NOT NULL,
  `sent_date` DATETIME NULL,
  `created_date` DATETIME NULL,
  `json` TEXT NULL,
  `type` VARCHAR(45) NULL,
  `user_id` INT NOT NULL,
  `game_id` INT NULL,
  PRIMARY KEY (`message_id`));

ALTER TABLE `kingsnthings`.`sent_message` 
ADD INDEX `ibfk_sent_message_game_idx` (`game_id` ASC),
ADD INDEX `ibfk_sent_message_user_idx` (`user_id` ASC);
ALTER TABLE `kingsnthings`.`sent_message` 
ADD CONSTRAINT `ibfk_sent_message_user`
  FOREIGN KEY (`user_id`)
  REFERENCES `kingsnthings`.`user` (`user_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `ibfk_sent_message_game`
  FOREIGN KEY (`game_id`)
  REFERENCES `kingsnthings`.`game` (`game_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `kingsnthings`.`game_user` 
DROP FOREIGN KEY `game_user_game_ibfk`;
ALTER TABLE `kingsnthings`.`game_user` 
CHANGE COLUMN `game_id` `game_id` INT(11) NULL ;
ALTER TABLE `kingsnthings`.`game_user` 
ADD CONSTRAINT `game_user_game_ibfk`
  FOREIGN KEY (`game_id`)
  REFERENCES `kingsnthings`.`game` (`game_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `kingsnthings`.`sent_message` 
DROP COLUMN `created_date`;

ALTER TABLE `kingsnthings`.`sent_message` 
ADD COLUMN `order` INT NULL AFTER `game_id`;
