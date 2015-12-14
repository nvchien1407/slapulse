SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `slapulse` ;
CREATE SCHEMA IF NOT EXISTS `slapulse` DEFAULT CHARACTER SET utf8 ;
USE `slapulse` ;

-- -----------------------------------------------------
-- Table `slapulse`.`Reference`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `slapulse`.`Reference` ;

CREATE  TABLE IF NOT EXISTS `slapulse`.`Reference` (
  `id` INT(11) NOT NULL ,
  `groupName` VARCHAR(100) NULL DEFAULT NULL ,
  `hashcodeValue` INT(11) NULL DEFAULT NULL ,
  `itemName` VARCHAR(250) NULL DEFAULT NULL ,
  `note` VARCHAR(255) NULL DEFAULT NULL ,
  `subGroupName` VARCHAR(100) NULL DEFAULT NULL ,
  `timezone` VARCHAR(50) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `slapulse`.`ServiceLevelAgreement`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `slapulse`.`ServiceLevelAgreement` ;

CREATE  TABLE IF NOT EXISTS `slapulse`.`ServiceLevelAgreement` (
  `id` INT(11) NOT NULL ,
  `tFormulaeDays` INT(11) NULL DEFAULT NULL ,
  `deadlineType` VARCHAR(255) NOT NULL ,
  `durationHours` INT(11) NULL DEFAULT NULL ,
  `durationMinutes` INT(11) NULL DEFAULT NULL ,
  `durationType` VARCHAR(255) NOT NULL ,
  `fixedTimeDaysToRoll` INT(11) NULL DEFAULT NULL ,
  `fixedTimeDeadline` DATETIME NULL DEFAULT NULL ,
  `fixedTimeThreshold` DATETIME NULL DEFAULT NULL ,
  `includeSpecialDays` VARCHAR(255) NULL DEFAULT NULL ,
  `name` VARCHAR(100) NOT NULL ,
  `notificationThreshold` BIGINT(20) NULL DEFAULT NULL ,
  `notificationThresholdDays` INT(11) NULL DEFAULT NULL ,
  `notificationThresholdHours` INT(11) NULL DEFAULT NULL ,
  `notificationThresholdMinutes` INT(11) NULL DEFAULT NULL ,
  `notifyDeadlineApproaching` BIT(1) NULL DEFAULT NULL ,
  `pauseThresholdHours` INT(11) NULL DEFAULT NULL ,
  `pauseThresholdMinutes` INT(11) NULL DEFAULT NULL ,
  `enableCutOffTime` BIT(1) NOT NULL ,
  `stopSlaWhenPaused` BIT(1) NOT NULL ,
  `workTime` VARCHAR(255) NOT NULL ,
  `calendarId` INT(11) NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `FKA0720CDBE8DB09CB`
    FOREIGN KEY (`calendarId` )
    REFERENCES `slapulse`.`Reference` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `FKA0720CDBE8DB09CB` ON `slapulse`.`ServiceLevelAgreement` (`calendarId` ASC) ;


-- -----------------------------------------------------
-- Table `slapulse`.`BusinessProcess`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `slapulse`.`BusinessProcess` ;

CREATE  TABLE IF NOT EXISTS `slapulse`.`BusinessProcess` (
  `id` INT(11) NOT NULL ,
  `description` VARCHAR(255) NOT NULL ,
  `nameId` INT(11) NOT NULL ,
  `serviceLevelAgreementId` INT(11) NULL DEFAULT NULL ,
  `typeId` INT(11) NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `FKA290766F908CE9D8`
    FOREIGN KEY (`nameId` )
    REFERENCES `slapulse`.`Reference` (`id` ),
  CONSTRAINT `FKA290766F66869F78`
    FOREIGN KEY (`serviceLevelAgreementId` )
    REFERENCES `slapulse`.`ServiceLevelAgreement` (`id` ),
  CONSTRAINT `FKA290766F9C1D8EC7`
    FOREIGN KEY (`typeId` )
    REFERENCES `slapulse`.`Reference` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `FKA290766F66869F78` ON `slapulse`.`BusinessProcess` (`serviceLevelAgreementId` ASC) ;

CREATE INDEX `FKA290766F9C1D8EC7` ON `slapulse`.`BusinessProcess` (`typeId` ASC) ;

CREATE INDEX `FKA290766F908CE9D8` ON `slapulse`.`BusinessProcess` (`nameId` ASC) ;


-- -----------------------------------------------------
-- Table `slapulse`.`DefaultDay`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `slapulse`.`DefaultDay` ;

CREATE  TABLE IF NOT EXISTS `slapulse`.`DefaultDay` (
  `id` INT(11) NOT NULL ,
  `defaultDayFlag` BIT(1) NOT NULL ,
  `nonWorkingDay` BIT(1) NULL DEFAULT NULL ,
  `regionId` INT(11) NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `FK9F44DDBBA6282001`
    FOREIGN KEY (`regionId` )
    REFERENCES `slapulse`.`Reference` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `FK9F44DDBBA6282001` ON `slapulse`.`DefaultDay` (`regionId` ASC) ;


-- -----------------------------------------------------
-- Table `slapulse`.`CalendarDate`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `slapulse`.`CalendarDate` ;

CREATE  TABLE IF NOT EXISTS `slapulse`.`CalendarDate` (
  `dateInfo` DATETIME NOT NULL ,
  `description` VARCHAR(255) NOT NULL ,
  `name` VARCHAR(255) NOT NULL ,
  `calendarDateId` INT(11) NOT NULL ,
  PRIMARY KEY (`calendarDateId`) ,
  CONSTRAINT `FKF4BEE42C10985C6D`
    FOREIGN KEY (`calendarDateId` )
    REFERENCES `slapulse`.`DefaultDay` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `FKF4BEE42C10985C6D` ON `slapulse`.`CalendarDate` (`calendarDateId` ASC) ;


-- -----------------------------------------------------
-- Table `slapulse`.`DefaultWeekDay`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `slapulse`.`DefaultWeekDay` ;

CREATE  TABLE IF NOT EXISTS `slapulse`.`DefaultWeekDay` (
  `day` VARCHAR(255) NOT NULL ,
  `defaultWeekDayId` INT(11) NOT NULL ,
  PRIMARY KEY (`defaultWeekDayId`) ,
  CONSTRAINT `FK84DB29A754F17388`
    FOREIGN KEY (`defaultWeekDayId` )
    REFERENCES `slapulse`.`DefaultDay` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `FK84DB29A754F17388` ON `slapulse`.`DefaultWeekDay` (`defaultWeekDayId` ASC) ;


-- -----------------------------------------------------
-- Table `slapulse`.`WorkHourRange`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `slapulse`.`WorkHourRange` ;

CREATE  TABLE IF NOT EXISTS `slapulse`.`WorkHourRange` (
  `id` INT(11) NOT NULL ,
  `fromTime` DATETIME NULL DEFAULT NULL ,
  `toTime` DATETIME NULL DEFAULT NULL ,
  `dayId` INT(11) NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `FK7F6C4D88E35C981D`
    FOREIGN KEY (`dayId` )
    REFERENCES `slapulse`.`DefaultDay` (`id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `FK7F6C4D88E35C981D` ON `slapulse`.`WorkHourRange` (`dayId` ASC) ;


-- -----------------------------------------------------
-- Table `slapulse`.`appuser`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `slapulse`.`appuser` ;

CREATE  TABLE IF NOT EXISTS `slapulse`.`appuser` (
  `username` VARCHAR(20) NOT NULL ,
  `confirmPassword` VARCHAR(255) NULL DEFAULT NULL ,
  `email` VARCHAR(50) NOT NULL ,
  `enabled` BIT(1) NULL DEFAULT NULL ,
  `first_name` VARCHAR(50) NOT NULL ,
  `last_name` VARCHAR(50) NOT NULL ,
  `password` VARCHAR(20) NOT NULL ,
  `password_hint` VARCHAR(255) NULL DEFAULT NULL ,
  `phone_number` VARCHAR(50) NULL DEFAULT NULL ,
  `version` INT(11) NULL DEFAULT NULL ,
  `website` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`username`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `slapulse`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `slapulse`.`role` ;

CREATE  TABLE IF NOT EXISTS `slapulse`.`role` (
  `name` VARCHAR(20) NOT NULL ,
  `description` VARCHAR(255) NULL DEFAULT NULL ,
  `version` INT(11) NULL DEFAULT NULL ,
  PRIMARY KEY (`name`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `slapulse`.`sequence_list`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `slapulse`.`sequence_list` ;

CREATE  TABLE IF NOT EXISTS `slapulse`.`sequence_list` (
  `name` VARCHAR(255) NULL DEFAULT NULL ,
  `next_value` INT(11) NULL DEFAULT NULL )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `slapulse`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `slapulse`.`user_role` ;

CREATE  TABLE IF NOT EXISTS `slapulse`.`user_role` (
  `appuser_username` VARCHAR(20) NOT NULL ,
  `roles_name` VARCHAR(20) NOT NULL ,
  `role_name` VARCHAR(20) NOT NULL ,
  `users_username` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`role_name`, `users_username`) ,
  CONSTRAINT `FK143BF46ADF4A1AF1`
    FOREIGN KEY (`users_username` )
    REFERENCES `slapulse`.`appuser` (`username` ),
  CONSTRAINT `FK143BF46A30F4C0D`
    FOREIGN KEY (`appuser_username` )
    REFERENCES `slapulse`.`appuser` (`username` ),
  CONSTRAINT `FK143BF46A4758D3C`
    FOREIGN KEY (`roles_name` )
    REFERENCES `slapulse`.`role` (`name` ),
  CONSTRAINT `FK143BF46A8A99C723`
    FOREIGN KEY (`role_name` )
    REFERENCES `slapulse`.`role` (`name` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `FK143BF46A30F4C0D` ON `slapulse`.`user_role` (`appuser_username` ASC) ;

CREATE INDEX `FK143BF46A8A99C723` ON `slapulse`.`user_role` (`role_name` ASC) ;

CREATE INDEX `FK143BF46A4758D3C` ON `slapulse`.`user_role` (`roles_name` ASC) ;

CREATE INDEX `FK143BF46ADF4A1AF1` ON `slapulse`.`user_role` (`users_username` ASC) ;


-- -----------------------------------------------------
-- Finish up and restore system defaults
-- -----------------------------------------------------
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
