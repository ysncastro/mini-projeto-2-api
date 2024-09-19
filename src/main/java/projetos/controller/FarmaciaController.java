package projetos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetos.model.Farmacia;
import projetos.service.FarmaciaService;

import java.util.List;

@RestController
@RequestMapping("/v1/farmacias")
public class FarmaciaController {

    private final FarmaciaService farmaciaService;

    public FarmaciaController(FarmaciaService farmaciaService) {
        this.farmaciaService = farmaciaService;
    }

    @GetMapping
    public ResponseEntity<List<Farmacia>> listarFarmacias() {
        List<Farmacia> farmacias = farmaciaService.listarFarmacias();
        return ResponseEntity.ok(farmacias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Farmacia> buscarFarmacia(@PathVariable Long id) {
        try {
            Farmacia farmacia = farmaciaService.buscarFarmaciaPorId(id);
            return ResponseEntity.ok(farmacia);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Farmacia> criarFarmacia(@RequestBody Farmacia farmacia) {
        Farmacia farmaciaCriada = farmaciaService.salvarFarmacia(farmacia);
        return ResponseEntity.status(HttpStatus.CREATED).body(farmaciaCriada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFarmacia(@PathVariable Long id) {
        try {
            farmaciaService.deletarFarmacia(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }}