package api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// CAMADA: Model
// RESPONSABILIDADE: um veículo da frota, com o motorista, a rota que atende e se está livre ou em rota.
// SITUAÇÃO: implementada.
@Entity
@Table(name = "veiculos")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String descricao;

    @Column(nullable = false, length = 50)
    private String motorista;

    @Column(nullable = false, length = 100)
    private String rota;

    // Gravado como texto ("DISPONIVEL"), não como número.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusVeiculo status;

    protected Veiculo() {
    }

    // Todo veículo nasce disponível: o cliente da API não escolhe o status.
    public Veiculo(String descricao, String motorista, String rota) {
        this.descricao = descricao;
        this.motorista = motorista;
        this.rota = rota;
        this.status = StatusVeiculo.DISPONIVEL;
    }

    // Chamado quando o veículo recebe uma entrega (R2).
    public void sairEmRota() {
        this.status = StatusVeiculo.EM_ROTA;
    }

    // Chamado quando a entrega do veículo é concluída ou cancelada (R3).
    public void liberar() {
        this.status = StatusVeiculo.DISPONIVEL;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getMotorista() {
        return motorista;
    }

    public String getRota() {
        return rota;
    }

    public StatusVeiculo getStatus() {
        return status;
    }
}
