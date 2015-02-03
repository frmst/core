ALTER TABLE `security_users` MODIFY `username` VARCHAR(255) NOT NULL, MODIFY `password` VARCHAR(255) NOT NULL;
ALTER IGNORE TABLE `security_users` ADD UNIQUE (`username`);

