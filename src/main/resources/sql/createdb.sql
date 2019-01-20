-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema repaircenter
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `repaircenter` ;

-- -----------------------------------------------------
-- Schema repaircenter
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `repaircenter` DEFAULT CHARACTER SET utf8 ;
USE `repaircenter` ;

-- -----------------------------------------------------
-- Table `repaircenter`.`user_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repaircenter`.`user_group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `value_UNIQUE` (`value` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `repaircenter`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repaircenter`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `user_group` INT NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `patronymic` VARCHAR(45) NOT NULL,
  `phone_number` VARCHAR(10) NULL,
  `email` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  INDEX `user_group_idx` (`user_group` ASC),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  CONSTRAINT `user_group`
    FOREIGN KEY (`user_group`)
    REFERENCES `repaircenter`.`user_group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `repaircenter`.`names`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repaircenter`.`names` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`value` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'имена пользователей';


-- -----------------------------------------------------
-- Table `repaircenter`.`patronymics`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repaircenter`.`patronymics` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `value_UNIQUE` (`value` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'отчества';


-- -----------------------------------------------------
-- Table `repaircenter`.`surnames`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repaircenter`.`surnames` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `value_UNIQUE` (`value` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'фамилии тут';


-- -----------------------------------------------------
-- Table `repaircenter`.`owner`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repaircenter`.`owner` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `surname_id` INT NOT NULL,
  `name_id` INT NOT NULL,
  `patronymic_id` INT NOT NULL,
  `phone_number` VARCHAR(15) NOT NULL,
  `email` VARCHAR(100) NULL,
  PRIMARY KEY (`id`),
  INDEX `owner_name_idx` (`name_id` ASC),
  INDEX `owner_patronymic_idx` (`patronymic_id` ASC),
  INDEX `owner_surname_idx` (`surname_id` ASC),
  CONSTRAINT `owner_name`
    FOREIGN KEY (`name_id`)
    REFERENCES `repaircenter`.`names` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `owner_patronymic`
    FOREIGN KEY (`patronymic_id`)
    REFERENCES `repaircenter`.`patronymics` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `owner_surname`
    FOREIGN KEY (`surname_id`)
    REFERENCES `repaircenter`.`surnames` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'информация о пользователе';


-- -----------------------------------------------------
-- Table `repaircenter`.`brand`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repaircenter`.`brand` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `value_UNIQUE` (`value` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `repaircenter`.`type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repaircenter`.`type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `value_UNIQUE` (`value` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'тип устройства';


-- -----------------------------------------------------
-- Table `repaircenter`.`model`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repaircenter`.`model` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `value_UNIQUE` (`value` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'таблица для хранения модели устройства';


-- -----------------------------------------------------
-- Table `repaircenter`.`defect`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repaircenter`.`defect` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `description_UNIQUE` (`value` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'неисправности';


-- -----------------------------------------------------
-- Table `repaircenter`.`status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repaircenter`.`status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `value_UNIQUE` (`value` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'этап ремонта';


-- -----------------------------------------------------
-- Table `repaircenter`.`repair`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repaircenter`.`repair` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `acceptor_id` INT NOT NULL,
  `master_id` INT NOT NULL,
  `master_comments` VARCHAR(1024) NULL,
  `diagnostic_result` VARCHAR(1024) NULL,
  `repair_result` VARCHAR(450) NULL,
  `status_id` INT NOT NULL,
  `date_of_receipt` VARCHAR(50) NULL,
  `time_of_receipt` VARCHAR(50) NULL,
  `date_of_give_out` VARCHAR(45) NULL,
  `time_of_give_out` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  INDEX `repair_status_idx` (`status_id` ASC),
  INDEX `repair_acceptor_idx` (`acceptor_id` ASC),
  INDEX `repair_master_idx` (`master_id` ASC),
  CONSTRAINT `repair_status`
    FOREIGN KEY (`status_id`)
    REFERENCES `repaircenter`.`status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `repair_acceptor`
    FOREIGN KEY (`acceptor_id`)
    REFERENCES `repaircenter`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `repair_master`
    FOREIGN KEY (`master_id`)
    REFERENCES `repaircenter`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'таблица для результатов диагностики-ремонта ';


-- -----------------------------------------------------
-- Table `repaircenter`.`completeness`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repaircenter`.`completeness` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `value_UNIQUE` (`value` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'комплектация устройства';


-- -----------------------------------------------------
-- Table `repaircenter`.`appearance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repaircenter`.`appearance` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `value` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `value_UNIQUE` (`value` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'внешний вид устройства';


-- -----------------------------------------------------
-- Table `repaircenter`.`device`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `repaircenter`.`device` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type_id` INT NOT NULL,
  `brand_id` INT NOT NULL,
  `model_id` INT NOT NULL,
  `serial_number` VARCHAR(45) NULL,
  `defect_id` INT NOT NULL,
  `owner_id` INT NOT NULL,
  `repair_id` INT NOT NULL,
  `completeness_id` INT NOT NULL,
  `appearance_id` INT NOT NULL,
  `note` VARCHAR(2000) NULL,
  PRIMARY KEY (`id`),
  INDEX `owner_idx` (`owner_id` ASC),
  INDEX `brand_idx` (`brand_id` ASC),
  INDEX `type_idx` (`type_id` ASC),
  INDEX `model_idx` (`model_id` ASC),
  INDEX `defect_idx` (`defect_id` ASC),
  INDEX `device_repair_idx` (`repair_id` ASC),
  INDEX `device_completeness_idx` (`completeness_id` ASC),
  INDEX `device_appearance_idx` (`appearance_id` ASC),
  CONSTRAINT `device_owner`
    FOREIGN KEY (`owner_id`)
    REFERENCES `repaircenter`.`owner` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `device_brand`
    FOREIGN KEY (`brand_id`)
    REFERENCES `repaircenter`.`brand` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `device_type`
    FOREIGN KEY (`type_id`)
    REFERENCES `repaircenter`.`type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `device_model`
    FOREIGN KEY (`model_id`)
    REFERENCES `repaircenter`.`model` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `device_defect`
    FOREIGN KEY (`defect_id`)
    REFERENCES `repaircenter`.`defect` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `device_repair`
    FOREIGN KEY (`repair_id`)
    REFERENCES `repaircenter`.`repair` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `device_completeness`
    FOREIGN KEY (`completeness_id`)
    REFERENCES `repaircenter`.`completeness` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `device_appearance`
    FOREIGN KEY (`appearance_id`)
    REFERENCES `repaircenter`.`appearance` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `repaircenter`.`user_group`
-- -----------------------------------------------------
START TRANSACTION;
USE `repaircenter`;
INSERT INTO `repaircenter`.`user_group` (`id`, `value`) VALUES (1, 'Администратор');
INSERT INTO `repaircenter`.`user_group` (`id`, `value`) VALUES (2, 'Менеджер');
INSERT INTO `repaircenter`.`user_group` (`id`, `value`) VALUES (3, 'Мастер');
INSERT INTO `repaircenter`.`user_group` (`id`, `value`) VALUES (4, 'Приемщик');
INSERT INTO `repaircenter`.`user_group` (`id`, `value`) VALUES (5, 'Уволен');

COMMIT;


-- -----------------------------------------------------
-- Data for table `repaircenter`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `repaircenter`;
INSERT INTO `repaircenter`.`user` (`id`, `login`, `password`, `user_group`, `surname`, `name`, `patronymic`, `phone_number`, `email`) VALUES (1, 'admin', md5('admin'), 1, 'Administrator', 'Admin', 'Adminovich', NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `repaircenter`.`status`
-- -----------------------------------------------------
START TRANSACTION;
USE `repaircenter`;
INSERT INTO `repaircenter`.`status` (`id`, `value`) VALUES (1, 'Принято');
INSERT INTO `repaircenter`.`status` (`id`, `value`) VALUES (2, 'Диагностика');
INSERT INTO `repaircenter`.`status` (`id`, `value`) VALUES (3, 'Ожидание комплектующих');
INSERT INTO `repaircenter`.`status` (`id`, `value`) VALUES (4, 'Ремонт');
INSERT INTO `repaircenter`.`status` (`id`, `value`) VALUES (5, 'Выдано');

COMMIT;