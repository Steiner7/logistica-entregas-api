package api.service;

// CAMADA: Service
// RESPONSABILIDADE: avisar que um veículo não existe. Herda de
//                   RecursoNaoEncontradoException, por isso vira 404.
// SITUAÇÃO: implementada.
public class VeiculoNaoEncontradoException extends RecursoNaoEncontradoException {

    public VeiculoNaoEncontradoException(Long id) {
        super("Veículo não encontrado: " + id);
    }
}
