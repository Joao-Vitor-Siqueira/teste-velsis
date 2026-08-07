package com.joaosiqueira.app.service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import com.joaosiqueira.app.model.Usuario;
import com.joaosiqueira.app.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new
                UsernameNotFoundException("Usuário não existe com o email: " + email));
        return new User(usuario.getEmail(), usuario.getSenha(), List.of());
    }
}
