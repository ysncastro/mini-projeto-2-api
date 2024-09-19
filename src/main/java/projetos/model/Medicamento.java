package projetos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private int quantidadeEmEstoque;

    private double preco;

    @ManyToOne
    @JoinColumn(name = "farmacia_id", nullable = false)
    @JsonBackReference
    private Farmacia farmacia;

    public Medicamento() {
    }

    public Medicamento(String nome, int quantidadeEmEstoque, double preco) {
        this.nome = nome;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.preco = preco;
    }

}