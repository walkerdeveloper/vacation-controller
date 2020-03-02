package cast.vacation.ctrl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cast.vacation.ctrl.model.Signin;

@Repository
public interface SigninRepository extends CrudRepository<Signin, Long>{

	@Query("Select s From Signin s Where s.email = ?1")
	Signin findUserByEmail(String email);
}
