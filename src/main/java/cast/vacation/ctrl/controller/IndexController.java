package cast.vacation.ctrl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cast.vacation.ctrl.model.Equipe;
import cast.vacation.ctrl.repository.EquipeRepository;

@RestController
@RequestMapping("/equipe")
public class IndexController {

	@Autowired
	private EquipeRepository equipeRepository;
	
	@GetMapping
	public List<Equipe> init() {
		return equipeRepository.findAll(); 
	}
}
