package com.joaosiqueira.app.controller;

import com.joaosiqueira.app.dto.UsuarioRequest;
import com.joaosiqueira.app.dto.UsuarioResponse;
import com.joaosiqueira.app.exception.EmailJaCadastradoException;
import com.joaosiqueira.app.exception.UsuarioNaoEncontradoException;
import com.joaosiqueira.app.model.Usuario;
import com.joaosiqueira.app.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        try {
            UsuarioResponse usuarioCadastrado = usuarioService.buscarPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarPorId(id));
        } catch (UsuarioNaoEncontradoException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioResponse>> listarUsuarios(
            @RequestParam(required = false) String nome,
            Pageable pageable) {

        return ResponseEntity.ok(
                usuarioService.listarUsuarios(nome, pageable)
        );
    }
}
