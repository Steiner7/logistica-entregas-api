package api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

// CAMADA: DTO
// RESPONSABILIDADE: o JSON que a API devolve para um produto, com os campos que ela calcula.
// SITUAÇÃO: implementada.
@Schema(description = "Um produto do estoque")
public record ProdutoResponseDTO(

        @Schema(example = "1")
        Long id,

        @Schema(example = "Palete de água mineral 500 ml")
        String nome,

        @Schema(example = "120")
        Integer estoqueAtual,

        @Schema(example = "20")
        Integer estoqueMinimo,

        @Schema(description = "Quanto ainda pode sair em entregas sem furar o estoque mínimo (calculado)", example = "100")
        Integer disponivelParaEntrega,

        @Schema(description = "true quando o estoque atual está abaixo do mínimo (calculado)", example = "false")
        boolean abaixoDoMinimo

) {
}
