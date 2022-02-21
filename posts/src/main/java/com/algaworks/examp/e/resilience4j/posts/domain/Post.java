package com.algaworks.examp.e.resilience4j.posts.domain;

public class Post {
	
	private Long id;
	private String title;
	private String description;
	private Long editorId;

	public Post() {
		
	}

	public Post(Long id, String title, String description, Long editorId) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.editorId = editorId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getEditorId() {
		return editorId;
	}

	public void setEditorId(Long editorId) {
		this.editorId = editorId;
	}
}
