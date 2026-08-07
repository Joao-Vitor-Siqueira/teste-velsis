package com.joaosiqueira.app.service;

import com.joaosiqueira.app.model.Usuario;
import com.joaosiqueira.app.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private String EMAIL = "joao@email.com";

    @Test
    void autenticacaoBemSucedida() {
        Usuario usuario = new Usuario("João Vitor", EMAIL,"123456");
        when(usuarioRepository.findByEmail(EMAIL)).thenReturn(Optional.of(usuario));

        UserDetails usuarioCadastrado = customUserDetailsService.loadUserByUsername(EMAIL);

        assertEquals(EMAIL, usuarioCadastrado.getUsername());
        assertEquals(usuario.getSenha(), usuarioCadastrado.getPassword());

        verify(usuarioRepository).findByEmail(EMAIL);
    }

    @Test
    void autenticacaoFalhou() {
        when(usuarioRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,() -> customUserDetailsService.loadUserByUsername(EMAIL));
        verify(usuarioRepository).findByEmail(EMAIL);
    }
}
