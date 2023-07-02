package br.com.okfx.mrv.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tributo")
public class Tributo implements Serializable {
    private static final long serialVersionUID = -7945131760273362631L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 45)
    private String nome;

    @Column(name = "ordem_apresentacao")
    private int ordemApresentacao;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "informacao_tributaria_id")
    private InformacaoTributaria informacaoTributaria;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "base_legal_id")
    private BaseLegal baseLegal;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "retencao_id")
    private Retencao retencao;

    @ManyToMany
    @JoinTable(name = "servico_tributo", joinColumns =
            {@JoinColumn(name = "tributo_id")}, inverseJoinColumns =
            {@JoinColumn(name = "servico_id")})
    private List<Servico> servicos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public InformacaoTributaria getInformacaoTributaria() {
        return informacaoTributaria;
    }

    public void setInformacaoTributaria(InformacaoTributaria informacaoTributaria) {
        this.informacaoTributaria = informacaoTributaria;
    }

    public BaseLegal getBaseLegal() {
        return baseLegal;
    }

    public void setBaseLegal(BaseLegal baseLegal) {
        this.baseLegal = baseLegal;
    }

    public Retencao getRetencao() {
        return retencao;
    }

    public void setRetencao(Retencao retencao) {
        this.retencao = retencao;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tributo tributo = (Tributo) o;
        return Objects.equals(id, tributo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}