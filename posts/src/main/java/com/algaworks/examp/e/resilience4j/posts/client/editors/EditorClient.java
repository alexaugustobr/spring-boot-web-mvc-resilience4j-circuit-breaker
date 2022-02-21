package com.algaworks.examp.e.resilience4j.posts.client.editors;

import java.util.List;

public interface EditorClient {

	EditorModel getOne(Long id);
	List<EditorModel> getAll();
	
}
