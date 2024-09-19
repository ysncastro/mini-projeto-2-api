package projetos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetos.model.Farmacia;

public interface FarmaciaRepository extends JpaRepository<Farmacia, Long> {
}