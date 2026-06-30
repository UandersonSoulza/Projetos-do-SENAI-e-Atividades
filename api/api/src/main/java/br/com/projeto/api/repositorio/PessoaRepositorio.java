package br.com.projeto.api.repositorio;

import br.com.projeto.api.modelo.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepositorio extends JpaRepository<Pessoa, Integer> {
}

