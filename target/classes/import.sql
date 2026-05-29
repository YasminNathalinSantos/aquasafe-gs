-- Pessoas
INSERT INTO tb_pessoa (nome, cpf, data_nascimento, email_pessoal, logradouro, numero, bairro, cep) VALUES ('Admin AquaSafe', '12345678909', DATE '1990-01-15', 'admin@aquasafe.com', 'Av. Paulista', '1000', 'Bela Vista', '01310-100');
INSERT INTO tb_pessoa (nome, cpf, data_nascimento, email_pessoal, logradouro, numero, bairro, cep) VALUES ('Operador Campo', '98765432100', DATE '1995-06-20', 'operador@aquasafe.com', 'Rua das Flores', '200', 'Centro', '04001-000');

-- Usuarios (senha: senha123)
INSERT INTO tb_usuario (fk_pessoa, rm, senha, permissao, data_criacao, status) VALUES (1, 'RM561365', '$2a$12$4VjHmC2QCkawWt/yQnj7JuhW4blkPA1c8ZzzdSysEoblcD1oCCY2u', 'ADMIN', DATE '2026-01-01', 'ATIVO');
INSERT INTO tb_usuario (fk_pessoa, rm, senha, permissao, data_criacao, status) VALUES (2, 'RM000001', '$2a$12$4VjHmC2QCkawWt/yQnj7JuhW4blkPA1c8ZzzdSysEoblcD1oCCY2u', 'USER', DATE '2026-01-01', 'ATIVO');

-- Regioes Monitoradas
INSERT INTO tb_regiao (nome, cidade, estado, nivel_risco_atual, latitude, longitude) VALUES ('Zona Norte Ribeirinha', 'Sao Paulo', 'SP', 'BAIXO', -23.4896, -46.6258);
INSERT INTO tb_regiao (nome, cidade, estado, nivel_risco_atual, latitude, longitude) VALUES ('Vale do Paraiba Sul', 'Sao Jose dos Campos', 'SP', 'MEDIO', -23.1794, -45.8869);
INSERT INTO tb_regiao (nome, cidade, estado, nivel_risco_atual, latitude, longitude) VALUES ('Baixada Santista', 'Santos', 'SP', 'ALTO', -23.9608, -46.3336);
INSERT INTO tb_regiao (nome, cidade, estado, nivel_risco_atual, latitude, longitude) VALUES ('Regiao Metropolitana N.', 'Guarulhos', 'SP', 'BAIXO', -23.4543, -46.5333);

-- Sensores
INSERT INTO tb_sensor (codigo, tipo_sensor, status, fk_regiao) VALUES ('SNS-001', 'NIVEL_AGUA', 'ATIVO', 1);
INSERT INTO tb_sensor (codigo, tipo_sensor, status, fk_regiao) VALUES ('SNS-002', 'CHUVA', 'ATIVO', 1);
INSERT INTO tb_sensor (codigo, tipo_sensor, status, fk_regiao) VALUES ('SNS-003', 'NIVEL_AGUA', 'ATIVO', 2);
INSERT INTO tb_sensor (codigo, tipo_sensor, status, fk_regiao) VALUES ('SNS-004', 'NIVEL_AGUA', 'MANUTENCAO', 3);
INSERT INTO tb_sensor (codigo, tipo_sensor, status, fk_regiao) VALUES ('SNS-005', 'CHUVA', 'ATIVO', 3);

-- Leituras
INSERT INTO tb_leitura (fk_sensor, nivel_agua, volume_chuva, data_hora) VALUES (1, 35.0, 12.5, TIMESTAMP '2026-05-20 08:00:00');
INSERT INTO tb_leitura (fk_sensor, nivel_agua, volume_chuva, data_hora) VALUES (1, 55.0, 28.3, TIMESTAMP '2026-05-20 10:00:00');
INSERT INTO tb_leitura (fk_sensor, nivel_agua, volume_chuva, data_hora) VALUES (3, 72.0, 45.1, TIMESTAMP '2026-05-20 09:00:00');
INSERT INTO tb_leitura (fk_sensor, nivel_agua, volume_chuva, data_hora) VALUES (5, 88.5, 67.8, TIMESTAMP '2026-05-20 11:00:00');

-- Alertas
INSERT INTO tb_alerta (fk_regiao, tipo_alerta, nivel_risco, mensagem, data_hora, ativo) VALUES (3, 'ENCHENTE', 'ALTO', 'Nivel de agua acima de 70%. Risco alto na Baixada Santista.', TIMESTAMP '2026-05-20 11:05:00', 1);
INSERT INTO tb_alerta (fk_regiao, tipo_alerta, nivel_risco, mensagem, data_hora, ativo) VALUES (2, 'ALAGAMENTO', 'MEDIO', 'Volume de chuva elevado. Monitorar Vale do Paraiba.', TIMESTAMP '2026-05-20 09:30:00', 1);