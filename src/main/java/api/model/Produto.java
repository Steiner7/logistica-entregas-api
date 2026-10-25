package api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// CAMADA: Model
// RESPONSABILIDADE: um produto do estoque, com a quantidade atual e o estoque mínimo de segurança.
// SITUAÇÃO: implementada.
@Entity
@Table(name = "produtos")
public class Produto {

    // Quem gera o id é o banco (BIGSERIAL).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(name = "estoque_atual", nullable = false)
    private Integer estoqueAtual;

    @Column(name = "estoque_minimo", nullable = false)
    private Integer estoqueMinimo;

    // Exigido pelo Hibernate.
    protected Produto() {
    }

    public Produto(String nome, Integer estoqueAtual, Integer estoqueMinimo) {
        this.nome = nome;
        this.estoqueAtual = estoqueAtual;
        this.estoqueMinimo = estoqueMinimo;
    }

    // Tira do estoque a quantidade que saiu numa entrega. Quem confere se pode é o EntregaService (R1).
    public void baixarEstoque(int quantidade) {
        this.estoqueAtual -= quantidade;
    }

    // Devolve ao estoque a quantidade de uma entrega cancelada (R4).
    public void devolverEstoque(int quantidade) {
        this.estoqueAtual += quantidade;
    }

    // Quanto ainda pode sair sem furar o estoque mínimo.
    public int getDisponivelParaEntrega() {
        return Math.max(0, estoqueAtual - estoqueMinimo);
    }

    public boolean isAbaixoDoMinimo() {
        return estoqueAtual < estoqueMinimo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getEstoqueAtual() {
        return estoqueAtual;
    }

    public Integer getEstoqueMinimo() {
        return estoqueMinimo;
    }
}
