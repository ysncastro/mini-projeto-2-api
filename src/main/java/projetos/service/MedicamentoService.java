package projetos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projetos.dto.MedicamentoCompraDTO;
import projetos.exception.EstoqueInsuficienteException;
import projetos.exception.MedicamentoNotFoundException;
import projetos.exception.VendedorNotFoundException;
import projetos.model.Farmacia;
import projetos.model.Funcionario;
import projetos.model.Medicamento;
import projetos.repository.FarmaciaRepository;
import projetos.repository.FuncionarioRepository;
import projetos.repository.MedicamentoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoService {

    @Autowired
    private FarmaciaRepository farmaciaRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Medicamento> listarMedicamentos() {
        return medicamentoRepository.findAll();
    }

    public List<Medicamento> listarMedicamentosPorFarmacia(Long farmaciaId) {
        return medicamentoRepository.findByFarmaciaId(farmaciaId);
    }

    public Optional<Medicamento> buscarMedicamentoPorId(Long id) {
        return medicamentoRepository.findById(id);
    }

    public String comprarMedicamentos(List<MedicamentoCompraDTO> compraDTOList) {
        StringBuilder resposta = new StringBuilder();

        for (MedicamentoCompraDTO compraDTO : compraDTOList) {
            String result = comprarMedicamento(compraDTO);
            resposta.append(result).append("\n");
        }

        return resposta.toString();
    }

    public String comprarMedicamento(MedicamentoCompraDTO compraDTO) throws MedicamentoNotFoundException, EstoqueInsuficienteException, VendedorNotFoundException {
        Optional<Medicamento> medicamentoOpt = medicamentoRepository.findByNome(compraDTO.getNomeMedicamento());
        if (medicamentoOpt.isEmpty()) {
            throw new MedicamentoNotFoundException("Medicamento não encontrado: " + compraDTO.getNomeMedicamento());
        }

        Medicamento medicamento = medicamentoOpt.get();

        if (medicamento.getQuantidadeEmEstoque() < compraDTO.getQuantidade()) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para o medicamento: " + compraDTO.getNomeMedicamento());
        }

        Optional<Funcionario> vendedorOpt = funcionarioRepository.findByNome(compraDTO.getNomeVendedor());
        if (vendedorOpt.isEmpty()) {
            throw new VendedorNotFoundException("Vendedor não encontrado: " + compraDTO.getNomeVendedor());
        }

        Funcionario vendedor = vendedorOpt.get();

        medicamento.setQuantidadeEmEstoque(medicamento.getQuantidadeEmEstoque() - compraDTO.getQuantidade());
        medicamentoRepository.save(medicamento);

        Farmacia farmacia = medicamento.getFarmacia();
        double valorCompra = medicamento.getPreco() * compraDTO.getQuantidade();
        farmacia.setLucro(farmacia.getLucro() + valorCompra);
        farmaciaRepository.save(farmacia);

        vendedor.setBonus(vendedor.getBonus() + 10);

        if (vendedor.getBonus() >= 30) {
            vendedor.setSalarioBase(vendedor.getSalarioBase() + 100);
            vendedor.setBonus(0);
        }
        funcionarioRepository.save(vendedor);

        return "Compra realizada com sucesso! Medicamento: " + medicamento.getNome() + ", Quantidade: " + compraDTO.getQuantidade();
    }

    public Medicamento adicionarMedicamento(Medicamento medicamento, Long idFarmacia) {
        Optional<Farmacia> optFarmacia = farmaciaRepository.findById(idFarmacia);
        if(optFarmacia.isEmpty()) {
            throw new VendedorNotFoundException("Farmácia de id: " + idFarmacia  + " não encontrada.");

        }
        Farmacia farmacia = optFarmacia.get();
        medicamento.setFarmacia(farmacia);
        return medicamentoRepository.save(medicamento);
    }

    public Optional<Medicamento> atualizarMedicamento(Long id, Medicamento medicamentoAtualizado) {
        return medicamentoRepository.findById(id)
                .map(medicamento -> {
                    medicamento.setNome(medicamentoAtualizado.getNome());
                    medicamento.setQuantidadeEmEstoque(medicamentoAtualizado.getQuantidadeEmEstoque());
                    medicamento.setPreco(medicamentoAtualizado.getPreco());
                    return medicamentoRepository.save(medicamento);
                });
    }

    public boolean deletarMedicamento(Long id) {
        return medicamentoRepository.findById(id)
                .map(medicamento -> {
                    medicamentoRepository.delete(medicamento);
                    return true;
                }).orElse(false);
    }
}
