package com.algaworks.examp.e.algaworksresilience4jeditors.infra.database;

import com.algaworks.examp.e.algaworksresilience4jeditors.domain.Editor;
import com.algaworks.examp.e.algaworksresilience4jeditors.domain.EditorRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EditorRepositoryImpl implements EditorRepository {
	
	private static final List<Editor> editors = new ArrayList<>();
	private static long id = 1;
	
	//Inicializador dos statics
	static {
		editors.add(new Editor(nextId(), "Thiago"));
		editors.add(new Editor(nextId(), "Daniel"));
		editors.add(new Editor(nextId(), "Alex"));
	}
	
	@Override
	public void save(Editor editor) {
		editor.setId(nextId());
		editors.add(editor);
	}

	@Override
	public Optional<Editor> getOne(Long id) {
		return editors.stream().filter(e -> e.getId().equals(id)).findFirst();
	}

	@Override
	public List<Editor> getAll() {
		return new ArrayList<>(editors);
	}

	private static long nextId() {
		return id++;
	}
}

