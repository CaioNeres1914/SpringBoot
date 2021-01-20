package io.github.curso.security.jwt;

import io.github.curso.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtServices {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${secutiry.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public Claims decoderToken(String token) throws ExpiredJwtException {

        return Jwts.parser()
                   .setSigningKey(chaveAssinatura)
                   .parseClaimsJws(token)
                   .getBody();
    }

    public Boolean tokenValido(String token) {

        try {
              Claims claims = decoderToken(token);
              Date dateExpiracao = claims.getExpiration();
              LocalDateTime localDateTime = dateExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
              return !LocalDateTime.now().isAfter(localDateTime);
        } catch (Exception ex){
              return false;
        }
    }

    public String obterLoginUser(String token) throws ExpiredJwtException{

        return (String) decoderToken(token).getSubject();
    }

    public String gerarToken(Usuario usuario){

        Long expString = Long.valueOf(expiracao);
        LocalDateTime expiracao = LocalDateTime.now().plusMinutes(expString);
        Date data = Date.from(expiracao.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                   .setSubject(usuario.getLogin())
                   .setExpiration(data)
                   .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                   .compact();
    }

}
