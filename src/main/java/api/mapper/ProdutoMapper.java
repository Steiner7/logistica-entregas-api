package api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import api.dto.ProdutoRequestDTO;
import api.dto.ProdutoResponseDTO;
import api.model.Produto;

// CAMADA: Mapper
// RESPONSABILIDADE: converter ProdutoRequestDTO em Produto e Produto em ProdutoResponseDTO.
// SITUAÇÃO: implementada.
@Component
public class ProdutoMapper {

    public Produto toEntity(ProdutoRequestDTO dto) {
        return new Produto(dto.nome(), dto.estoqueAtual(), dto.estoqueMinimo());
    }

    public ProdutoResponseDTO toResponse(Produto produto) {
        return new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getEstoqueAtual(),
                produto.getEstoqueMinimo(), produto.getDisponivelParaEntrega(), produto.isAbaixoDoMinimo());
    }

    public List<ProdutoResponseDTO> toResponseList(List<Produto> produtos) {
        return produtos.stream().map(this::toResponse).toList();
    }
}
