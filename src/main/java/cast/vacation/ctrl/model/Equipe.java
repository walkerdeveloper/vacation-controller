package cast.vacation.ctrl.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "equipe")
public class Equipe implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cd_equipe")
	private Long idEquipe;
	
	@Column(name = "nome_equipe")
	@NotNull
	private String nomeDaEquipe;

	public Long getIdEquipe() {
		return idEquipe;
	}

	public void setIdEquipe(Long idEquipe) {
		this.idEquipe = idEquipe;
	}

	public String getNomeDaEquipe() {
		return nomeDaEquipe;
	}

	public void setNomeDaEquipe(String nomeDaEquipe) {
		this.nomeDaEquipe = nomeDaEquipe;
	}
	
	
	@Override
	public String toString() {
		return "Id: " + this.getIdEquipe() + "\nNome: " + this.getNomeDaEquipe();
	}
	

}
