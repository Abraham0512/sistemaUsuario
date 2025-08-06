package com.demo.login.iniciosesion.repository;

import com.demo.login.iniciosesion.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findUsuarioByEmailAndActivoTrue(String email);

}
