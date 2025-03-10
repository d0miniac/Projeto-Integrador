-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema armariodigital
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `armariodigital` ;

-- -----------------------------------------------------
-- Schema armariodigital
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `armariodigital` DEFAULT CHARACTER SET utf8 ;
USE `armariodigital` ;

-- -----------------------------------------------------
-- Table `armariodigital`.`funcionarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `armariodigital`.`funcionarios` (
  `idFuncionario` INT(11) NOT NULL AUTO_INCREMENT,
  `CPF` VARCHAR(14) NOT NULL,
  `NomeFuncionario` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(30) NOT NULL,
  `Senha` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`idFuncionario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `armariodigital`.`clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `armariodigital`.`clientes` (
  `idCliente` INT(11) NOT NULL AUTO_INCREMENT,
  `Nome_Clientes` VARCHAR(45) NOT NULL,
  `CPF` VARCHAR(30) NOT NULL,
  `Email` VARCHAR(40) NOT NULL,
  `Telefone` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`idCliente`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `armariodigital`.`vendas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `armariodigital`.`vendas` (
  `idVenda` INT(11) NOT NULL AUTO_INCREMENT,
  `Hora_venda` TIME(6) NOT NULL,
  `Quantidade` INT(11) NOT NULL,
  `MTD_Pagamento` VARCHAR(30) NOT NULL,
  `Funcionario_idFuncionario` INT(11) NOT NULL,
  `Cliente_idCliente` INT(11) NOT NULL,
  PRIMARY KEY (`idVenda`),
 FOREIGN KEY (`Funcionario_idFuncionario`) REFERENCES `funcionarios` (`idFuncionario`),
  FOREIGN KEY (`Cliente_idCliente`) REFERENCES `clientes` (`idCliente`)
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `armariodigital`.`fornecedores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `armariodigital`.`fornecedores` (
  `idFornecedor` INT(11) NOT NULL AUTO_INCREMENT,
  `Email` VARCHAR(40) NOT NULL,
  `Nome_Fornecedor` VARCHAR(45) NOT NULL,
  `Nome_Ctt` VARCHAR(45) NOT NULL,
  `Telefone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idFornecedor`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `armariodigital`.`produtos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `armariodigital`.`produtos` (
  `idProduto` INT(11) NOT NULL AUTO_INCREMENT,
  `Tamanho` VARCHAR(10) NOT NULL,
  `Categoria` VARCHAR(30) NOT NULL,
  `Preco` DECIMAL(30,0) NOT NULL,
  `QT_Estoque` INT(11) NOT NULL,
  `Cor` VARCHAR(30) NOT NULL,
  `Marca` VARCHAR(30) NOT NULL,
  `idFornecedor` INT(11) NOT NULL,
  `Fornecedor_idFornecedor` INT(11) NOT NULL,
  PRIMARY KEY (`idProduto`),
 FOREIGN KEY (`idFornecedor`) REFERENCES `fornecedores` (`idFornecedor`)
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `armariodigital`.`carrinho`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `armariodigital`.`carrinho` (
  `idCarrinho` INT(11) NOT NULL AUTO_INCREMENT,
  `Venda_idVenda` INT(11) NOT NULL,
  `Produto_idProduto` INT(11) NOT NULL,
  `Quantidade` INT(11) NOT NULL,
  PRIMARY KEY (`idCarrinho`),
  
    FOREIGN KEY (`Venda_idVenda`) REFERENCES `vendas` (`idVenda`),
  FOREIGN KEY (`Produto_idProduto`) REFERENCES `produtos` (`idProduto`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
