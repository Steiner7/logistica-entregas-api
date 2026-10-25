package api.model;

// CAMADA: Model
// RESPONSABILIDADE: as situações possíveis de um veículo da frota.
// SITUAÇÃO: implementada.
public enum StatusVeiculo {
    // Parado no pátio, pode receber uma entrega.
    DISPONIVEL,
    // Levando uma entrega; só volta a DISPONIVEL quando ela é concluída ou cancelada.
    EM_ROTA
}
