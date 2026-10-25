package api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import api.dto.ProdutoRequestDTO;
import api.dto.ProdutoResponseDTO;
import api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// CAMADA: Controller
// RESPONSABILIDADE: receber as requisições de /produtos e repassar ao ProdutoService.
// ROTAS ATENDIDAS: POST /produtos, GET /produtos e GET /produtos/{id}.
// SITUAÇÃO: implementada.
@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Controle de estoque")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um produto no estoque")
    public ProdutoResponseDTO criar(@Valid @RequestBody ProdutoRequestDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    @Operation(summary = "Lista os produtos do estoque")
    public List<ProdutoResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um produto pelo id")
    public ProdutoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}
