-- mysql workbench forward engineering

set @old_unique_checks=@@unique_checks, unique_checks=0;
set @old_foreign_key_checks=@@foreign_key_checks, foreign_key_checks=0;
set @old_sql_mode=@@sql_mode, sql_mode='only_full_group_by,strict_trans_tables,no_zero_in_date,no_zero_date,error_for_division_by_zero,no_engine_substitution';

-- -----------------------------------------------------
-- schema wanteddb
-- -----------------------------------------------------
drop schema if exists `wanted_db` ;

create schema if not exists `wanted_db` default character set utf8 ;
use `wanted_db` ;

-- -----------------------------------------------------
-- table `wanted_db`.`member`
-- -----------------------------------------------------
create table if not exists wanted_db.`member` (
    `id` bigint not null auto_increment,
    `email` varchar(255) not null,
    `username` varchar(16) not null,
    `created_at` timestamp null,
    primary key (`id`),
    unique index `email_unique` (`email` asc) visible,
    unique index `username_unique` (`username` asc) visible)
    engine = innodb;


-- -----------------------------------------------------
-- table `wanteddb`.`post`
-- -----------------------------------------------------
create table if not exists wanted_db.`post` (
    `id` bigint not null auto_increment,
    `title` varchar(100) not null,
    `author_id` bigint not null,
    `created_at` datetime null,
    `updated_at` datetime null,
    primary key (`id`),
    index `fk_post_member_idx` (`author_id` asc) visible,
    constraint `fk_post_member`
    foreign key (`author_id`) references `wanted_db`.`member` (`id`))
    engine = innodb
    default charset = utf8mb4
    collate = utf8mb4_unicode_ci;

-- -----------------------------------------------------
-- table `wanteddb`.`member_auth`
-- -----------------------------------------------------
create table if not exists wanted_db.`member_auth` (
    `id` bigint not null auto_increment,
    `member_id` bigint not null,
    `access_token` varchar(1000) not null,
    `refresh_token` varchar(1000) not null,
    `created_at` datetime null,
    `expired_at` datetime null,
    primary key (`id`),
    constraint `fk_memberauth_member1`
    foreign key (`member_id`)
        references wanted_db.`member` (`id`)
        on delete cascade )
    engine = innodb;

-- -----------------------------------------------------
-- Table `wanteddb`.`member_password`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wanted_db`.`member_password` (
    `id` BIGINT NOT NULL auto_increment,
    `member_id` BIGINT NOT NULL,
    `hash` VARCHAR(255) NOT NULL,
    `salt` binary(16) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_member_password_member1_idx` (`member_id` ASC) VISIBLE,
    CONSTRAINT `fk_member_password_member`
        FOREIGN KEY (`member_id`)
            REFERENCES `wanted_db`.`member` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- table `wanteddb`.`post_contents`
-- -----------------------------------------------------
create table if not exists `wanted_db`.`post_contents` (
    `id` bigint not null auto_increment,
    `content` text not null,
    `post_id` bigint not null,
    primary key (`id`),
    index `fk_postcontents_post_idx` (`post_id` asc) visible,
    constraint `fk_postcontents_post` foreign key (`post_id`)
      references `wanted_db`.`post` (`id`)
      on delete cascade )
    engine = innodb
    default charset = utf8mb4
    collate = utf8mb4_unicode_ci;

set sql_mode=@old_sql_mode;
set foreign_key_checks=@old_foreign_key_checks;
set unique_checks=@old_unique_checks;
