package api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api.model.Veiculo;

// CAMADA: Repository
// RESPONSABILIDADE: ler e gravar os veículos da frota na tabela veiculos.
// SITUAÇÃO: implementada.
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}
