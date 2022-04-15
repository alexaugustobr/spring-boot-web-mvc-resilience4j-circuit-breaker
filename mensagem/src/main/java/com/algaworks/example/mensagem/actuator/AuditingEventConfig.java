package com.algaworks.example.mensagem.actuator;

import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditingEventConfig {

   @Bean
   public AuditEventRepository auditEventRepository() {
      return new InMemoryAuditEventRepository();
   }

}