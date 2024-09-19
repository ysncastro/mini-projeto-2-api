package projetos.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Farmacia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double lucro = 0;

    @OneToMany(mappedBy = "farmacia", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Medicamento> medicamentos;

    @OneToMany(mappedBy = "farmacia", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Funcionario> funcionarios;

    public Farmacia() {
    }

    public Farmacia(List<Medicamento> medicamentos, List<Funcionario> funcionarios) {
        this.medicamentos = medicamentos;
        this.funcionarios = funcionarios;
        this.lucro = 0;
    }

}
