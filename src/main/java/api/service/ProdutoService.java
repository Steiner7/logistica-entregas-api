package api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.dto.ProdutoRequestDTO;
import api.dto.ProdutoResponseDTO;
import api.mapper.ProdutoMapper;
import api.repository.ProdutoRepository;

// CAMADA: Service
// RESPONSABILIDADE: cadastrar e consultar os produtos do estoque.
// REGRAS: nenhuma. O estoque só muda pelas entregas (R1 e R4, no EntregaService).
// ROTAS ATENDIDAS: POST /produtos, GET /produtos e GET /produtos/{id}.
// SITUAÇÃO: implementada.
@Service
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    public ProdutoService(ProdutoRepository repository, ProdutoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        return mapper.toResponse(repository.save(mapper.toEntity(dto)));
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listar() {
        return mapper.toResponseList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }
}
