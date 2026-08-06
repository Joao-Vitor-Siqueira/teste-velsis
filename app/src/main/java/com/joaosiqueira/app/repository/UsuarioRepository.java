package com.joaosiqueira.app.repository;

import com.joaosiqueira.app.model.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
