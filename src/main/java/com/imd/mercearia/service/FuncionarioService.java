package com.imd.mercearia.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import com.imd.mercearia.model.Funcionario;
import com.imd.mercearia.repository.FuncionarioRepository;

@Component
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario salvarFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public void atualizarFuncionario(Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
    }

    public void deletarFuncionario(Integer id) {
        funcionarioRepository.deleteById(id);
    }

    public Optional<Funcionario> buscarFuncionarioPorId(Integer id) {
        return funcionarioRepository.findById(id);
    }

    public List<Funcionario> listaFuncionariosPorfiltro(Funcionario filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Funcionario> example = Example.of(filtro, matcher);
        return funcionarioRepository.findAll(example);
    }
}
