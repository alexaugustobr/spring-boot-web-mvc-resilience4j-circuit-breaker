package com.algaworks.examp.e.resilience4j.posts.api;

import com.algaworks.examp.e.resilience4j.posts.client.editors.EditorClient;
import com.algaworks.examp.e.resilience4j.posts.client.editors.EditorModel;
import com.algaworks.examp.e.resilience4j.posts.domain.Post;
import com.algaworks.examp.e.resilience4j.posts.domain.PostRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	private final PostRepository posts;
	private final EditorClient editors;

	public PostController(PostRepository posts, EditorClient editors) {
		this.posts = posts;
		this.editors = editors;
	}

	@GetMapping
	public List<PostModel> getAll() {
		return posts.getAll()
				.stream()
				.map(this::convertPostToModel)
				.collect(Collectors.toList());
	}

	@GetMapping("/{postId}")
	public PostModel getOne(@PathVariable Long postId) {
		return posts.getOne(postId)
				.map(this::convertPostToModel)
				.orElseThrow(ResourceNotFoundException::new);
	}

	private PostModel convertPostToModel(Post post) {
		return findEditor(post.getEditorId())
				.map(editor-> PostModel.of(post, editor))
				.orElseThrow();
	}

	private Optional<EditorModel> findEditor(Long editorId) {
		return editors.getOne(editorId);
	}
}
