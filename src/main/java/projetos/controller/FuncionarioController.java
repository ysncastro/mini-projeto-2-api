package projetos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projetos.exception.VendedorNotFoundException;
import projetos.model.Funcionario;
import projetos.service.FuncionarioService;

import java.util.List;

@RestController
@RequestMapping("/v1/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/farmacia/{farmaciaId}")
    public ResponseEntity<List<Funcionario>> listarFuncionariosPorFarmacia(@PathVariable Long farmaciaId) {
        List<Funcionario> funcionarios = funcionarioService.listarFuncionariosPorFarmacia(farmaciaId);
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarFuncionarioPorId(@PathVariable Long id) {
        return funcionarioService.buscarFuncionarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/farmacia/{idFarmacia}")
    public ResponseEntity<Funcionario> adicionarFuncionario(@RequestBody Funcionario funcionario, @PathVariable Long idFarmacia) {
        try {
            Funcionario funcionarioCriado = funcionarioService.adicionarFuncionario(funcionario, idFarmacia);
            return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioCriado);
        } catch (VendedorNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionario) {
        return funcionarioService.atualizarFuncionario(id, funcionario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable Long id) {
        return funcionarioService.deletarFuncionario(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}