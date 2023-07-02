package br.com.okfx.mrv.api.repository.filter;

public class TributoFilter {


    private String nome;
    private int ordemApresentacao;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getOrdemApresentacao() {
        return ordemApresentacao;
    }

    public void setOrdemApresentacao(int ordemApresentacao) {
        this.ordemApresentacao = ordemApresentacao;
    }

}