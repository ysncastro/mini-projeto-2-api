package projetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetos.model.Medicamento;

import java.util.List;
import java.util.Optional;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    List<Medicamento> findByFarmaciaId(Long farmaciaId);

    Optional<Medicamento> findByNome(String nome);


}