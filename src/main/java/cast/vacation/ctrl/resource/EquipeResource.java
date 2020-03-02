package cast.vacation.ctrl.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cast.vacation.ctrl.event.RecursoCriadoEvent;
import cast.vacation.ctrl.model.Equipe;
import cast.vacation.ctrl.repository.EquipeRepository;

@RestController
@RequestMapping("/equipe")
public class EquipeResource {

	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Equipe> listar() {
		return equipeRepository.findAll(); 
	}
	
	@GetMapping("/{idEquipe}")
	public Optional<Equipe> buscarPeloCodigo(@PathVariable Long idEquipe) {
		return equipeRepository.findById(idEquipe);
	}
	
	@PostMapping
	public ResponseEntity<Equipe> criar(@Valid @RequestBody Equipe equipe, HttpServletResponse response) {
		System.out.println(equipe.toString());
		Equipe equipeSalva = equipeRepository.save(equipe);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, equipeSalva.getIdEquipe()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(equipeSalva);
	}
	
	@GetMapping("/teste")
	public List<String> teste(){
		return equipeRepository.teste();
	}
}
