package com.algaworks.example.produto.client.avaliacoes;

import java.util.List;

public interface AvaliacaoClient {

	List<AvaliacaoModel> buscarTodosPorProduto(Long productId);
}
