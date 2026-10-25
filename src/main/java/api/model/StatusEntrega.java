package api.model;

// CAMADA: Model
// RESPONSABILIDADE: as situações possíveis de uma entrega.
// SITUAÇÃO: implementada.
public enum StatusEntrega {
    // A entrega saiu e o veículo está a caminho. É o único status que ainda pode mudar (R3).
    EM_TRANSITO,
    // O cliente recebeu.
    ENTREGUE,
    // A entrega foi desfeita e o produto voltou ao estoque (R4).
    CANCELADA
}
