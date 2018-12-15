
-- drop table if exists "user_group";
-- drop table if exist "user";

CREATE TABLE IF NOT EXISTS user_group (
  id SERIAL,
  value VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "user" (
  id SERIAL,
  login VARCHAR(45) NOT NULL,
  password VARCHAR(45) NOT NULL,
  user_group INT NOT NULL,
  surname VARCHAR(45) NOT NULL,
  name VARCHAR(45) NOT NULL,
  patronymic VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
--   FOREIGN KEY (user_group)
--   REFERENCES user_group (id)
);

--   INDEX `user_group_idx` (`user_group` ASC),
--   UNIQUE INDEX `login_UNIQUE` (`login` ASC),
--   CONSTRAINT `user_group`
--   FOREIGN KEY (`user_group`)
--   REFERENCES `servicedb`.`user_group` (`id`)
--   ON DELETE NO ACTION
--   ON UPDATE NO ACTION)
--   ENGINE = InnoDB;