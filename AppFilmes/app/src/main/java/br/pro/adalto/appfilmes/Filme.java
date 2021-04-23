package br.pro.adalto.appfilmes;

public class Filme {

    public static final int ANO_MINIMO = 2000;

    public int id;
    public String nome;
    private int ano;

    public Filme() {

    }

    public Filme(String nome, int ano) {
        this.nome = nome;
        this.setAno( ano );
    }

    public Filme(int id, String nome, int ano) {
        this.id = id;
        this.nome = nome;
        this.setAno(ano);
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        if( ano >= ANO_MINIMO )
            this.ano = ano;

    }

    @Override
    public String toString() {
        return id + " - " + nome + " | " + ano;
    }
}
