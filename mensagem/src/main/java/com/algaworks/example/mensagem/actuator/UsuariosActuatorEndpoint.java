package com.algaworks.example.mensagem.actuator;

import com.algaworks.example.mensagem.domain.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerEndpoint(id="usuarios")
@Component
public class UsuariosActuatorEndpoint {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<String> usuarioMetricas() {
        return Arrays.asList("usuariosAtivos");
    }

    @GetMapping("/usuarios-ativos")
    public Map<String, Object> usuarioAtivosMetricas() {
        final HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("quantidade", usuarioRepository.count());
        return hashMap;
    }
    
    @PostMapping
    public void usuarioAtivosMetricasEnvio(@RequestBody Map<String, Object> config) {
        System.out.println(config);
    }
}