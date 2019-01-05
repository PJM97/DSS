-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema DSS
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema DSS
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `DSS` DEFAULT CHARACTER SET utf8 ;
USE `DSS` ;

-- -----------------------------------------------------
-- Table `DSS`.`Cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Cliente` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `visivel` TINYINT NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DSS`.`Componente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Componente` (
  `nome` VARCHAR(45) NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  `designacao` VARCHAR(80) NOT NULL,
  `preco` DOUBLE NOT NULL,
  `visivel` TINYINT NOT NULL,
  `stock` INT NOT NULL,
  PRIMARY KEY (`nome`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DSS`.`Pacote`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Pacote` (
  `nomePacote` VARCHAR(45) NOT NULL,
  `preco` DOUBLE NOT NULL,
  `visivel` TINYINT NOT NULL,
  PRIMARY KEY (`nomePacote`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DSS`.`Configuracao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Configuracao` (
  `idConfiguracao` INT NOT NULL,
  `preco` DOUBLE NOT NULL,
  `estado` INT NOT NULL,
  `visivel` TINYINT NOT NULL,
  `Cliente_username` VARCHAR(45) NOT NULL,
  `sp` INT NOT NULL,
  PRIMARY KEY (`idConfiguracao`),
  INDEX `fk_Configuracao_Cliente1_idx` (`Cliente_username` ASC) VISIBLE,
  CONSTRAINT `fk_Configuracao_Cliente1`
    FOREIGN KEY (`Cliente_username`)
    REFERENCES `DSS`.`Cliente` (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DSS`.`Administrador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Administrador` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DSS`.`FuncionarioFabrica`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`FuncionarioFabrica` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `visivel` TINYINT NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DSS`.`FuncionarioStand`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`FuncionarioStand` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `visivel` TINYINT NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DSS`.`Obrigatorio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Obrigatorio` (
  `Componente_nome` VARCHAR(45) NOT NULL,
  `Componente_nome1` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Componente_nome`, `Componente_nome1`),
  INDEX `fk_Componente_has_Componente_Componente2_idx` (`Componente_nome1` ASC) VISIBLE,
  INDEX `fk_Componente_has_Componente_Componente1_idx` (`Componente_nome` ASC) VISIBLE,
  CONSTRAINT `fk_Componente_has_Componente_Componente1`
    FOREIGN KEY (`Componente_nome`)
    REFERENCES `DSS`.`Componente` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Componente_has_Componente_Componente2`
    FOREIGN KEY (`Componente_nome1`)
    REFERENCES `DSS`.`Componente` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DSS`.`Incompativel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Incompativel` (
  `Componente_nome` VARCHAR(45) NOT NULL,
  `Componente_nome1` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Componente_nome`, `Componente_nome1`),
  INDEX `fk_Componente_has_Componente1_Componente2_idx` (`Componente_nome1` ASC) VISIBLE,
  INDEX `fk_Componente_has_Componente1_Componente1_idx` (`Componente_nome` ASC) VISIBLE,
  CONSTRAINT `fk_Componente_has_Componente1_Componente1`
    FOREIGN KEY (`Componente_nome`)
    REFERENCES `DSS`.`Componente` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Componente_has_Componente1_Componente2`
    FOREIGN KEY (`Componente_nome1`)
    REFERENCES `DSS`.`Componente` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DSS`.`Configuracao_has_Componente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Configuracao_has_Componente` (
  `Configuracao_idConfiguracao` INT NOT NULL,
  `Componente_nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Configuracao_idConfiguracao`, `Componente_nome`),
  INDEX `fk_Configuracao_has_Componente_Componente1_idx` (`Componente_nome` ASC) VISIBLE,
  INDEX `fk_Configuracao_has_Componente_Configuracao1_idx` (`Configuracao_idConfiguracao` ASC) VISIBLE,
  CONSTRAINT `fk_Configuracao_has_Componente_Configuracao1`
    FOREIGN KEY (`Configuracao_idConfiguracao`)
    REFERENCES `DSS`.`Configuracao` (`idConfiguracao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Configuracao_has_Componente_Componente1`
    FOREIGN KEY (`Componente_nome`)
    REFERENCES `DSS`.`Componente` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DSS`.`Pacote_has_Componente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Pacote_has_Componente` (
  `Pacote_nomePacote` VARCHAR(45) NOT NULL,
  `Componente_nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Pacote_nomePacote`, `Componente_nome`),
  INDEX `fk_Pacote_has_Componente_Componente1_idx` (`Componente_nome` ASC) VISIBLE,
  INDEX `fk_Pacote_has_Componente_Pacote1_idx` (`Pacote_nomePacote` ASC) VISIBLE,
  CONSTRAINT `fk_Pacote_has_Componente_Pacote1`
    FOREIGN KEY (`Pacote_nomePacote`)
    REFERENCES `DSS`.`Pacote` (`nomePacote`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Pacote_has_Componente_Componente1`
    FOREIGN KEY (`Componente_nome`)
    REFERENCES `DSS`.`Componente` (`nome`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DSS`.`Configuracao_has_Pacote`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Configuracao_has_Pacote` (
  `Configuracao_idConfiguracao` INT NOT NULL,
  `Pacote_nomePacote` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Configuracao_idConfiguracao`, `Pacote_nomePacote`),
  INDEX `fk_Configuracao_has_Pacote_Pacote1_idx` (`Pacote_nomePacote` ASC) VISIBLE,
  INDEX `fk_Configuracao_has_Pacote_Configuracao1_idx` (`Configuracao_idConfiguracao` ASC) VISIBLE,
  CONSTRAINT `fk_Configuracao_has_Pacote_Configuracao1`
    FOREIGN KEY (`Configuracao_idConfiguracao`)
    REFERENCES `DSS`.`Configuracao` (`idConfiguracao`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Configuracao_has_Pacote_Pacote1`
    FOREIGN KEY (`Pacote_nomePacote`)
    REFERENCES `DSS`.`Pacote` (`nomePacote`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `DSS` ;

-- -----------------------------------------------------
-- procedure deleteDB
-- -----------------------------------------------------

DELIMITER $$
USE `DSS`$$
CREATE PROCEDURE `deleteDB` ()
BEGIN

delete from configuracao_has_pacote where configuracao_idconfiguracao > '0';
delete from configuracao_has_componente where configuracao_idconfiguracao > '0';
delete from configuracao where idConfiguracao > '0';

delete from pacote_has_componente 	where pacote_nomepacote > '0';
delete from pacote where nomepacote != ' ';

delete from obrigatorio where componente_nome != ' ';
delete from incompativel where componente_nome != ' ';
delete from componente where nome != ' ';

delete from cliente where username != ' ';
delete from funcionariostand where username != ' ';
delete from funcionariofabrica where username != ' ';
delete from administrador where username != ' ';

END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
