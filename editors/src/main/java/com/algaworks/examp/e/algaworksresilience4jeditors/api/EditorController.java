package com.algaworks.examp.e.algaworksresilience4jeditors.api;

import com.algaworks.examp.e.algaworksresilience4jeditors.domain.EditorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/editors")
public class EditorController {
	
	private final EditorRepository editors;

	public EditorController(EditorRepository editors) {
		this.editors = editors;
	}

	@GetMapping
	public List<EditorModel> getAll() {
		return editors.getAll()
				.stream()
				.map(EditorModel::of)
				.collect(Collectors.toList());
	}
	
	@GetMapping("/{editorId}")
	public EditorModel getOne(@PathVariable Long editorId) {
		return EditorModel.of(editors.getOne(editorId)
				.orElseThrow(ResourceNotFoundException::new));
	}
}
