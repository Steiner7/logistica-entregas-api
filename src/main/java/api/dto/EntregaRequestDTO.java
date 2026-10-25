package api.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// CAMADA: DTO
// RESPONSABILIDADE: o JSON que o cliente envia para registrar uma entrega. Produto e veículo entram pelo id.
// SITUAÇÃO: implementada.
@Schema(description = "Dados para registrar uma entrega")
public record EntregaRequestDTO(

        @Schema(description = "Quem recebe", example = "Mercado Bom Preço")
        @NotBlank(message = "Cliente é obrigatório")
        @Size(max = 50, message = "Cliente tem no máximo 50 caracteres")
        String cliente,

        @Schema(description = "Endereço de entrega", example = "Rua Henrique Lage, 500 - Criciúma")
        @NotBlank(message = "Destino é obrigatório")
        @Size(max = 100, message = "Destino tem no máximo 100 caracteres")
        String destino,

        @Schema(description = "Quantas unidades do produto saem do estoque", example = "30")
        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade precisa ser maior que zero")
        Integer quantidade,

        @Schema(description = "Data prevista de chegada (yyyy-MM-dd)", example = "2026-11-03")
        @NotNull(message = "Previsão é obrigatória")
        @FutureOrPresent(message = "Previsão não pode ser no passado")
        LocalDate previsao,

        @Schema(description = "Id do produto que sai do estoque", example = "1")
        @NotNull(message = "Produto é obrigatório")
        Long produtoId,

        @Schema(description = "Id do veículo que leva a entrega", example = "1")
        @NotNull(message = "Veículo é obrigatório")
        Long veiculoId

) {
}
