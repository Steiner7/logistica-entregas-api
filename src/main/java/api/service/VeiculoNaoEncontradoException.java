package api.service;

// Veículo que não existe: herda de RecursoNaoEncontradoException, então sai como 404.
public class VeiculoNaoEncontradoException extends RecursoNaoEncontradoException {

    public VeiculoNaoEncontradoException(Long id) {
        super("Veículo não encontrado: " + id);
    }
}
