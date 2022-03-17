package com.algaworks.example.produto.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class Produto {
	
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotEmpty
	private List<@NotBlank String> tags = new ArrayList<String>();

	public Produto() {
		
	}

	public Produto(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
