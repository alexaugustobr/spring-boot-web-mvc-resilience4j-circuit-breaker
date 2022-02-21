package com.algaworks.examp.e.resilience4j.posts.domain;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
	
	void save(Post post);
	Optional<Post> getOne(Long id);
	List<Post> getAll();
	
}
