package cast.vacation.ctrl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cast.vacation.ctrl.model.Equipe;

public interface EquipeRepository extends JpaRepository<Equipe, Long>{

	@Query("select f.nomeFuncionario from Equipe e JOIN e.funcionarios f")
	List<String> teste();
	
}
