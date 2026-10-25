package api.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

// CAMADA: DTO
// RESPONSABILIDADE: o JSON que a API devolve para uma entrega. Produto e veículo saem como objeto.
// SITUAÇÃO: implementada.
@Schema(description = "Uma entrega rastreada")
public record EntregaResponseDTO(

        @Schema(example = "1")
        Long id,

        @Schema(example = "Mercado Bom Preço")
        String cliente,

        @Schema(example = "Rua Henrique Lage, 500 - Criciúma")
        String destino,

        @Schema(example = "30")
        Integer quantidade,

        @Schema(description = "EM_TRANSITO, ENTREGUE ou CANCELADA", example = "EM_TRANSITO")
        String status,

        @Schema(example = "2026-11-03")
        LocalDate previsao,

        @Schema(description = "Quando foi concluída ou cancelada; null enquanto está em trânsito", example = "null")
        LocalDate dataConclusao,

        ProdutoResponseDTO produto,

        VeiculoResponseDTO veiculo

) {
}
