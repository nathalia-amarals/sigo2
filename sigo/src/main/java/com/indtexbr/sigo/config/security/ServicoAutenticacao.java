package com.indtexbr.sigo.config.security;

import com.indtexbr.sigo.entity.Usuario;
import com.indtexbr.sigo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ServicoAutenticacao implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Usuario usuarioDoc = usuarioRepository.findByUsuario(usuario).get();
        return usuarioDoc;
    }


}
