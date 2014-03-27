ALTER TABLE `kingsnthings`.`sent_message` 
ADD COLUMN `sent_message_id` INT NOT NULL AUTO_INCREMENT FIRST,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`sent_message_id`);


ALTER TABLE `kingsnthings`.`sent_message` 
ADD UNIQUE INDEX `ibindex_sent_message_un` (`message_id` ASC, `user_id` ASC);