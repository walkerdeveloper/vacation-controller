package cast.vacation.ctrl.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cast.vacation.ctrl.event.RecursoCriadoEvent;
import cast.vacation.ctrl.model.Funcionario;
import cast.vacation.ctrl.repository.FuncionarioRepository;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioResource {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Funcionario> listar(){
		return funcionarioRepository.findAll();
	}
	
	@GetMapping("/{idFuncionario}")
	public Optional<Funcionario> buscarFuncionarioPorId(@PathVariable Long idFuncionario){
		return funcionarioRepository.findById(idFuncionario);
	}
	
	@PostMapping
	public ResponseEntity<Funcionario> criar(@RequestBody Funcionario funcionario, HttpServletResponse response){
		
		Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, funcionarioSalvo.getIdFuncionario()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioSalvo);
	}
	
	@DeleteMapping("/{idFuncionario}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long idFuncionario) {
		funcionarioRepository.deleteById(idFuncionario);
	}
	
	
}
