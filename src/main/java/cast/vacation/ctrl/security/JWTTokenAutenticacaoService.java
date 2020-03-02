package cast.vacation.ctrl.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cast.vacation.ctrl.ApplicationContextLoad;
import cast.vacation.ctrl.model.Signin;
import cast.vacation.ctrl.repository.SigninRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {

	private static final long EXPIRATION_TIME = 172800000;

	private static final String SECRET = "SenhaExtremamenteSecreta";

	private static final String TOKEN_PREFIX = "Bearer";

	private static final String HEADER_STRING = "Authorization";

	// Gerar token de autenticação e adicionar no cabeçalho de resposta
	public void addAuthentication(HttpServletResponse response, String email) throws IOException {

		String JWT = Jwts.builder() // Ativa o gerador de token
				.setSubject(email) // Adiciona o usuário
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Tempo de expiração
				.signWith(SignatureAlgorithm.HS512, SECRET).compact(); // Compactação e algoritmo de geração de senha
		// Junta o token com o prefixo
		String token = TOKEN_PREFIX + " " + JWT;
		// Adiciona o cabeçalho Http
		response.addHeader(HEADER_STRING, token);
		// Escrever o token como resposta no corpo do http
		response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
	}

	// Retorna o usuário validado com token, se não for válido vai retornar null
	public Authentication getAuthentication(HttpServletRequest request) {

		// Pega o token enviado no cabeçalho http
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			// Faz a validação do token na requisição
			String email = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
					.getSubject();
			if (token != null) {

				Signin signin = ApplicationContextLoad.getApplicationContext().getBean(SigninRepository.class)
						.findUserByEmail(email);

				if (signin != null) {
					return new UsernamePasswordAuthenticationToken(
							signin.getEmail(), 
							signin.getPassword(), 
							signin.getAuthorities());
				}
			}
		}
		return null;

	}
}
