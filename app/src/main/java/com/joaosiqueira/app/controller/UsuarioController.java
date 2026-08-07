package com.joaosiqueira.app.controller;

import com.joaosiqueira.app.dto.UsuarioRequest;
import com.joaosiqueira.app.dto.UsuarioResponse;
import com.joaosiqueira.app.exception.EmailJaCadastradoException;
import com.joaosiqueira.app.model.Usuario;
import com.joaosiqueira.app.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponse> createUsuario(@RequestBody UsuarioRequest request) {
        try {
            UsuarioResponse usuarioCadastrado = usuarioService.createUsuario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCadastrado);
        } catch (EmailJaCadastradoException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
