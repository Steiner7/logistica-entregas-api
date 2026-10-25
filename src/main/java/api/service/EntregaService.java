package api.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.dto.EntregaRequestDTO;
import api.dto.EntregaResponseDTO;
import api.mapper.EntregaMapper;
import api.model.Entrega;
import api.model.Produto;
import api.model.StatusEntrega;
import api.model.StatusVeiculo;
import api.model.Veiculo;
import api.repository.EntregaRepository;
import api.repository.ProdutoRepository;
import api.repository.VeiculoRepository;

// CAMADA: Service
// RESPONSABILIDADE: registrar, concluir e cancelar entregas, mantendo o estoque e a frota em dia.
// REGRAS: R1 (a entrega não pode deixar o estoque do produto abaixo do mínimo),
//         R2 (só um veículo DISPONIVEL recebe entrega; ele passa a EM_ROTA),
//         R3 (só entrega EM_TRANSITO pode ser concluída ou cancelada; o veículo é liberado) e
//         R4 (cancelar uma entrega devolve a quantidade ao estoque do produto).
// ROTAS ATENDIDAS: as de /entregas em docs/contrato-da-api.md.
// SITUAÇÃO: implementada.
@Service
public class EntregaService {

    private final EntregaRepository repository;
    private final ProdutoRepository produtoRepository;
    private final VeiculoRepository veiculoRepository;
    private final EntregaMapper mapper;

    public EntregaService(EntregaRepository repository, ProdutoRepository produtoRepository,
                          VeiculoRepository veiculoRepository, EntregaMapper mapper) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
        this.veiculoRepository = veiculoRepository;
        this.mapper = mapper;
    }

    // As mudanças no produto e no veículo são gravadas junto com a entrega, na mesma transação:
    // ou tudo vai para o banco, ou nada vai.
    @Transactional
    public EntregaResponseDTO criar(EntregaRequestDTO dto) {
        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new ProdutoNaoEncontradoException(dto.produtoId()));
        Veiculo veiculo = veiculoRepository.findById(dto.veiculoId())
                .orElseThrow(() -> new VeiculoNaoEncontradoException(dto.veiculoId()));

        // R1: o estoque mínimo é reserva de segurança e não pode ser usado em entregas.
        if (dto.quantidade() > produto.getDisponivelParaEntrega()) {
            throw new RegraDeNegocioException("R1: a entrega deixaria o estoque de \"" + produto.getNome()
                    + "\" abaixo do mínimo de " + produto.getEstoqueMinimo() + ". Disponível para entrega: "
                    + produto.getDisponivelParaEntrega());
        }

        // R2: um veículo leva uma entrega de cada vez.
        if (veiculo.getStatus() != StatusVeiculo.DISPONIVEL) {
            throw new RegraDeNegocioException("R2: o veículo " + veiculo.getId()
                    + " já está em rota e não pode receber outra entrega");
        }

        produto.baixarEstoque(dto.quantidade());
        veiculo.sairEmRota();
        Entrega entrega = mapper.toEntity(dto, produto, veiculo);
        return mapper.toResponse(repository.save(entrega));
    }

    @Transactional(readOnly = true)
    public List<EntregaResponseDTO> listar() {
        return mapper.toResponseList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public EntregaResponseDTO buscarPorId(Long id) {
        return mapper.toResponse(buscarEntidade(id));
    }

    @Transactional
    public EntregaResponseDTO concluir(Long id) {
        Entrega entrega = buscarEntidade(id);
        exigirEmTransito(entrega, "concluída");

        entrega.concluir(LocalDate.now());
        entrega.getVeiculo().liberar();
        return mapper.toResponse(entrega);
    }

    @Transactional
    public EntregaResponseDTO cancelar(Long id) {
        Entrega entrega = buscarEntidade(id);
        exigirEmTransito(entrega, "cancelada");

        // R4: o que não foi entregue volta para o estoque.
        entrega.getProduto().devolverEstoque(entrega.getQuantidade());
        entrega.cancelar(LocalDate.now());
        entrega.getVeiculo().liberar();
        return mapper.toResponse(entrega);
    }

    // R3: entrega ENTREGUE ou CANCELADA já está encerrada e não muda mais.
    private void exigirEmTransito(Entrega entrega, String acao) {
        if (entrega.getStatus() != StatusEntrega.EM_TRANSITO) {
            throw new RegraDeNegocioException("R3: só uma entrega em trânsito pode ser " + acao
                    + "; a entrega " + entrega.getId() + " está " + entrega.getStatus());
        }
    }

    private Entrega buscarEntidade(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntregaNaoEncontradaException(id));
    }
}
