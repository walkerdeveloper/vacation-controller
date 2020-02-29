package cast.vacation.ctrl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cast.vacation.ctrl.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

}
