package com.joaosiqueira.app.repository;
import com.joaosiqueira.app.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    boolean existsByEmail(String email);
}
