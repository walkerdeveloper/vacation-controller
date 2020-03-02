package cast.vacation.ctrl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import cast.vacation.ctrl.service.ImpSigninDetailsService;

// Mapear URL's, endereços, autorizar ou bloquear acessos ***
@Configuration
@EnableWebSecurity
@Service
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImpSigninDetailsService impSigninDetailsService;
	
	
	// Configura as solicitações de acesso HTTP
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// Proteção contra usuários sem token validado
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		
		// Permitir qualquer um acessar página inicial da aplicação
		.disable().authorizeRequests().antMatchers("/").permitAll()
		.antMatchers("/index").permitAll()
		
		// URL de logout - redirecionar após logout do usuário
		.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
		
		// Mapear URL de logout e invalidar o usuário
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		// Filtra requisições de login para autenticação
		.and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), 
								   UsernamePasswordAuthenticationFilter.class)

		// Filtra demais requisições para verificar a presença do TOKEN no HEADER Http
		.addFilterBefore(new JWTApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// Service para consultar o usuário no BD
		auth.userDetailsService(impSigninDetailsService)
		// Padrão de criptografia de senha
		.passwordEncoder(new BCryptPasswordEncoder());
	}

}
