package api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import api.dto.VeiculoRequestDTO;
import api.dto.VeiculoResponseDTO;
import api.model.Veiculo;

// CAMADA: Mapper
// RESPONSABILIDADE: converter VeiculoRequestDTO em Veiculo e Veiculo em VeiculoResponseDTO.
// SITUAÇÃO: implementada.
@Component
public class VeiculoMapper {

    public Veiculo toEntity(VeiculoRequestDTO dto) {
        return new Veiculo(dto.descricao(), dto.motorista(), dto.rota());
    }

    public VeiculoResponseDTO toResponse(Veiculo veiculo) {
        return new VeiculoResponseDTO(veiculo.getId(), veiculo.getDescricao(), veiculo.getMotorista(),
                veiculo.getRota(), veiculo.getStatus().name());
    }

    public List<VeiculoResponseDTO> toResponseList(List<Veiculo> veiculos) {
        return veiculos.stream().map(this::toResponse).toList();
    }
}
