package api.service;

// Entrega que não existe: herda de RecursoNaoEncontradoException, então sai como 404.
public class EntregaNaoEncontradaException extends RecursoNaoEncontradoException {

    public EntregaNaoEncontradaException(Long id) {
        super("Entrega não encontrada: " + id);
    }
}
