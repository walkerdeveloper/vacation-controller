package cast.vacation.ctrl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cast.vacation.ctrl.model.Ferias;

@Repository
public interface FeriasRepository extends JpaRepository<Ferias, Long> {

	@Query("Select func.idFuncionario, func.nomeFuncionario, f.dataInicio, f.dataFim From Ferias f JOIN f.funcionario func Where func.idFuncionario = ?1")
	List<String> feriasByFuncionarioId(Long id);
}
