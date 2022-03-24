package com.algaworks.example.mensagem.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Mensagem {

	@Id
	private Long id;
	private String usuario;
	private String conteudo;

	public Mensagem() {
		
	}

	public Mensagem(Long id, String usuario, String conteudo) {
		this.id = id;
		this.usuario = usuario;
		this.conteudo = conteudo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
}
