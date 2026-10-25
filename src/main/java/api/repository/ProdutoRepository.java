package api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api.model.Produto;

// CAMADA: Repository
// RESPONSABILIDADE: ler e gravar os produtos do estoque na tabela produtos.
// SITUAÇÃO: implementada.
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
