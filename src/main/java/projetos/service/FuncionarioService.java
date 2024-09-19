package projetos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetos.exception.VendedorNotFoundException;
import projetos.model.Farmacia;
import projetos.model.Funcionario;
import projetos.repository.FarmaciaRepository;
import projetos.repository.FuncionarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FarmaciaRepository farmaciaRepository;

    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public List<Funcionario> listarFuncionariosPorFarmacia(Long farmaciaId) {
        return funcionarioRepository.findByFarmaciaId(farmaciaId);
    }

    public Optional<Funcionario> buscarFuncionarioPorId(Long id) {
        return funcionarioRepository.findById(id);
    }

    public Funcionario adicionarFuncionario(Funcionario funcionario, Long idFarmacia) {
        Optional<Farmacia> optFarmacia = farmaciaRepository.findById(idFarmacia);
        if(optFarmacia.isEmpty()) {
            throw new VendedorNotFoundException("Farmácia de id: " + idFarmacia  + " não encontrada.");

        }
        Farmacia farmacia = optFarmacia.get();
        funcionario.setFarmacia(farmacia);
        return funcionarioRepository.save(funcionario);
    }

    public Optional<Funcionario> atualizarFuncionario(Long id, Funcionario funcionarioAtualizado) {
        return funcionarioRepository.findById(id)
                .map(funcionario -> {
                    funcionario.setNome(funcionarioAtualizado.getNome());
                    funcionario.setBonus(funcionarioAtualizado.getBonus());
                    funcionario.setSalarioBase(funcionarioAtualizado.getSalarioBase());
                    return funcionarioRepository.save(funcionario);
                });
    }

    public boolean deletarFuncionario(Long id) {
        return funcionarioRepository.findById(id)
                .map(funcionario -> {
                    funcionarioRepository.delete(funcionario);
                    return true;
                }).orElse(false);
    }
}
