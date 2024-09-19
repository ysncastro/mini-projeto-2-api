package projetos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetos.dto.MedicamentoCompraDTO;
import projetos.exception.EstoqueInsuficienteException;
import projetos.exception.MedicamentoNotFoundException;
import projetos.exception.VendedorNotFoundException;
import projetos.model.Medicamento;
import projetos.service.MedicamentoService;

import java.util.List;

@RestController
@RequestMapping("/v1/medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public ResponseEntity<List<Medicamento>> listarMedicamentos() {
        List<Medicamento> medicamentos = medicamentoService.listarMedicamentos();
        return ResponseEntity.ok(medicamentos);
    }

    @GetMapping("/farmacia/{farmaciaId}")
    public ResponseEntity<List<Medicamento>> listarMedicamentosPorFarmacia(@PathVariable Long farmaciaId) {
        List<Medicamento> medicamentos = medicamentoService.listarMedicamentosPorFarmacia(farmaciaId);
        return ResponseEntity.ok(medicamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> buscarMedicamentoPorId(@PathVariable Long id) {
        return medicamentoService.buscarMedicamentoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/comprar")
    public ResponseEntity<String> comprarMedicamento(@RequestBody List<MedicamentoCompraDTO> compraDTOList) {
        try {
            String resposta = medicamentoService.comprarMedicamentos(compraDTOList);
            return ResponseEntity.ok(resposta);
        } catch (MedicamentoNotFoundException | EstoqueInsuficienteException | VendedorNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/farmacia/{idFarmacia}")
    public ResponseEntity<Medicamento> adicionarMedicamento(@RequestBody Medicamento medicamento, @PathVariable Long idFarmacia) {
        try {
            Medicamento medicamentoCriado = medicamentoService.adicionarMedicamento(medicamento, idFarmacia);
            return ResponseEntity.status(HttpStatus.CREATED).body(medicamentoCriado);
        } catch (VendedorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> atualizarMedicamento(@PathVariable Long id, @RequestBody Medicamento medicamento) {
        return medicamentoService.atualizarMedicamento(id, medicamento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMedicamento(@PathVariable Long id) {
        return medicamentoService.deletarMedicamento(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}