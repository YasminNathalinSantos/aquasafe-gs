package br.com.fiap.aquasafe.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.aquasafe.model.Usuario;
import br.com.fiap.aquasafe.repository.UsuarioRepository;

@Configuration
public class UsuarioConfig {

	@Autowired
	private UsuarioRepository repU;

	@Bean
	public UserDetailsService gerarUsuario() throws Exception {
		return rm -> {
			Usuario usuario = repU.findByRm(rm)
					.orElseThrow(() -> new UsernameNotFoundException(
							"Usuario nao foi localizado"));

			return User.builder()
					.username(usuario.getRm())
					.password(usuario.getSenha())
					.authorities("ROLE_USER", "ROLE_ADMIN")
					.build();
		};
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}