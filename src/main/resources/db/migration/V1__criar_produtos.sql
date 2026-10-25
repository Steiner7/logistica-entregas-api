-- Produtos guardados no estoque (antiga tabela controleestoque).
-- estoque_minimo é a reserva de segurança: nenhuma entrega pode deixar o estoque abaixo dele (R1).
CREATE TABLE produtos (
    id             BIGSERIAL PRIMARY KEY,
    nome           VARCHAR(50) NOT NULL,
    estoque_atual  INTEGER     NOT NULL CHECK (estoque_atual >= 0),
    estoque_minimo INTEGER     NOT NULL CHECK (estoque_minimo >= 0)
);
