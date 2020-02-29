package cast.vacation.ctrl.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cast.vacation.ctrl.model.Equipe;
import cast.vacation.ctrl.repository.EquipeRepository;

@RestController
@RequestMapping("/equipe")
public class EquipeResource {

	@Autowired
	private EquipeRepository equipeRepository;
	
	@GetMapping
	public List<Equipe> listar() {
		return equipeRepository.findAll(); 
	}
	
	@GetMapping("/{idEquipe}")
	public Optional<Equipe> buscarPeloCodigo(@PathVariable Long idEquipe) {
		return equipeRepository.findById(idEquipe);
	}
	
	@PostMapping
	public ResponseEntity<Equipe> criar(@RequestBody Equipe equipe, HttpServletResponse response) {
		Equipe equipeSalva = equipeRepository.save(equipe);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(equipeSalva.getIdEquipe()).toUri();
		response.setHeader("location", uri.toASCIIString());
		System.out.println("uri: " + uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(equipeSalva);
	}
}
