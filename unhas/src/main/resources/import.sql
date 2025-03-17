-- Inserindo usuários
INSERT INTO usuario (ativo, password, role, username) VALUES (true, 'senha123', 'ADMIN', 'admin_user');
INSERT INTO usuario (ativo, password, role, username) VALUES (true, 'senha123', 'USER', 'cliente_user');
INSERT INTO usuario (ativo, password, role, username) VALUES (true, 'senha456', 'USER', 'cliente_user2');
INSERT INTO usuario (ativo, password, role, username) VALUES (true, 'senha789', 'USER', 'cliente_user3');
INSERT INTO usuario (ativo, password, role, username) VALUES (true, 'senha101', 'USER', 'cliente_user4');

-- Inserindo clientes
INSERT INTO cliente (data_cadastro, usuario_id, email, nome, telefone) VALUES (NOW(), 1, 'admin@email.com', 'Administrador', '11999999999');
INSERT INTO cliente (data_cadastro, usuario_id, email, nome, telefone) VALUES (NOW(), 2, 'cliente@email.com', 'Cliente Teste', '11988888888');
INSERT INTO cliente (data_cadastro, usuario_id, email, nome, telefone) VALUES (NOW(), 3, 'cliente2@email.com', 'Cliente Dois', '11977777777');
INSERT INTO cliente (data_cadastro, usuario_id, email, nome, telefone) VALUES (NOW(), 4, 'cliente3@email.com', 'Cliente Três', '11966666666');
INSERT INTO cliente (data_cadastro, usuario_id, email, nome, telefone) VALUES (NOW(), 5, 'cliente4@email.com', 'Cliente Quatro', '11955555555');

-- Inserindo serviços
INSERT INTO servico (ativo, duracao_minutos, preco, descricao, nome) VALUES (true, 60, 150.00, 'Corte de cabelo masculino', 'Corte Masculino');
INSERT INTO servico (ativo, duracao_minutos, preco, descricao, nome) VALUES (true, 90, 250.00, 'Pintura capilar', 'Coloração');
INSERT INTO servico (ativo, duracao_minutos, preco, descricao, nome) VALUES (true, 45, 100.00, 'Hidratação capilar', 'Hidratação');
INSERT INTO servico (ativo, duracao_minutos, preco, descricao, nome) VALUES (true, 30, 80.00, 'Barba completa', 'Barbearia');
INSERT INTO servico (ativo, duracao_minutos, preco, descricao, nome) VALUES (true, 120, 300.00, 'Alisamento capilar', 'Alisamento');

