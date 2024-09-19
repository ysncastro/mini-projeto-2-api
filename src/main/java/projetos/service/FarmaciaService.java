package projetos.service;


import org.springframework.stereotype.Service;
import projetos.model.Farmacia;
import projetos.model.Funcionario;
import projetos.model.Medicamento;
import projetos.repository.FarmaciaRepository;
import projetos.repository.FuncionarioRepository;
import projetos.repository.MedicamentoRepository;

import java.util.List;


@Service
public class FarmaciaService {

    private final FarmaciaRepository farmaciaRepository;

    private final FuncionarioRepository funcionarioRepository;
    private final MedicamentoRepository medicamentoRepository;

    public FarmaciaService(FarmaciaRepository farmaciaRepository, FuncionarioRepository funcionarioRepository, MedicamentoRepository medicamentoRepository) {
        this.farmaciaRepository = farmaciaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    public List<Farmacia> listarFarmacias() {
        return farmaciaRepository.findAll();
    }

    public Farmacia salvarFarmacia(Farmacia farmacia) {
        Farmacia farmaciaSalva = farmaciaRepository.save(farmacia);

        for (Medicamento medicamento : farmaciaSalva.getMedicamentos()) {
            medicamento.setFarmacia(farmaciaSalva);
            medicamentoRepository.save(medicamento);
        }

        for (Funcionario funcionario : farmaciaSalva.getFuncionarios()) {
            funcionario.setFarmacia(farmaciaSalva);
            funcionarioRepository.save(funcionario);
        }

        return farmaciaSalva;
    }

    public Farmacia buscarFarmaciaPorId(Long id) {
        return farmaciaRepository.findById(id).orElseThrow(() -> new RuntimeException("Farmácia não encontrada"));
    }

    public void deletarFarmacia(Long id) {
        farmaciaRepository.deleteById(id);
    }
}