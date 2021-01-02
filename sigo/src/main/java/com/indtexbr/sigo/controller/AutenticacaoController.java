package com.indtexbr.sigo.controller;

import com.indtexbr.sigo.config.security.TokenService;
import com.indtexbr.sigo.entity.LoginForm;
import com.indtexbr.sigo.entity.Usuario;
import com.indtexbr.sigo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("autenticar")
    public ResponseEntity autenticar(@RequestBody @Validated LoginForm form){
        UsernamePasswordAuthenticationToken dadosLogin = form.converter();
        try{
            Authentication authentication = authenticationManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e){

            System.out.println(e);
            System.out.println(dadosLogin.getCredentials());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("criar")
    public ResponseEntity criarUsuario(@RequestBody LoginForm form){
        Usuario usuario = new Usuario();
        usuario.setUsuario(form.getUsuario());
        usuario.setSenha(new BCryptPasswordEncoder().encode(form.getSenha()));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok().build();
    }
}
