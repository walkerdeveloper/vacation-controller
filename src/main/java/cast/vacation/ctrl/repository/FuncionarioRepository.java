package cast.vacation.ctrl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cast.vacation.ctrl.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

	//@Query("Select f From Funcionario JOIN f.ferias fe")
	//List<Funcionario> filtrarFeriasParaVencer(int qtdMeses);
}
