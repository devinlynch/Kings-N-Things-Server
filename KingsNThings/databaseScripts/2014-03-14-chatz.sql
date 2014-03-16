CREATE TABLE `kingsnthings`.`game_chat` (
  `game_chat_id` INT NOT NULL,
  `game_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `message` TEXT NOT NULL,
  PRIMARY KEY (`game_chat_id`),
  INDEX `game_chat_ibfk_1_idx` (`game_id` ASC),
  INDEX `game_chat_ibfk2_idx` (`user_id` ASC),
  CONSTRAINT `game_chat_ibfk_1`
    FOREIGN KEY (`game_id`)
    REFERENCES `kingsnthings`.`game` (`game_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `game_chat_ibfk2`
    FOREIGN KEY (`user_id`)
    REFERENCES `kingsnthings`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `kingsnthings`.`game_chat` 
ADD COLUMN `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `message`;

ALTER TABLE `kingsnthings`.`sent_message` 
ADD COLUMN `queued` TINYINT NOT NULL DEFAULT 1 AFTER `order`;