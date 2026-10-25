package api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import api.dto.EntregaRequestDTO;
import api.dto.EntregaResponseDTO;
import api.model.Entrega;
import api.model.Produto;
import api.model.Veiculo;

// CAMADA: Mapper
// RESPONSABILIDADE: converter EntregaRequestDTO em Entrega e Entrega em EntregaResponseDTO,
//                   com o produto e o veículo dentro da resposta.
// SITUAÇÃO: implementada.
@Component
public class EntregaMapper {

    private final ProdutoMapper produtoMapper;
    private final VeiculoMapper veiculoMapper;

    public EntregaMapper(ProdutoMapper produtoMapper, VeiculoMapper veiculoMapper) {
        this.produtoMapper = produtoMapper;
        this.veiculoMapper = veiculoMapper;
    }

    // O produto e o veículo chegam prontos: quem busca no banco é o EntregaService.
    public Entrega toEntity(EntregaRequestDTO dto, Produto produto, Veiculo veiculo) {
        return new Entrega(dto.cliente(), dto.destino(), dto.quantidade(), dto.previsao(), produto, veiculo);
    }

    public EntregaResponseDTO toResponse(Entrega entrega) {
        return new EntregaResponseDTO(entrega.getId(), entrega.getCliente(), entrega.getDestino(),
                entrega.getQuantidade(), entrega.getStatus().name(), entrega.getPrevisao(),
                entrega.getDataConclusao(), produtoMapper.toResponse(entrega.getProduto()),
                veiculoMapper.toResponse(entrega.getVeiculo()));
    }

    public List<EntregaResponseDTO> toResponseList(List<Entrega> entregas) {
        return entregas.stream().map(this::toResponse).toList();
    }
}
