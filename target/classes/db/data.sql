-- Inserção de produtos
INSERT INTO produto (id, descricao, preco_unitario) VALUES 
(10, 'Televisor', 2000.0),
(20, 'Geladeira', 3500.0),
(30, 'Fogao', 1200.0),
(40, 'Lava-louça', 1800.0),
(50, 'lava-roupas', 2870.0),
(60, 'Microondas', 900.0),
(70, 'Ar-Condicionado', 3200.0),
(80, 'Cafeteira', 550.0);

-- Inserção de itens de estoque
INSERT INTO item_de_estoque (id, produto_id, quantidade, estoque_min, estoque_max) VALUES 
(100, 10, 20, 5, 50),
(200, 20, 10, 5, 30),
(300, 40, 8, 5, 50),
(500, 60, 15, 5, 40),
(600, 70, 12, 3, 25),
(700, 80, 25, 5, 50);

-- Inserção de orçamentos
INSERT INTO orcamento 
(id, nome_cliente, pais, estado, custo_itens, imposto_estadual, imposto_federal, desconto, custo_consumidor, data_criacao, validade, efetivado) 
VALUES 
(1, 'Fulano', 'Brasil', 'RS', 7000.0, 700.0, 350.0, 0.0, 8050.0, '2025-06-10', '2025-06-20', TRUE),
(2, 'Ciclano', 'Brasil', 'SP', 4700.0, 564.0, 235.0, 200.0, 5299.0, '2025-06-15', '2025-06-25', FALSE),
(3, 'Maria', 'Brasil', 'RJ', 6500.0, 975.0, 325.0, 200.0, 7600.0, '2025-06-18', '2025-06-28', TRUE),
(4, 'Joao', 'Brasil', 'RS', 4100.0, 410.0, 205.0, 100.0, 4615.0, '2025-06-19', '2025-06-29', FALSE);

-- Inserção de itens de pedido
INSERT INTO item_pedido (id, produto_id, quantidade) VALUES 
(1000, 10, 2), 
(1001, 20, 1),
(1002, 40, 2),
(1003, 50, 1),
(1004, 10, 1),    -- Televisor para orçamento 3
(1005, 70, 2),    -- Ar-Condicionado para orçamento 3
(1006, 60, 3),    -- Microondas para orçamento 4
(1007, 80, 4);    -- Cafeteira para orçamento 4

-- Relacionamento entre orçamentos e itens
-- Orçamento 1 → Televisor e Geladeira
INSERT INTO orcamento_itens (orcamento_id, itens_id) VALUES 
(1, 1000), 
(1, 1001);

-- Orçamento 2 → Lava-louça e lava-roupas
INSERT INTO orcamento_itens (orcamento_id, itens_id) VALUES 
(2, 1002), 
(2, 1003);

-- Orçamento 3 → Televisor e Ar-Condicionado
INSERT INTO orcamento_itens (orcamento_id, itens_id) VALUES 
(3, 1004), 
(3, 1005);

-- Orçamento 4 → Microondas e Cafeteira
INSERT INTO orcamento_itens (orcamento_id, itens_id) VALUES 
(4, 1006), 
(4, 1007);
