package br.com.example.atividade.modelo;

public class Produto {

    private String nome;
    private int idade;

    public Produto() {
    }

    public Produto(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", idade=" + idade +
                '}';
    }
}

