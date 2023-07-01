package br.com.okfx.mrv.api.repository.filter;


public class RetencaoFilter {

    private String aliquota;
    private String fatoGerador;

    public String getAliquota() {
        return aliquota;
    }

    public void setAliquota(String aliquota) {
        this.aliquota = aliquota;
    }

    public String getFatoGerador() {
        return fatoGerador;
    }

    public void setFatoGerador(String fatoGerador) {
        this.fatoGerador = fatoGerador;
    }
}
