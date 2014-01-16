CREATE SCHEMA `kingsnthings` ;

CREATE TABLE `kingsnthings`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `host_name` VARCHAR(45) NULL,
  `port` INT NULL,
  PRIMARY KEY (`user_id`));

CREATE TABLE `kingsnthings`.`game` (
  `game_id` INT NOT NULL AUTO_INCREMENT,
  `startedDate` DATETIME NULL,
  `state` BLOB NULL,
  PRIMARY KEY (`game_id`));

CREATE TABLE `kingsnthings`.`game_user` (
  `game_user_id` INT NOT NULL,
  `game_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`game_user_id`),
  UNIQUE INDEX `game_user_index_un_1` (`game_id` ASC, `user_id` ASC),
  INDEX `game_user_user_ibfk_idx` (`user_id` ASC),
  CONSTRAINT `game_user_user_ibfk`
    FOREIGN KEY (`user_id`)
    REFERENCES `kingsnthings`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `game_user_game_ibfk`
    FOREIGN KEY (`game_id`)
    REFERENCES `kingsnthings`.`game` (`game_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `kingsnthings`.`user` 
ADD COLUMN `created_datetime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `port`,
ADD COLUMN `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP AFTER `created_datetime`;
