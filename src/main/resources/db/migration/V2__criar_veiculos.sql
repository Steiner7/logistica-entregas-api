-- Veículos da frota (antiga tabela controlefrota).
-- status: DISPONIVEL ou EM_ROTA. Quem muda o status é a API, nas regras R2 e R3.
CREATE TABLE veiculos (
    id        BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(50)  NOT NULL,
    motorista VARCHAR(50)  NOT NULL,
    rota      VARCHAR(100) NOT NULL,
    status    VARCHAR(20)  NOT NULL
);
