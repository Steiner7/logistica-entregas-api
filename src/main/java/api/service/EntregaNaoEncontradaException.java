package api.service;

// CAMADA: Service
// RESPONSABILIDADE: avisar que uma entrega não existe. Herda de
//                   RecursoNaoEncontradoException, por isso vira 404.
// SITUAÇÃO: implementada.
public class EntregaNaoEncontradaException extends RecursoNaoEncontradoException {

    public EntregaNaoEncontradaException(Long id) {
        super("Entrega não encontrada: " + id);
    }
}
