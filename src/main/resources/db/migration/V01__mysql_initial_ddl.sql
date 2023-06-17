CREATE TABLE `natureza` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `informacao_tributaria` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` text NOT NULL,
  `natureza_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_informacao_tributaria_natureza_idx` (`natureza_id`),
  CONSTRAINT `fk_informacao_tributaria_natureza` FOREIGN KEY (`natureza_id`) REFERENCES `natureza` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `base_legal` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` text NOT NULL,
  `natureza_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_base_legal_natureza_idx` (`natureza_id`),
  CONSTRAINT `fk_base_legal_natureza` FOREIGN KEY (`natureza_id`) REFERENCES `natureza` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `retencao` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `aliquota` varchar(45) NOT NULL DEFAULT '0',
  `fato_gerador` varchar(45) NOT NULL,
  `observacao` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `tributo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `ordem_apresentacao` int DEFAULT '0',
  `informacao_tributaria_id` bigint DEFAULT NULL,
  `base_legal_id` bigint DEFAULT NULL,
  `retencao_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_tributo_informacao_tributaria_idx` (`informacao_tributaria_id`),
  KEY `fk_tributo_base_legal_idx` (`base_legal_id`),
  KEY `fk_tributo_retencao_idx` (`retencao_id`),
  CONSTRAINT `fk_tributo_base_legal` FOREIGN KEY (`base_legal_id`) REFERENCES `base_legal` (`id`),
  CONSTRAINT `fk_tributo_informacao_tributaria` FOREIGN KEY (`informacao_tributaria_id`) REFERENCES `informacao_tributaria` (`id`),
  CONSTRAINT `fk_tributo_retencao` FOREIGN KEY (`retencao_id`) REFERENCES `retencao` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `servico` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo` varchar(45) NOT NULL,
  `nome` text NOT NULL,
  `informacao_geral` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `servico_tributo` (
  `servico_id` bigint NOT NULL,
  `tributo_id` bigint NOT NULL,
  PRIMARY KEY (`servico_id`,`tributo_id`),
  KEY `fk_servico_tributo_tributo_id_idx` (`tributo_id`),
  CONSTRAINT `fk_servico_tributo_servico_id` FOREIGN KEY (`servico_id`) REFERENCES `servico` (`id`),
  CONSTRAINT `fk_servico_tributo_tributo_id` FOREIGN KEY (`tributo_id`) REFERENCES `tributo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
