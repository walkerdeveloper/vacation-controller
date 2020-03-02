package cast.vacation.ctrl.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

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
import cast.vacation.ctrl.model.Ferias;
import cast.vacation.ctrl.model.Funcionario;
import cast.vacation.ctrl.repository.FeriasRepository;
import cast.vacation.ctrl.repository.FuncionarioRepository;

@RestController
@RequestMapping("/ferias")
public class FeriasResource {

	@Autowired
	private FeriasRepository feriasRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Ferias> buscar(){
		return feriasRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public List<String> feriasByFuncionarioId(@PathVariable Long id){
		return feriasRepository.feriasByFuncionarioId(id);
	}
	
	/*@PostMapping
	public ResponseEntity<Ferias> salvar(@RequestBody Ferias ferias, HttpServletResponse response){
		repository.save(ferias);
		
		Optional<Ferias> f = repository.findById(ferias.getId());
		publisher.publishEvent(new RecursoCriadoEvent(this, response, f.get().getId()));
		
		Ferias feriasSalva = f.get();
		return ResponseEntity.status(HttpStatus.CREATED).body(feriasSalva);
	}*/
	
	@PostMapping
	public ResponseEntity<Ferias> salvar(@RequestBody Ferias ferias, HttpServletResponse response){
		
		Funcionario func = funcionarioRepository.findById(ferias.getFuncionario().getIdFuncionario()).get();
		ferias.setFuncionario(func);
		
		feriasRepository.save(ferias);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, ferias.getId()));
		
		return new ResponseEntity<Ferias>(ferias, HttpStatus.CREATED);
	}
}
