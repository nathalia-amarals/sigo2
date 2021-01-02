package com.indtexbr.sigo.repository;

import com.indtexbr.sigo.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario,String> {
    public Optional<Usuario> findByUsuario(String usuario);
}
