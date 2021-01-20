package io.github.curso.rest.controller;

import io.github.curso.domain.dto.CredenciaisDTO;
import io.github.curso.domain.dto.TokenDTO;
import io.github.curso.domain.entity.Usuario;
import io.github.curso.domain.service.impl.UserDetailService;
import io.github.curso.exception.SenhaInvalidaException;
import io.github.curso.security.jwt.JwtServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtServices jwt;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario cadastraUser(@RequestBody  Usuario usuario){

        return userDetailService.save(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciaisDTO){

        try {
            Usuario usuario = Usuario.builder()
                    .login(credenciaisDTO.getLogin())
                    .senha(credenciaisDTO.getSenha()).build();
            UserDetails autenticar = userDetailService.autenticar(usuario);
            String token = jwt.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(),token);

        }catch (UsernameNotFoundException  | SenhaInvalidaException s) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, s.getMessage());
        }

    }

}
