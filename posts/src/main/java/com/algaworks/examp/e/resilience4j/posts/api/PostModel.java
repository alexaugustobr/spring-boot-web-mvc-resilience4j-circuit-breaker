package com.algaworks.examp.e.resilience4j.posts.api;

import com.algaworks.examp.e.resilience4j.posts.client.editors.EditorModel;
import com.algaworks.examp.e.resilience4j.posts.domain.Post;

public class PostModel {

	public Long id;
	public String title;
	public String description;
	public EditorModel editor;

	public PostModel() {

	}

	public PostModel(Long id, String title, String description, EditorModel editor) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.editor = editor;
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

	public EditorModel getEditor() {
		return editor;
	}

	public void setEditor(EditorModel editor) {
		this.editor = editor;
	}
	
	public Post toEntity() {
		
		Long editorId;
		if (this.editor != null) {
			editorId = this.editor.getId();
		} else {
			editorId = null;
		}
		
		return new Post(
				this.id,
				this.title,
				this.description,
				editorId
		);
	}
	
	public static PostModel of(Post post, EditorModel editor) {
		return new PostModel(
				post.getId(),
				post.getTitle(),
				post.getDescription(),
				editor
		);
	}
	
}
