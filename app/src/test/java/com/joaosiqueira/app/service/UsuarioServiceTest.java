package com.joaosiqueira.app.service;

import com.joaosiqueira.app.dto.UsuarioRequest;
import com.joaosiqueira.app.dto.UsuarioResponse;
import com.joaosiqueira.app.exception.EmailJaCadastradoException;
import com.joaosiqueira.app.exception.UsuarioNaoEncontradoException;
import com.joaosiqueira.app.model.Usuario;
import com.joaosiqueira.app.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

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

    private final String NOME = "João";
    private final String EMAIL = "joao@email.com";
    private final String SENHA = "123456";

    private Usuario criarNomeUsuario(String nome) {
        Usuario usuario = new Usuario(
                nome,
                nome.toLowerCase().replace(" ", "") + "@email.com",
                "senha"
        );
        return usuario;
    }

    @Test
    void cadastroDeUsuarioComSucesso() {

        UsuarioRequest request =
                new UsuarioRequest(NOME, EMAIL, SENHA);

        when(usuarioRepository.existsByEmail(request.email()))
                .thenReturn(false);

        when(passwordEncoder.encode(request.senha()))
                .thenReturn("hashSenha");

        Usuario usuarioSalvo =
                new Usuario(NOME, EMAIL, "hashSenha");

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

    @Test
    void emailJaCadastrado() {
        UsuarioRequest request =
                new UsuarioRequest(NOME, EMAIL, SENHA);

        when(usuarioRepository.existsByEmail(request.email()))
                .thenReturn(true);

        assertThrows(
                EmailJaCadastradoException.class,
                () -> usuarioService.createUsuario(request)
        );

        verify(usuarioRepository, never()).save(any());
        verify(emailService, never()).enviarEmail(any());
    }

    @Test
    void buscarUsuarioExistentePorId() {
        Usuario usuario = new Usuario(NOME, EMAIL, SENHA);
        ReflectionTestUtils.setField(usuario, "id", 1L);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        UsuarioResponse response =
                usuarioService.buscarPorId(1L);

        assertEquals(1L, response.id());
        assertEquals("João", response.nome());

    }

    @Test
    void buscarUsuarioInexistentePorId() {
        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> usuarioService.buscarPorId(1L)
        );
    }

    @Test
    void listarUsuariosSemFiltro() {

        Pageable pageable = PageRequest.of(0, 10);

        Usuario usuario1 = criarNomeUsuario("João Vitor");
        Usuario usuario2 = criarNomeUsuario("Pedro Silva");
        Usuario usuario3 = criarNomeUsuario("Maria Silva");

        ReflectionTestUtils.setField(usuario1, "id", 1L);
        ReflectionTestUtils.setField(usuario2, "id", 2L);
        ReflectionTestUtils.setField(usuario3, "id", 3L);

        Page<Usuario> paginaUsuarios =
                new PageImpl<>(List.of(usuario1, usuario2, usuario3));


        when(usuarioRepository.findAll(pageable))
                .thenReturn(paginaUsuarios);


        Page<UsuarioResponse> response =
                usuarioService.listarUsuarios(null, pageable);


        assertEquals(3, response.getTotalElements());
        assertEquals("João Vitor",
                response.getContent().get(0).nome());
        assertEquals("Pedro Silva",
                response.getContent().get(1).nome());
        assertEquals("Maria Silva",
                response.getContent().get(2).nome());

        verify(usuarioRepository).findAll(pageable);
    }
}