-- Inserindo agendamentos
INSERT INTO agendamento (valor_total, cliente_id, data_criacao, data_hora_inicio, data_hora_fim, servico_id, status) VALUES (150.00, 2, NOW(), '2025-03-18 10:00:00', '2025-03-18 11:00:00', 1, 'AGENDADO');
INSERT INTO agendamento (valor_total, cliente_id, data_criacao, data_hora_inicio, data_hora_fim, servico_id, status) VALUES (250.00, 3, NOW(), '2025-03-19 15:00:00', '2025-03-19 16:30:00', 2, 'CONFIRMADO');
INSERT INTO agendamento (valor_total, cliente_id, data_criacao, data_hora_inicio, data_hora_fim, servico_id, status) VALUES (100.00, 4, NOW(), '2025-03-20 09:00:00', '2025-03-20 09:45:00', 3, 'CONCLUIDO');
INSERT INTO agendamento (valor_total, cliente_id, data_criacao, data_hora_inicio, data_hora_fim, servico_id, status) VALUES (80.00, 5, NOW(), '2025-03-21 14:00:00', '2025-03-21 14:30:00', 4, 'CANCELADO');
INSERT INTO agendamento (valor_total, cliente_id, data_criacao, data_hora_inicio, data_hora_fim, servico_id, status) VALUES (300.00, 2, NOW(), '2025-03-22 17:00:00', '2025-03-22 19:00:00', 5, 'AGENDADO');
INSERT INTO agendamento (valor_total, cliente_id, data_criacao, data_hora_inicio, data_hora_fim, servico_id, status) VALUES (120.00, 2, NOW(), '2025-03-17 08:00:00', '2025-03-17 09:30:00', 1, 'AGENDADO');
INSERT INTO agendamento (valor_total, cliente_id, data_criacao, data_hora_inicio, data_hora_fim, servico_id, status) VALUES (200.00, 3, NOW(), '2025-03-17 10:00:00', '2025-03-17 11:00:00', 2, 'CONFIRMADO');
INSERT INTO agendamento (valor_total, cliente_id, data_criacao, data_hora_inicio, data_hora_fim, servico_id, status) VALUES (90.00, 3, NOW(), '2025-03-17 12:00:00', '2025-03-17 12:45:00', 3, 'CONCLUIDO');
INSERT INTO agendamento (valor_total, cliente_id, data_criacao, data_hora_inicio, data_hora_fim, servico_id, status) VALUES (150.00, 3, NOW(), '2025-03-17 14:00:00', '2025-03-17 15:00:00', 4, 'CANCELADO');
INSERT INTO agendamento (valor_total, cliente_id, data_criacao, data_hora_inicio, data_hora_fim, servico_id, status) VALUES (250.00, 3, NOW(), '2025-03-17 16:00:00', '2025-03-17 17:30:00', 5, 'AGENDADO');
INSERT INTO agendamento (valor_total, cliente_id, data_criacao, data_hora_inicio, data_hora_fim, servico_id, status) VALUES (220.00, 2, NOW(), '2025-03-18 09:00:00', '2025-03-18 10:30:00', 1, 'CONFIRMADO');
INSERT INTO agendamento (valor_total, cliente_id, data_criacao, data_hora_inicio, data_hora_fim, servico_id, status) VALUES (180.00, 2, NOW(), '2025-03-18 11:00:00', '2025-03-18 12:00:00', 2, 'CONCLUIDO');
INSERT INTO agendamento (valor_total, cliente_id, data_criacao, data_hora_inicio, data_hora_fim, servico_id, status) VALUES (95.00, 4, NOW(), '2025-03-18 13:00:00', '2025-03-18 13:45:00', 3, 'CANCELADO');
INSERT INTO agendamento (valor_total, cliente_id, data_criacao, data_hora_inicio, data_hora_fim, servico_id, status) VALUES (300.00, 4, NOW(), '2025-03-19 10:00:00', '2025-03-19 12:00:00', 4, 'AGENDADO');
INSERT INTO agendamento (valor_total, cliente_id, data_criacao, data_hora_inicio, data_hora_fim, servico_id, status) VALUES (130.00, 5, NOW(), '2025-03-19 14:00:00', '2025-03-19 14:45:00', 5, 'CONFIRMADO');


-- Inserindo horários disponíveis
INSERT INTO horario (disponivel, hora_inicio, hora_fim, dia_semana) VALUES (true, '09:00:00', '10:00:00', 'MONDAY');
INSERT INTO horario (disponivel, hora_inicio, hora_fim, dia_semana) VALUES (true, '14:00:00', '15:00:00', 'WEDNESDAY');
INSERT INTO horario (disponivel, hora_inicio, hora_fim, dia_semana) VALUES (true, '10:00:00', '11:00:00', 'FRIDAY');
INSERT INTO horario (disponivel, hora_inicio, hora_fim, dia_semana) VALUES (true, '16:00:00', '17:00:00', 'SATURDAY');
INSERT INTO horario (disponivel, hora_inicio, hora_fim, dia_semana) VALUES (true, '13:00:00', '14:00:00', 'SUNDAY');

-- Inserindo itens no portfólio
INSERT INTO portfolio_item (data_publicacao, servico_id, descricao, imagem_url, titulo) VALUES (NOW(), 1, 'Corte moderno para homens', 'https://exemplo.com/corte.jpg', 'Corte Estiloso');
INSERT INTO portfolio_item (data_publicacao, servico_id, descricao, imagem_url, titulo) VALUES (NOW(), 2, 'Coloração personalizada', 'https://exemplo.com/coloracao.jpg', 'Nova Cor');
INSERT INTO portfolio_item (data_publicacao, servico_id, descricao, imagem_url, titulo) VALUES (NOW(), 3, 'Hidratação profunda', 'https://exemplo.com/hidratacao.jpg', 'Hidratação Perfeita');
INSERT INTO portfolio_item (data_publicacao, servico_id, descricao, imagem_url, titulo) VALUES (NOW(), 4, 'Barba bem feita', 'https://exemplo.com/barba.jpg', 'Barbearia Premium');
INSERT INTO portfolio_item (data_publicacao, servico_id, descricao, imagem_url, titulo) VALUES (NOW(), 5, 'Alisamento impecável', 'https://exemplo.com/alisamento.jpg', 'Alisamento Top');
