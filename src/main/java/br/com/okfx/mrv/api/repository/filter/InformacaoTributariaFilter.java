package br.com.okfx.mrv.api.repository.filter;

import br.com.okfx.mrv.api.model.Natureza;

public class InformacaoTributariaFilter {

    private String descricao;
    private String natureza;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }
}
