package br.com.projeto.api.controlador;

import br.com.projeto.api.modelo.Pessoa;
import br.com.projeto.api.repositorio.PessoaRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PessoaControlador {

    private final PessoaRepositorio pessoaRepositorio;

    public PessoaControlador(PessoaRepositorio pessoaRepositorio) {
        this.pessoaRepositorio = pessoaRepositorio;
    }

    @GetMapping
    public List<Pessoa> listarPessoas() {
        return pessoaRepositorio.findAll();
    }

    @PostMapping
    public Pessoa cadastrar(@RequestBody Pessoa pessoa) {
        // código pode vir nulo no cadastro
        pessoa.setCodigo(null);
        return pessoaRepositorio.save(pessoa);
    }

    @PutMapping
    public Pessoa atualizar(@RequestBody Pessoa pessoa) {
        if (pessoa.getCodigo() == null) {
            throw new IllegalArgumentException("codigo é obrigatório para atualizar");
        }
        return pessoaRepositorio.save(pessoa);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> remover(@PathVariable Integer codigo) {
        if (!pessoaRepositorio.existsById(codigo)) {
            return ResponseEntity.notFound().build();
        }
        pessoaRepositorio.deleteById(codigo);
        return ResponseEntity.ok().build();
    }
}

