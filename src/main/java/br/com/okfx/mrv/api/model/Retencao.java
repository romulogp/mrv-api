package br.com.okfx.mrv.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "retencao")
public class Retencao implements Serializable {
    private static final long serialVersionUID = -561665691048946139L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 45)
    private String aliquota;

    @Size(min = 5, max = 45)
    @NotNull
    @Column(name = "fato_gerador")
    private String fatoGerador;

    @Size(max = 255)
    private String observacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Retencao retencao = (Retencao) o;
        return Objects.equals(id, retencao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
