-- Entregas rastreadas (antiga tabela rastreamentoentregas).
-- Cada entrega leva uma quantidade de um produto, num veículo da frota.
-- status: EM_TRANSITO, ENTREGUE ou CANCELADA. data_conclusao só é preenchida quando sai de EM_TRANSITO.
CREATE TABLE entregas (
    id             BIGSERIAL PRIMARY KEY,
    cliente        VARCHAR(50)  NOT NULL,
    destino        VARCHAR(100) NOT NULL,
    quantidade     INTEGER      NOT NULL CHECK (quantidade > 0),
    status         VARCHAR(20)  NOT NULL,
    previsao       DATE         NOT NULL,
    data_conclusao DATE,
    produto_id     BIGINT       NOT NULL REFERENCES produtos (id),
    veiculo_id     BIGINT       NOT NULL REFERENCES veiculos (id)
);
