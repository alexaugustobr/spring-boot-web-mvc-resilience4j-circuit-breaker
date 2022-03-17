package com.algaworks.example.produto;

import com.algaworks.example.produto.domain.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

class AlgaWorksApplicationTests {

	@Test
	void contextLoads() {
		Produto produto = new Produto();
		produto.setNome("Notebook");

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<Produto>> violations = validator.validate(produto);

		for (ConstraintViolation<Produto> violation : violations) {
			System.out.println(violation.getPropertyPath());
			System.out.println(violation.getPropertyPath());
		}

		Assertions.assertTrue(violations.isEmpty());
		
	}
	
//	<dependency>
//			<groupId>org.springframework.boot</groupId>
//			<artifactId>spring-boot-starter-validation</artifactId>
//	</dependency>

}
