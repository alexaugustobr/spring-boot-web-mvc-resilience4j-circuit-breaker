package com.algaworks.examp.e.algaworksresilience4jeditors.domain;

import java.util.List;
import java.util.Optional;

public interface EditorRepository {
	
	void save(Editor editor);
	Optional<Editor> getOne(Long id);
	List<Editor> getAll();
	
}
