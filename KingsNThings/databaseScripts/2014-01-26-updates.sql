ALTER TABLE `kingsnthings`.`game_user` 
CHANGE COLUMN `game_user_id` `game_user_id` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `kingsnthings`.`game` 
ADD COLUMN `created_from_game_lobby_id` VARCHAR(100) NULL AFTER `state`;

ALTER TABLE `kingsnthings`.`game` 
ADD COLUMN `active` TINYINT NOT NULL DEFAULT 0 AFTER `created_from_game_lobby_id`;
