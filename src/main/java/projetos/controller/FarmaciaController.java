package projetos.controller;

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
    public List<Farmacia> listarFarmacias() {
        return farmaciaService.listarFarmacias();
    }

    @GetMapping("/{id}")
    public Farmacia buscarFarmacia(@PathVariable Long id) {
        return farmaciaService.buscarFarmaciaPorId(id);
    }

    @PostMapping
    public Farmacia criarFarmacia(@RequestBody Farmacia farmacia) {
        return farmaciaService.salvarFarmacia(farmacia);
    }

    @DeleteMapping("/{id}")
    public void deletarFarmacia(@PathVariable Long id) {
        farmaciaService.deletarFarmacia(id);
    }
}
