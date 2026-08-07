package com.joaosiqueira.app.service;

import com.joaosiqueira.app.dto.UsuarioRequest;
import com.joaosiqueira.app.dto.UsuarioResponse;
import com.joaosiqueira.app.exception.EmailJaCadastradoException;
import com.joaosiqueira.app.exception.UsuarioNaoEncontradoException;
import com.joaosiqueira.app.model.Usuario;
import com.joaosiqueira.app.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional
    public UsuarioResponse createUsuario(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())){
            throw new EmailJaCadastradoException(request.email());
        }
        String hashSenha = passwordEncoder.encode(request.senha());
        Usuario usuario = new Usuario(request.nome(), request.email(), hashSenha);
        Usuario usuarioCadastrado = usuarioRepository.save(usuario);
        emailService.enviarEmail(usuarioCadastrado.getEmail());

        return new UsuarioResponse(
                usuarioCadastrado.getId(),
                usuarioCadastrado.getNome(),
                usuarioCadastrado.getEmail());
    }

    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(String.valueOf(id)));

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
