CREATE DATABASE IF NOT EXISTS `armariodigital` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `armariodigital`;
-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: armariodigital
-- ------------------------------------------------------
-- Server version	8.0.40-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `carrinho`
--

DROP TABLE IF EXISTS `carrinho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carrinho` (
  `idCarrinho` int NOT NULL AUTO_INCREMENT,
  `Venda_idVenda` int NOT NULL,
  `Produto_idProduto` int NOT NULL,
  `Quantidade` int NOT NULL,
  PRIMARY KEY (`idCarrinho`),
  KEY `Vendas_idVendas` (`Venda_idVenda`),
  KEY `Produtos_idProdutos` (`Produto_idProduto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrinho`
--

LOCK TABLES `carrinho` WRITE;
/*!40000 ALTER TABLE `carrinho` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrinho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `idCliente` int NOT NULL AUTO_INCREMENT,
  `Nome_Clientes` varchar(45) NOT NULL,
  `Email` varchar(40) NOT NULL,
  `Telefone` varchar(30) NOT NULL,
  PRIMARY KEY (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornecedores`
--

DROP TABLE IF EXISTS `fornecedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fornecedores` (
  `idFornecedor` int NOT NULL AUTO_INCREMENT,
  `Email` varchar(40) NOT NULL,
  `Nome_Fornecedor` varchar(45) NOT NULL,
  `Nome_Ctt` varchar(45) NOT NULL,
  `Telefone` varchar(45) NOT NULL,
  PRIMARY KEY (`idFornecedor`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornecedores`
--

LOCK TABLES `fornecedores` WRITE;
/*!40000 ALTER TABLE `fornecedores` DISABLE KEYS */;
INSERT INTO `fornecedores` VALUES (1,'cleusafernandes@gmail.com','Bazar Dona Cleusa','Cleusa Fernandes','47 9999-1111');
/*!40000 ALTER TABLE `fornecedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funcionarios`
--

DROP TABLE IF EXISTS `funcionarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funcionarios` (
  `idFuncionario` int NOT NULL AUTO_INCREMENT,
  `CPF` varchar(30) NOT NULL,
  `NomeFuncionario` varchar(45) NOT NULL,
  `Email` varchar(30) NOT NULL,
  `Senha` varchar(15) NOT NULL,
  `Perfil` varchar(5) NOT NULL,
  PRIMARY KEY (`idFuncionario`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionarios`
--

LOCK TABLES `funcionarios` WRITE;
/*!40000 ALTER TABLE `funcionarios` DISABLE KEYS */;
INSERT INTO `funcionarios` VALUES (2,'123.456.789-01','Isaque Padilha','a@a','123','Admin'),(3,'123.456.789-02','Algum Nome','algum@gmail.com','123','Admin'),(4,'123.456.789-03','fulano','fulano@gmail.com','123','Comum');
/*!40000 ALTER TABLE `funcionarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produtos`
--

DROP TABLE IF EXISTS `produtos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produtos` (
  `idProduto` int NOT NULL AUTO_INCREMENT,
  `Tamanho` varchar(10) NOT NULL,
  `Categoria` varchar(30) NOT NULL,
  `Preco` decimal(30,2) NOT NULL,
  `QT_Estoque` int NOT NULL,
  `Cor` varchar(30) NOT NULL,
  `Marca` varchar(30) NOT NULL,
  `Fornecedor_idFornecedor` int NOT NULL,
  `Imagem` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`idProduto`),
  KEY `Fornecedores_idFornecedores` (`Fornecedor_idFornecedor`),
  CONSTRAINT `Fornecedor_idFornecedor` FOREIGN KEY (`Fornecedor_idFornecedor`) REFERENCES `fornecedores` (`idFornecedor`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produtos`
--

LOCK TABLES `produtos` WRITE;
/*!40000 ALTER TABLE `produtos` DISABLE KEYS */;
INSERT INTO `produtos` VALUES (8,'M','Blusa',120.00,94,'Amarelo','Nike',1,'ImagensProdutos/prod_1733917850653.png'),(9,'PP','Blusa',120.00,100,'Amarelo','Nike',1,'ImagensProdutos/prod_1733917920596.png'),(10,'G','Camisa',120.00,8,'Cinza','Adidas',1,NULL);
/*!40000 ALTER TABLE `produtos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendas`
--

DROP TABLE IF EXISTS `vendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendas` (
  `idVenda` int NOT NULL AUTO_INCREMENT,
  `Data_venda` date NOT NULL,
  `Total` int NOT NULL,
  `Funcionario_idFuncionario` int NOT NULL,
  `Hora_Venda` time NOT NULL,
  PRIMARY KEY (`idVenda`),
  KEY `Funcionarios_idFuncionarios` (`Funcionario_idFuncionario`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendas`
--

LOCK TABLES `vendas` WRITE;
/*!40000 ALTER TABLE `vendas` DISABLE KEYS */;
/*!40000 ALTER TABLE `vendas` ENABLE KEYS */;
UNLOCK TABLES;

-- Tabela nova para armazenar pagamentos
DROP TABLE IF EXISTS `pagamentos`;

CREATE TABLE `pagamentos` (
  `idPagamento` int NOT NULL AUTO_INCREMENT,
  `numeroCartao` varchar(16) NOT NULL,
  `nomeCartao` varchar(100) NOT NULL,
  `validade` varchar(5) NOT NULL,
  `cvv` varchar(3) NOT NULL,
  `dataPagamento` date NOT NULL,
  `valorTotal` decimal(10,2) NOT NULL,
  `idCliente` int NOT NULL,
  `idVenda` int DEFAULT NULL,
  PRIMARY KEY (`idPagamento`),
  KEY `fk_pagamentos_clientes` (`idCliente`),
  KEY `fk_pagamentos_vendas` (`idVenda`),
  CONSTRAINT `fk_pagamentos_clientes` FOREIGN KEY (`idCliente`) REFERENCES `clientes` (`idCliente`),
  CONSTRAINT `fk_pagamentos_vendas` FOREIGN KEY (`idVenda`) REFERENCES `vendas` (`idVenda`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*!40101 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-30 17:47:23