package projetos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicamentoCompraDTO {

    private String nomeMedicamento;
    private Integer quantidade;
    private String nomeVendedor;
}
