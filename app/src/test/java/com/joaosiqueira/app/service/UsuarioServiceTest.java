package com.joaosiqueira.app.service;

import com.joaosiqueira.app.dto.UsuarioRequest;
import com.joaosiqueira.app.dto.UsuarioResponse;
import com.joaosiqueira.app.exception.EmailJaCadastradoException;
import com.joaosiqueira.app.model.Usuario;
import com.joaosiqueira.app.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void cadastroDeUsuarioComSucesso() {

        UsuarioRequest request =
                new UsuarioRequest("João", "joao@email.com", "123456");

        when(usuarioRepository.existsByEmail(request.email()))
                .thenReturn(false);

        when(passwordEncoder.encode(request.senha()))
                .thenReturn("hashSenha");

        Usuario usuarioSalvo =
                new Usuario("João", "joao@email.com", "hashSenha");

        ReflectionTestUtils.setField(usuarioSalvo, "id", 1L);

        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuarioSalvo);

        UsuarioResponse response = usuarioService.createUsuario(request);

        assertEquals(1L, response.id());
        assertEquals("João", response.nome());
        assertEquals("joao@email.com", response.email());

        verify(usuarioRepository).save(any(Usuario.class));
        verify(emailService).enviarEmail("joao@email.com");
    }
}
