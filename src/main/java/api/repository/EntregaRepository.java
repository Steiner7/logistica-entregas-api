package api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api.model.Entrega;

// CAMADA: Repository
// RESPONSABILIDADE: ler e gravar as entregas na tabela entregas.
// SITUAÇÃO: implementada.
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
}
