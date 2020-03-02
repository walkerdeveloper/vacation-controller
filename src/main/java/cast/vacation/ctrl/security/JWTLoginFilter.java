package cast.vacation.ctrl.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import cast.vacation.ctrl.model.Signin;

// Estabelece o gerenciador de Token
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	// Configurando o gerenciador de autenticação
	protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {

		// Obriga a autenticar a URL
		super(new AntPathRequestMatcher(url));

		// Gerenciador de autenticação
		setAuthenticationManager(authenticationManager);
	}

	// Retorna o usuário ao processar a autenticação
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		// Está pegando o token para validar
		Signin signin = new ObjectMapper().readValue(request.getInputStream(), Signin.class);
		System.out.println("InpusStream" + request.getInputStream().toString());
		
		UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken(signin.getEmail(),signin.getPassword());
		return getAuthenticationManager().authenticate(u);
		// Retorna email, senha e acessos
		/*return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
						signin.getEmail(),
						signin.getPassword()));*/
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());
	}

}
