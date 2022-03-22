package com.algaworks.example.avaliacao.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SegurancaService {

	public String getUsuario() {
		var context = SecurityContextHolder.getContext();
		if (context == null || context.getAuthentication() == null) {
			return "anonimo";
		}
		return context.getAuthentication().getName();
	}

}