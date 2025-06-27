-- Usa o banco existente
CREATE DATABASE IF NOT EXISTS `armariodigital` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `armariodigital`;

-- Tabela: fornecedores
CREATE TABLE IF NOT EXISTS `fornecedores` (
  `idFornecedor` INT NOT NULL AUTO_INCREMENT,
  `Email` VARCHAR(40) NOT NULL,
  `Nome_Fornecedor` VARCHAR(45) NOT NULL,
  `Nome_Ctt` VARCHAR(45) NOT NULL,
  `Telefone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idFornecedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT IGNORE INTO `fornecedores` (`idFornecedor`, `Email`, `Nome_Fornecedor`, `Nome_Ctt`, `Telefone`) VALUES
(1, 'cleusafernandes@gmail.com', 'Bazar Dona Cleusa', 'Cleusa Fernandes', '47 9999-1111');

-- Tabela: produtos
CREATE TABLE IF NOT EXISTS `produtos` (
  `idProduto` INT NOT NULL AUTO_INCREMENT,
  `Tamanho` VARCHAR(10) NOT NULL,
  `Categoria` VARCHAR(30) NOT NULL,
  `Preco` DECIMAL(30,2) NOT NULL,
  `QT_Estoque` INT NOT NULL,
  `Cor` VARCHAR(30) NOT NULL,
  `Marca` VARCHAR(30) NOT NULL,
  `Fornecedor_idFornecedor` INT NOT NULL,
  `Imagem` VARCHAR(120) DEFAULT NULL,
  PRIMARY KEY (`idProduto`),
  FOREIGN KEY (`Fornecedor_idFornecedor`) REFERENCES `fornecedores` (`idFornecedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT IGNORE INTO `produtos` (`idProduto`, `Tamanho`, `Categoria`, `Preco`, `QT_Estoque`, `Cor`, `Marca`, `Fornecedor_idFornecedor`, `Imagem`) VALUES
(8, 'M', 'Blusa', 120.00, 94, 'Amarelo', 'Nike', 1, 'ImagensProdutos/prod_1733917850653.png'),
(9, 'PP', 'Blusa', 120.00, 100, 'Amarelo', 'Nike', 1, 'ImagensProdutos/prod_1733917920596.png'),
(10, 'G', 'Camisa', 120.00, 8, 'Cinza', 'Adidas', 1, NULL);

-- Tabela: funcionarios
CREATE TABLE IF NOT EXISTS `funcionarios` (
  `idFuncionario` INT NOT NULL AUTO_INCREMENT,
  `CPF` VARCHAR(30) NOT NULL,
  `NomeFuncionario` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(30) NOT NULL,
  `Senha` VARCHAR(15) NOT NULL,
  `Perfil` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`idFuncionario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

INSERT IGNORE INTO `funcionarios` (`idFuncionario`, `CPF`, `NomeFuncionario`, `Email`, `Senha`, `Perfil`) VALUES
(2, '123.456.789-01', 'Isaque Padilha', 'a@a', '123', 'Admin'),
(3, '123.456.789-02', 'Algum Nome', 'algum@gmail.com', '123', 'Admin'),
(4, '123.456.789-03', 'fulano', 'fulano@gmail.com', '123', 'Comum');

-- Tabela: clientes
CREATE TABLE IF NOT EXISTS `clientes` (
  `idCliente` INT NOT NULL AUTO_INCREMENT,
  `Nome_Clientes` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(40) NOT NULL,
  `Telefone` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Tabela: vendas
CREATE TABLE IF NOT EXISTS `vendas` (
  `idVenda` INT NOT NULL AUTO_INCREMENT,
  `Data_venda` DATE NOT NULL,
  `Total` INT NOT NULL,
  `Funcionario_idFuncionario` INT NOT NULL,
  `Hora_Venda` TIME NOT NULL,
  PRIMARY KEY (`idVenda`),
  FOREIGN KEY (`Funcionario_idFuncionario`) REFERENCES `funcionarios` (`idFuncionario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Tabela: carrinho
CREATE TABLE IF NOT EXISTS `carrinho` (
  `idCarrinho` INT NOT NULL AUTO_INCREMENT,
  `Venda_idVenda` INT NOT NULL,
  `Produto_idProduto` INT NOT NULL,
  `Quantidade` INT NOT NULL,
  PRIMARY KEY (`idCarrinho`),
  FOREIGN KEY (`Venda_idVenda`) REFERENCES `vendas` (`idVenda`),
  FOREIGN KEY (`Produto_idProduto`) REFERENCES `produtos` (`idProduto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Tabela: pagamentos
CREATE TABLE IF NOT EXISTS `pagamentos` (
  `idPagamento` INT NOT NULL AUTO_INCREMENT,
  `numeroCartao` VARCHAR(16) NOT NULL,
  `nomeCartao` VARCHAR(100) NOT NULL,
  `validade` VARCHAR(5) NOT NULL,
  `cvv` VARCHAR(3) NOT NULL,
  `dataPagamento` DATE NOT NULL,
  `valorTotal` DECIMAL(10,2) NOT NULL,
  `idCliente` INT NOT NULL,
  `idVenda` INT DEFAULT NULL,
  PRIMARY KEY (`idPagamento`),
  FOREIGN KEY (`idCliente`) REFERENCES `clientes` (`idCliente`),
  FOREIGN KEY (`idVenda`) REFERENCES `vendas` (`idVenda`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- VIEW: Detalhes da Venda com JOIN entre carrinho e produtos
CREATE OR REPLACE VIEW vw_detalhes_venda AS
SELECT 
    c.Venda_idVenda       AS idVenda,
    c.Produto_idProduto   AS idProduto,
    c.Quantidade          AS quantidade,
    p.Preco               AS precoUnitario,
    (p.Preco * c.Quantidade) AS subtotal,
    p.Imagem              AS imagem,
    p.Marca               AS marca,
    p.Categoria           AS categoria,
    p.Tamanho             AS tamanho,
    p.Cor                 AS cor
FROM carrinho c
JOIN produtos p ON c.Produto_idProduto = p.idProduto;