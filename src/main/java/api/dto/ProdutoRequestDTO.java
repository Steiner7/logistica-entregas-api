package api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

// CAMADA: DTO
// RESPONSABILIDADE: o JSON que o cliente envia para cadastrar um produto no estoque.
// SITUAÇÃO: implementada.
@Schema(description = "Dados para cadastrar um produto no estoque")
public record ProdutoRequestDTO(

        @Schema(description = "Nome do produto", example = "Palete de água mineral 500 ml")
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 50, message = "Nome tem no máximo 50 caracteres")
        String nome,

        @Schema(description = "Quantidade em estoque agora", example = "120")
        @NotNull(message = "Estoque atual é obrigatório")
        @PositiveOrZero(message = "Estoque atual não pode ser negativo")
        Integer estoqueAtual,

        @Schema(description = "Reserva de segurança: nenhuma entrega pode deixar o estoque abaixo disto", example = "20")
        @NotNull(message = "Estoque mínimo é obrigatório")
        @PositiveOrZero(message = "Estoque mínimo não pode ser negativo")
        Integer estoqueMinimo

) {
}
