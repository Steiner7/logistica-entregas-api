package api.service;

// Produto que não existe: herda de RecursoNaoEncontradoException, então sai como 404.
public class ProdutoNaoEncontradoException extends RecursoNaoEncontradoException {

    public ProdutoNaoEncontradoException(Long id) {
        super("Produto não encontrado: " + id);
    }
}
