package com.algaworks.example.config;

import com.algaworks.example.usuario.domain.Usuario;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		basePackages = "com.algaworks.example.usuario", 
		entityManagerFactoryRef = "usuariosEntityManagerFactory",
		transactionManagerRef = "usuariosTransactionManager"
)
public class UsuarioJpaConfiguration {

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean usuariosEntityManagerFactory(
			@Qualifier("usuariosDataSource") DataSource usuariosDataSource, 
			EntityManagerFactoryBuilder builder) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		
		return builder.dataSource(usuariosDataSource)
				.packages(Usuario.class)
				.properties(properties)
				.build();
	}

	@Bean
	public PlatformTransactionManager usuariosTransactionManager(
			@Qualifier("usuariosEntityManagerFactory") LocalContainerEntityManagerFactoryBean usuariosEMF) {
		return new JpaTransactionManager(Objects.requireNonNull(usuariosEMF.getObject()));
	}
	
}
