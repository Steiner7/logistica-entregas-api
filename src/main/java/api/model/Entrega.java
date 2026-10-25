package api.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// CAMADA: Model
// RESPONSABILIDADE: uma entrega rastreada: o que sai do estoque, para qual cliente, em qual veículo e em que situação está.
// SITUAÇÃO: implementada.
@Entity
@Table(name = "entregas")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String cliente;

    @Column(nullable = false, length = 100)
    private String destino;

    @Column(nullable = false)
    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusEntrega status;

    @Column(nullable = false)
    private LocalDate previsao;

    // Fica nulo enquanto a entrega está em trânsito.
    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;

    // Muitas entregas para um produto: a chave estrangeira é entregas.produto_id.
    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    // Muitas entregas para um veículo: a chave estrangeira é entregas.veiculo_id.
    @ManyToOne(optional = false)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    protected Entrega() {
    }

    // Toda entrega nasce em trânsito: o cliente da API não escolhe o status.
    public Entrega(String cliente, String destino, Integer quantidade, LocalDate previsao,
                   Produto produto, Veiculo veiculo) {
        this.cliente = cliente;
        this.destino = destino;
        this.quantidade = quantidade;
        this.previsao = previsao;
        this.produto = produto;
        this.veiculo = veiculo;
        this.status = StatusEntrega.EM_TRANSITO;
    }

    public void concluir(LocalDate data) {
        this.status = StatusEntrega.ENTREGUE;
        this.dataConclusao = data;
    }

    public void cancelar(LocalDate data) {
        this.status = StatusEntrega.CANCELADA;
        this.dataConclusao = data;
    }

    public Long getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDestino() {
        return destino;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public StatusEntrega getStatus() {
        return status;
    }

    public LocalDate getPrevisao() {
        return previsao;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public Produto getProduto() {
        return produto;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }
}
