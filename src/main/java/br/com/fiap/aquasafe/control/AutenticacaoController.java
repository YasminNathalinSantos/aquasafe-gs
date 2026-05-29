package br.com.fiap.aquasafe.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.aquasafe.security.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticacao", description = "Endpoint de login e geracao de token JWT")
@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager manager;

	@Autowired
	private JWTUtil jwtUtil;

	@Operation(summary = "Realizar login e obter token JWT", tags = "Autenticacao",
		description = "Informe o RM e a senha. Duracao do token em minutos (padrao: 60).")
	@PostMapping(value = "/login")
	public String logar(
			@RequestParam String usuario,
			@RequestParam String senha,
			@RequestParam(value = "duracao", defaultValue = "60") Integer duracao) {
		try {
			var autenticacao = new UsernamePasswordAuthenticationToken(usuario, senha);
			manager.authenticate(autenticacao);
			return jwtUtil.gerarToken(usuario, duracao);
		} catch (Exception e) {
			return "Credenciais invalidas!";
		}
	}
}
