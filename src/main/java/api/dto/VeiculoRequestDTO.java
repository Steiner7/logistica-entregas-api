package api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// CAMADA: DTO
// RESPONSABILIDADE: o JSON que o cliente envia para cadastrar um veículo na frota. O status a API decide.
// SITUAÇÃO: implementada.
@Schema(description = "Dados para cadastrar um veículo na frota")
public record VeiculoRequestDTO(

        @Schema(description = "Modelo e placa do veículo", example = "Caminhão VW Delivery - ABC1D23")
        @NotBlank(message = "Descrição é obrigatória")
        @Size(max = 50, message = "Descrição tem no máximo 50 caracteres")
        String descricao,

        @Schema(description = "Nome do motorista", example = "Carlos Souza")
        @NotBlank(message = "Motorista é obrigatório")
        @Size(max = 50, message = "Motorista tem no máximo 50 caracteres")
        String motorista,

        @Schema(description = "Rota que o veículo atende", example = "Criciúma - Tubarão - Laguna")
        @NotBlank(message = "Rota é obrigatória")
        @Size(max = 100, message = "Rota tem no máximo 100 caracteres")
        String rota

) {
}
