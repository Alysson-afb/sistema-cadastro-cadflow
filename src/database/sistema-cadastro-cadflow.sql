DROP DATABASE IF EXISTS CadFlow;
CREATE DATABASE CadFlow;
USE CadFlow;

CREATE TABLE pessoa (
    pk_cod_pessoa INT PRIMARY KEY AUTO_INCREMENT,
    
    nome_completo VARCHAR(100) NOT NULL,
    nome_social VARCHAR(100),
    cpf CHAR(11) UNIQUE,
    data_nascimento DATE NOT NULL,
    sexo CHAR(1) NOT NULL, 
    cor VARCHAR(50) NOT NULL,
    
    nacionalidade VARCHAR(50) NOT NULL,
    naturalidade VARCHAR(50),
    estado_civil VARCHAR(50),
    profissao VARCHAR(50),
    escolaridade VARCHAR(50),
    
    telefone VARCHAR(20),
    endereco_atual VARCHAR(150),
    estado_uf CHAR(2),

    CONSTRAINT sexo_ck CHECK (sexo IN ('M','F','O'))
);

CREATE TABLE acolhido (
    fk_id_pessoa INT PRIMARY KEY, 
    
    registro_cartorio VARCHAR(100),
    medida_protetiva VARCHAR(255),

    historico_rua VARCHAR(255),
    info_saude VARCHAR(255),
    servicos_acessados VARCHAR(255),
    data_entrada DATE DEFAULT (CURRENT_DATE),
    data_desligamento DATE,
    avaliacao_interdisciplinar TEXT,
    
    FOREIGN KEY (fk_id_pessoa) REFERENCES pessoa(pk_cod_pessoa) ON DELETE CASCADE
);

CREATE TABLE familiar (
    pk_id_vinculo INT PRIMARY KEY AUTO_INCREMENT,
    fk_id_acolhido INT NOT NULL,
    fk_id_parente INT NOT NULL,
    
    parentesco VARCHAR(50) NOT NULL,
    ocupacao VARCHAR(50),
    
    FOREIGN KEY (fk_id_acolhido) REFERENCES acolhido(fk_id_pessoa) ON DELETE CASCADE,
    FOREIGN KEY (fk_id_parente) REFERENCES pessoa(pk_cod_pessoa) ON DELETE CASCADE
);

CREATE TABLE profissional (
    pk_cod_profissional INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(72) NOT NULL,
    cargo VARCHAR(50) NOT NULL
);

USE CadFlow;

ALTER TABLE pessoa ADD COLUMN ativo TINYINT DEFAULT 1;

ALTER TABLE acolhido ADD COLUMN responsavel_acolhimento VARCHAR(100);
ALTER TABLE acolhido ADD COLUMN contato_responsavel VARCHAR(50);
ALTER TABLE acolhido ADD COLUMN residia_com VARCHAR(100);
ALTER TABLE acolhido ADD COLUMN detalhes_acolhimento TEXT;
ALTER TABLE acolhido ADD COLUMN motivo_acolhimento TEXT;

ALTER TABLE acolhido ADD COLUMN plano_objetivo VARCHAR(255);
ALTER TABLE acolhido ADD COLUMN plano_acoes TEXT;
ALTER TABLE acolhido ADD COLUMN plano_responsaveis VARCHAR(150);
ALTER TABLE acolhido ADD COLUMN plano_prazo_inicio DATE;
ALTER TABLE acolhido ADD COLUMN plano_prazo_fim DATE;

CREATE TABLE configuracao (
    chave VARCHAR(50) PRIMARY KEY,
    valor VARCHAR(255)
);

INSERT INTO configuracao (chave, valor) VALUES ('capacidade_maxima', '20');

ALTER TABLE acolhido ADD COLUMN observacoes TEXT;