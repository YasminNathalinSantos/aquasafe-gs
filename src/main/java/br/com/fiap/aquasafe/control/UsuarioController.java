package br.com.fiap.aquasafe.control;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.aquasafe.dto.UsuarioRequestDTO;
import br.com.fiap.aquasafe.model.Pessoa;
import br.com.fiap.aquasafe.model.StatusUsuarioEnum;
import br.com.fiap.aquasafe.model.Usuario;
import br.com.fiap.aquasafe.repository.PessoaRepository;
import br.com.fiap.aquasafe.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuarios", description = "Endpoints para gestao de usuarios")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repU;

	@Autowired
	private PessoaRepository repP;

	@Autowired
	private PasswordEncoder encoder;

	@Operation(summary = "Listar todos os usuarios", tags = "Usuarios")
	@GetMapping("/todos")
	public List<Usuario> listarTodos() {
		return repU.findAll();
	}

	@Operation(summary = "Buscar usuario por ID", tags = "Usuarios")
	@GetMapping("/{id}")
	public Usuario buscarPorId(@PathVariable Long id) {
		return repU.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Usuario com id " + id + " nao encontrado"));
	}

	@Operation(summary = "Criar novo usuario", tags = "Usuarios")
	@PostMapping("/novo")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario inserir(@RequestBody UsuarioRequestDTO dto) {

	
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(dto.nome());
		pessoa.setCpf(dto.cpf());
		pessoa.setDataNascimento(dto.dataNascimento());
		pessoa.setEmailPessoal(dto.emailPessoal());
		pessoa.setEndereco(dto.endereco());
		repP.save(pessoa);

		
		Usuario usuario = new Usuario();
		usuario.setPessoa(pessoa);
		usuario.setRm(dto.rm());
		usuario.setSenha(encoder.encode(dto.senha()));
		usuario.setPermissao(dto.permissao() != null ? dto.permissao() : "USER");
		usuario.setDataCriacao(dto.dataCriacao() != null ? dto.dataCriacao() : LocalDate.now());
		usuario.setStatus(dto.status() != null ? dto.status() : StatusUsuarioEnum.ATIVO);

		repU.save(usuario);
		return usuario;
	}

	@Operation(summary = "Remover usuario", tags = "Usuarios")
	@DeleteMapping("/remover/{id}")
	public Usuario remover(@PathVariable Long id) {
		Usuario usuario = repU.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Usuario com id " + id + " nao encontrado"));
		repU.deleteById(id);
		return usuario;
	}
}