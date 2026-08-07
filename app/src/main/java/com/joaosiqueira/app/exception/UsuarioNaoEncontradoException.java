package com.joaosiqueira.app.exception;

public class UsuarioNaoEncontradoException extends RuntimeException{
    public UsuarioNaoEncontradoException(String id) {
        super("Usuario nao encontrado com email: " + id);
    }
}
