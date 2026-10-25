package api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

// CAMADA: DTO
// RESPONSABILIDADE: o JSON que a API devolve para um veículo da frota.
// SITUAÇÃO: implementada.
@Schema(description = "Um veículo da frota")
public record VeiculoResponseDTO(

        @Schema(example = "1")
        Long id,

        @Schema(example = "Caminhão VW Delivery - ABC1D23")
        String descricao,

        @Schema(example = "Carlos Souza")
        String motorista,

        @Schema(example = "Criciúma - Tubarão - Laguna")
        String rota,

        @Schema(description = "DISPONIVEL ou EM_ROTA", example = "DISPONIVEL")
        String status

) {
}
