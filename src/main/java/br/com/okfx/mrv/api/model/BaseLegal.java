package br.com.okfx.mrv.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "base_legal")
public class BaseLegal implements Serializable {
    private static final long serialVersionUID = 5835190352090319991L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "natureza_id")
    private Natureza natureza;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Natureza getNatureza() {
        return natureza;
    }

    public void setNatureza(Natureza natureza) {
        this.natureza = natureza;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseLegal baseLegal = (BaseLegal) o;
        return Objects.equals(id, baseLegal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
