package cast.vacation.ctrl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import cast.vacation.ctrl.repository.SigninRepository;

@SpringBootApplication
@EntityScan(basePackages = {"cast.vacation.ctrl.model"})
@ComponentScan(basePackages = {"cast.*"}, basePackageClasses = SigninRepository.class)
@EnableJpaRepositories(basePackages = {"cast.vacation.ctrl.repository"})
@EnableTransactionManagement
@EnableWebMvc
@RestController
//@EnableAutoConfiguration
public class VacationControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacationControllerApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("132"));
	}

}
