package com.algaworks.examp.e.resilience4j.posts.client.editors;

import java.util.List;
import java.util.Optional;

public interface EditorClient {

	Optional<EditorModel> getOne(Long id);
	List<EditorModel> getAll();
	
}
