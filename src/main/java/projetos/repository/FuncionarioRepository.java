package projetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetos.model.Funcionario;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    List<Funcionario> findByFarmaciaId(Long farmaciaId);

    Optional<Funcionario> findByNome(String nome);

}