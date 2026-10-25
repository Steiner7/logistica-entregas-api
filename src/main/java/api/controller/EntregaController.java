package api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import api.dto.EntregaRequestDTO;
import api.dto.EntregaResponseDTO;
import api.service.EntregaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// CAMADA: Controller
// RESPONSABILIDADE: receber as requisições de /entregas e repassar ao EntregaService.
// ROTAS ATENDIDAS: POST /entregas, GET /entregas, GET /entregas/{id},
//                  PUT /entregas/{id}/concluir e PUT /entregas/{id}/cancelar.
// SITUAÇÃO: implementada.
@RestController
@RequestMapping("/entregas")
@Tag(name = "Entregas", description = "Rastreamento de entregas")
public class EntregaController {

    private final EntregaService service;

    public EntregaController(EntregaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registra uma entrega: baixa o estoque e coloca o veículo em rota (R1 e R2)")
    public EntregaResponseDTO criar(@Valid @RequestBody EntregaRequestDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    @Operation(summary = "Lista as entregas")
    public List<EntregaResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma entrega pelo id")
    public EntregaResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}/concluir")
    @Operation(summary = "Marca a entrega como ENTREGUE e libera o veículo (R3)")
    public EntregaResponseDTO concluir(@PathVariable Long id) {
        return service.concluir(id);
    }

    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancela a entrega, devolve o produto ao estoque e libera o veículo (R3 e R4)")
    public EntregaResponseDTO cancelar(@PathVariable Long id) {
        return service.cancelar(id);
    }
}
