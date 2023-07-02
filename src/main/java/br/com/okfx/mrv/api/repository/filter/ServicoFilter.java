package br.com.okfx.mrv.api.repository.filter;

public class ServicoFilter {

    private String codigo;
    private String nome;
    private String informacaoGeral;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getInformacaoGeral() {
        return informacaoGeral;
    }

    public void setInformacaoGeral(String informacaoGeral) {
        this.informacaoGeral = informacaoGeral;
    }
}
