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

import api.dto.VeiculoRequestDTO;
import api.dto.VeiculoResponseDTO;
import api.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// CAMADA: Controller
// RESPONSABILIDADE: receber as requisições de /veiculos e repassar ao VeiculoService.
// ROTAS ATENDIDAS: POST /veiculos, GET /veiculos e GET /veiculos/{id}.
// SITUAÇÃO: implementada.
@RestController
@RequestMapping("/veiculos")
@Tag(name = "Veículos", description = "Controle da frota")
public class VeiculoController {

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um veículo na frota (nasce DISPONIVEL)")
    public VeiculoResponseDTO criar(@Valid @RequestBody VeiculoRequestDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    @Operation(summary = "Lista os veículos da frota")
    public List<VeiculoResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um veículo pelo id")
    public VeiculoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}
