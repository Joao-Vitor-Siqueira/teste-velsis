package com.joaosiqueira.app.config;

import com.joaosiqueira.app.model.Usuario;
import com.joaosiqueira.app.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {

            if (usuarioRepository.count() == 0) {

                Usuario usuario1 = new Usuario(
                        "Gabriel Costa",
                        "gabriel@email.com",
                        passwordEncoder.encode("123456")
                );

                Usuario usuario2 = new Usuario(
                        "Lucas Leite",
                        "lucas@email.com",
                        passwordEncoder.encode("123456")
                );

                Usuario usuario3 = new Usuario(
                        "Alan Silva",
                        "alan@email.com",
                        passwordEncoder.encode("123456")
                );

                usuarioRepository.saveAll(List.of(usuario1, usuario2, usuario3));
            }
        };
    }
}
