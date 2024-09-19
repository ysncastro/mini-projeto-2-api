package projetos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetos.model.Medicamento;
import projetos.dto.MedicamentoCompraDTO;
import projetos.service.MedicamentoService;

import java.util.List;

@RestController
@RequestMapping("/v1/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping
    public List<Medicamento> listarMedicamentos() {
        return medicamentoService.listarMedicamentos();
    }

    @GetMapping("/farmacia/{farmaciaId}")
    public List<Medicamento> listarMedicamentosPorFarmacia(@PathVariable Long farmaciaId) {
        return medicamentoService.listarMedicamentosPorFarmacia(farmaciaId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> buscarMedicamentoPorId(@PathVariable Long id) {
        return medicamentoService.buscarMedicamentoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/comprar")
    public ResponseEntity<String> comprarMedicamento(@RequestBody List<MedicamentoCompraDTO> compraDTOList) {
        String resposta = medicamentoService.comprarMedicamentos(compraDTOList);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/farmacia/{idFarmacia}")
    public Medicamento adicionarMedicamento(@RequestBody Medicamento medicamento, @PathVariable Long idFarmacia) {
        return medicamentoService.adicionarMedicamento(medicamento, idFarmacia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> atualizarMedicamento(@PathVariable Long id, @RequestBody Medicamento medicamento) {
        return medicamentoService.atualizarMedicamento(id, medicamento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMedicamento(@PathVariable Long id) {
        if (medicamentoService.deletarMedicamento(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
