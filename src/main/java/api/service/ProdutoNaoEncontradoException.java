package api.service;

// CAMADA: Service
// RESPONSABILIDADE: avisar que um produto não existe. Herda de
//                   RecursoNaoEncontradoException, por isso vira 404.
// SITUAÇÃO: implementada.
public class ProdutoNaoEncontradoException extends RecursoNaoEncontradoException {

    public ProdutoNaoEncontradoException(Long id) {
        super("Produto não encontrado: " + id);
    }
}
