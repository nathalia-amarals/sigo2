package com.indtexbr.sigo.config.security;

import com.indtexbr.sigo.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {
        Usuario logado = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date((hoje.getTime() + 82000000));

        return Jwts.builder()
                .setIssuer("API")
                .setSubject(logado.getUsuario())
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, Base64.encodeBase64(secret.getBytes()))
                .compact();
    }

    public boolean isTokenValido(String token){
        try{
            Jwts.parser().setSigningKey(Base64.encodeBase64(secret.getBytes())).parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public String getUsUsuario(String token) {
        Claims claims = Jwts.parser().setSigningKey(Base64.encodeBase64(secret.getBytes())).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
