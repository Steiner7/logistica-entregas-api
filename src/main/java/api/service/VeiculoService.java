package api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.dto.VeiculoRequestDTO;
import api.dto.VeiculoResponseDTO;
import api.mapper.VeiculoMapper;
import api.repository.VeiculoRepository;

// CAMADA: Service
// RESPONSABILIDADE: cadastrar e consultar os veículos da frota.
// REGRAS: nenhuma. O status do veículo só muda pelas entregas (R2 e R3, no EntregaService).
// ROTAS ATENDIDAS: POST /veiculos, GET /veiculos e GET /veiculos/{id}.
// SITUAÇÃO: implementada.
@Service
public class VeiculoService {

    private final VeiculoRepository repository;
    private final VeiculoMapper mapper;

    public VeiculoService(VeiculoRepository repository, VeiculoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public VeiculoResponseDTO criar(VeiculoRequestDTO dto) {
        return mapper.toResponse(repository.save(mapper.toEntity(dto)));
    }

    @Transactional(readOnly = true)
    public List<VeiculoResponseDTO> listar() {
        return mapper.toResponseList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public VeiculoResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(id));
    }
}
