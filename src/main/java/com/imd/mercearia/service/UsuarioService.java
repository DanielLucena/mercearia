package com.imd.mercearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.imd.mercearia.exception.LoginJaCadastradoException;
import com.imd.mercearia.exception.SenhaInvalidaException;
import com.imd.mercearia.model.Usuario;
import com.imd.mercearia.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Component
public class UsuarioService implements UserDetailsService {
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        Usuario usuarioExistente = repository.findByLogin(usuario.getLogin()).orElse(null);
        if (usuarioExistente != null)
            throw new LoginJaCadastradoException(usuarioExistente.getLogin());
        return repository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario) {
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = passwordEncoder.matches(usuario.getSenha(), user.getPassword());

        if (senhasBatem) {
            return user;
        }

        throw new SenhaInvalidaException();
    }

    /*
     * Ele é responsável por carregar os detalhes do usuário com base no nome de
     * usuário fornecido.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Consulta ao repositório para obter o usuário com base no nome de usuário
        // fornecido
        Usuario usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        // muda totalmente
        // String[] roles = usuario.isAdmin() ? new String[] { "ADMIN", "USER" } : new
        // String[] { "USER" };
        String[] roles;
        switch (usuario.getRole()) {
            case CLIENTE: {
                roles = new String[] { "CLIENTE", "USER" };
            }
                break;
            case CAIXA: {
                roles = new String[] { "CAIXA", "USER" };
            }
                break;
            case REPOSITOR: {
                roles = new String[] { "REPOSITOR", "USER" };
            }
                break;
            case GERENTE: {
                roles = new String[] { "GERENTE", "CAIXA", "REPOSITOR", "CLIENTE", "USER" };
            }
                break;
            default: {
                roles = new String[] { "USER" };
            }

        }
        // String[] roles = new String[] { "ADMIN", "USER" };

        // Cria e retorna o objeto UserDetails com os detalhes do usuário
        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();

    }
}
