package projetos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private double salarioBase;

    private int bonus = 0;

    @ManyToOne
    @JoinColumn(name = "farmacia_id")
    @JsonBackReference
    private Farmacia farmacia;

    public Funcionario() {
    }

    public Funcionario(String nome, double salarioBase) {
        this.nome = nome;
        this.salarioBase = salarioBase;
        this.bonus = 0;
    }

    public void adicionarBonus() {
        this.bonus += 10;
    }

}