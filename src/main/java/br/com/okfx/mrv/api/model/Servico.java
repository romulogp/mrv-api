package br.com.okfx.mrv.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "servico")
public class Servico implements Serializable {
    private static final long serialVersionUID = -5333414159769444422L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 45, min = 3)
    private String codigo;

    @NotNull
    private String nome;

    @NotNull
    @Column(name = "informacao_geral")
    private String informacaoGeral;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "servico_tributo", joinColumns =
            {@JoinColumn(name = "tributo_id")}, inverseJoinColumns =
            {@JoinColumn(name = "servico_id")})
    private List<Tributo> tributos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Tributo> getTributos() {
        return tributos;
    }

    public void setTributos(List<Tributo> tributos) {
        this.tributos = tributos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servico servico = (Servico) o;
        return Objects.equals(id, servico.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}