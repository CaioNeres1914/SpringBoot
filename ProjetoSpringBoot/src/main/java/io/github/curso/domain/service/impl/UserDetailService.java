package io.github.curso.domain.service.impl;

import io.github.curso.domain.entity.Usuario;
import io.github.curso.domain.repositorio.UsuarioRepository;
import io.github.curso.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioRepository usuarioRepository;

    public UserDetails autenticar (Usuario usuario){

        UserDetails userDetails = loadUserByUsername(usuario.getLogin());
        Boolean senhasIguais = passwordEncoder.matches(usuario.getSenha(), userDetails.getPassword());
        if (senhasIguais)
            return userDetails;

        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Usuario usuario =  usuarioRepository.findByLogin(username)
                                           .orElseThrow(() -> new UsernameNotFoundException("Usuario não existe."));

       String[] roles = usuario.getAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

       return User.builder()
                  .username(usuario.getLogin())
                  .password(usuario.getSenha())
                  .roles(roles)
                  .build();
    }

    @Transactional
    public Usuario save(Usuario usuario) {

        Usuario user = new Usuario();
        user.setSenha(passwordEncoder.encode(usuario.getSenha()));
        user.setLogin(usuario.getLogin());
        user.setAdmin(usuario.getAdmin());

        return usuarioRepository.save(user);

    }
}
