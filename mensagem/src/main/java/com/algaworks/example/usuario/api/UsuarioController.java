package com.algaworks.example.usuario.api;

import com.algaworks.example.config.RecursoNaoEncontradoException;
import com.algaworks.example.usuario.domain.Usuario;
import com.algaworks.example.usuario.domain.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	private final UsuarioRepository usuarioRepository;

	public UsuarioController(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@GetMapping
	public Page<Usuario> buscarPaginado(Pageable pageable) {
		return usuarioRepository.findAll(pageable);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario criarNovo(@RequestBody @Valid Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@GetMapping("/{id}")
	public Usuario buscarPorId(@PathVariable Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(RecursoNaoEncontradoException::new);
	}
	
}
