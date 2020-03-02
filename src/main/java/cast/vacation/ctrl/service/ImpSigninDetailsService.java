package cast.vacation.ctrl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cast.vacation.ctrl.model.Signin;
import cast.vacation.ctrl.repository.SigninRepository;

@Service
public class ImpSigninDetailsService implements UserDetailsService {

	@Autowired
	private SigninRepository signinRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Signin signin = signinRepository.findUserByEmail(email);
		
		if(signin == null) {
			throw new  UsernameNotFoundException("Usuário não encontrado");
		}
		
		return new User(signin.getEmail(), signin.getPassword(), signin.getAuthorities());
	}

}
