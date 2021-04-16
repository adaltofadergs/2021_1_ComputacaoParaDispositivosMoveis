package br.pro.adalto.appfilmes;

public class Filme {

    public int id;
    public String nome, ano;

    public Filme() {

    }

    public Filme(String nome, String ano) {
        this.nome = nome;
        this.ano = ano;
    }

    public Filme(int id, String nome, String ano) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
    }

}
