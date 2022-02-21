package com.algaworks.examp.e.resilience4j.posts.infra.database;

import com.algaworks.examp.e.resilience4j.posts.domain.Post;
import com.algaworks.examp.e.resilience4j.posts.domain.PostRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PostRepositoryImpl implements PostRepository {

	private static final List<Post> posts = new ArrayList<>();
	private static long id = 1;

	//Inicializador dos statics
	static {
		posts.add(new Post(nextId(), "Como iniciar em Java", "", 1L));
		posts.add(new Post(nextId(), "ReactJS com TypeScript", "", 2L));
		posts.add(new Post(nextId(), "Resilience4j", "", 3L));
	}

	@Override
	public void save(Post post) {
		post.setId(nextId());
		posts.add(post);
	}

	@Override
	public Optional<Post> getOne(Long id) {
		return posts.stream().filter(e -> e.getId().equals(id)).findFirst();
	}

	@Override
	public List<Post> getAll() {
		return new ArrayList<>(posts);
	}

	private static long nextId() {
		return id++;
	}
}
