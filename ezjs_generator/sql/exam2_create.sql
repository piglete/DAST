-- MySQL Script generated by MySQL Workbench
-- 01/18/17 21:48:02
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema exam2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema exam2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `exam2` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `exam2` ;

-- -----------------------------------------------------
-- Table `exam2`.`company`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exam2`.`company` ;

CREATE TABLE IF NOT EXISTS `exam2`.`company` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `logo` VARCHAR(255) NULL,
  `contact_name` VARCHAR(45) NULL COMMENT '联系人姓名',
  `contact_phone` VARCHAR(45) NULL,
  `contact_email` VARCHAR(45) NULL,
  `create_time` DATETIME NULL,
  `modify_time` DATETIME NULL,
  `using_type` INT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
COMMENT = '企业/学校';


-- -----------------------------------------------------
-- Table `exam2`.`course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exam2`.`course` ;

CREATE TABLE IF NOT EXISTS `exam2`.`course` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `share` TINYINT(1) NULL DEFAULT 0 COMMENT '是否共享',
  `share_alias` VARCHAR(45) NULL COMMENT '共享名称',
  `create_time` DATETIME NULL,
  `modify_time` DATETIME NULL,
  `using_type` INT NULL DEFAULT 1,
  `company_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_course_company1_idx` (`company_id` ASC),
  CONSTRAINT `fk_course_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `exam2`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '课程,暂时只支持数据库这一个课程';


-- -----------------------------------------------------
-- Table `exam2`.`subject`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exam2`.`subject` ;

CREATE TABLE IF NOT EXISTS `exam2`.`subject` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(225) NULL,
  `code` VARCHAR(225) NULL COMMENT '编码,#parentId#...#currentId',
  `using_type` INT NULL DEFAULT 1,
  `create_time` DATETIME NULL,
  `modify_time` DATETIME NULL,
  `subject_id` INT NULL,
  `course_id` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_subject_subject_idx` (`subject_id` ASC),
  INDEX `fk_subject_course1_idx` (`course_id` ASC),
  CONSTRAINT `fk_subject_subject`
    FOREIGN KEY (`subject_id`)
    REFERENCES `exam2`.`subject` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subject_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `exam2`.`course` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '专题类型, 章节/知识点等信息';

ALTER TABLE subject AUTO_INCREMENT=100;

-- -----------------------------------------------------
-- Table `exam2`.`question_stem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exam2`.`question_stem` ;

CREATE TABLE IF NOT EXISTS `exam2`.`question_stem` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `stem` TEXT NULL,
  `stem_type` INT NULL DEFAULT 0 COMMENT '0, text/plain\n1, text/html',
  `stem_image` VARCHAR(225) NULL,
  `subject_code` VARCHAR(225) NULL COMMENT '对应所在专题的编码',
  `question_type` INT NOT NULL COMMENT '题目类型：1单选，2多选，3判断, 4语句题',
  `difficulty` INT NOT NULL DEFAULT 6 COMMENT '难易度, 3易6中9难',
  `judgment_answer` TINYINT(1) NULL DEFAULT 0 COMMENT '只对判断题有效，表示判断题的答案.',
  `text_answer` TEXT NULL COMMENT '填空题和语句题(主观题)的答案信息',
  `create_time` DATETIME NULL,
  `modify_time` DATETIME NULL,
  `using_type` INT NULL DEFAULT 1,
  `subject_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_question_stem_subject1_idx` (`subject_id` ASC),
  CONSTRAINT `fk_question_stem_subject1`
    FOREIGN KEY (`subject_id`)
    REFERENCES `exam2`.`subject` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '题干信息';


-- -----------------------------------------------------
-- Table `exam2`.`question_choice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exam2`.`question_choice` ;

CREATE TABLE IF NOT EXISTS `exam2`.`question_choice` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` TEXT NULL,
  `content_type` INT NULL DEFAULT 0 COMMENT '0,text/plain\n1,text/html',
  `content_image` VARCHAR(225) NULL,
  `is_right` TINYINT(1) NULL,
  `create_time` DATETIME NULL,
  `modify_time` DATETIME NULL,
  `using_type` INT NULL DEFAULT 1,
  `question_stem_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_question_choice_question_stem1_idx` (`question_stem_id` ASC),
  CONSTRAINT `fk_question_choice_question_stem1`
    FOREIGN KEY (`question_stem_id`)
    REFERENCES `exam2`.`question_stem` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = '选项信息,主要针对单选题和判断题';


-- -----------------------------------------------------
-- Table `exam2`.`department`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exam2`.`department` ;

CREATE TABLE IF NOT EXISTS `exam2`.`department` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `create_time` DATETIME NULL,
  `modify_time` DATETIME NULL,
  `using_type` INT NULL DEFAULT 1,
  `department_id` INT NOT NULL,
  `company_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_department_department1_idx` (`department_id` ASC),
  INDEX `fk_department_company1_idx` (`company_id` ASC),
  CONSTRAINT `fk_department_department1`
    FOREIGN KEY (`department_id`)
    REFERENCES `exam2`.`department` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_department_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `exam2`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = '学院/专业/班级';


-- -----------------------------------------------------
-- Table `exam2`.`staff`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exam2`.`staff` ;

CREATE TABLE IF NOT EXISTS `exam2`.`staff` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `num` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `create_time` DATETIME NULL,
  `modify_time` DATETIME NULL,
  `using_type` INT NULL DEFAULT 1,
  `department_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_staff_department1_idx` (`department_id` ASC),
  CONSTRAINT `fk_staff_department1`
    FOREIGN KEY (`department_id`)
    REFERENCES `exam2`.`department` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = '学生信息';


-- -----------------------------------------------------
-- Table `exam2`.`leader`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exam2`.`leader` ;

CREATE TABLE IF NOT EXISTS `exam2`.`leader` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `num` VARCHAR(45) NULL COMMENT '教师职工号',
  `password` VARCHAR(45) NULL,
  `create_time` DATETIME NULL,
  `modify_time` DATETIME NULL,
  `using_type` INT NULL DEFAULT 1,
  `role` INT NULL DEFAULT 0 COMMENT '如果是管理员则能够访问所有专业已经学生的信息, 而且还能够管理管理教师',
  `company_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_leader_company1_idx` (`company_id` ASC),
  CONSTRAINT `fk_leader_company1`
    FOREIGN KEY (`company_id`)
    REFERENCES `exam2`.`company` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = '教师';


-- -----------------------------------------------------
-- Table `exam2`.`leader_has_department`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exam2`.`leader_has_department` ;

CREATE TABLE IF NOT EXISTS `exam2`.`leader_has_department` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `leader_id` INT NOT NULL,
  `department_id` INT NOT NULL,
  `using_type` INT NULL DEFAULT 1,
  `create_time` DATETIME NULL,
  `modify_time` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_leader_has_department_department1_idx` (`department_id` ASC),
  INDEX `fk_leader_has_department_leader1_idx` (`leader_id` ASC),
  CONSTRAINT `fk_leader_has_department_leader1`
    FOREIGN KEY (`leader_id`)
    REFERENCES `exam2`.`leader` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_leader_has_department_department1`
    FOREIGN KEY (`department_id`)
    REFERENCES `exam2`.`department` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = '给教师授权, 允许其访问该专业已经子专业的信息\n';


-- -----------------------------------------------------
-- Table `exam2`.`exam`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `exam2`.`exam` ;

CREATE TABLE IF NOT EXISTS `exam2`.`exam` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `create_time` DATETIME NULL,
  `modify_time` DATETIME NULL,
  `using_type` INT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
COMMENT = '考试信息, TODO';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
