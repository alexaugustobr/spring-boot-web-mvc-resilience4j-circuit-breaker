package com.algaworks.examp.e.algaworksresilience4jeditors.api;

import com.algaworks.examp.e.algaworksresilience4jeditors.domain.Editor;

public class EditorModel {
	
	private Long id;
	private String name;

	public EditorModel() {
	}

	public EditorModel(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static EditorModel of(Editor editor) {
		return new EditorModel(editor.getId(), editor.getName());
	}
}
