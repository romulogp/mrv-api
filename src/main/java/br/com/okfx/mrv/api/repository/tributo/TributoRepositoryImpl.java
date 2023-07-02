package br.com.okfx.mrv.api.repository.tributo;

import br.com.okfx.mrv.api.model.Tributo;
import br.com.okfx.mrv.api.repository.filter.TributoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class TributoRepositoryImpl implements TributoRepositoryQuery {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Tributo> filtrar(TributoFilter tributoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Tributo> criteria = builder.createQuery(Tributo.class);
        Root<Tributo> root = criteria.from(Tributo.class);
        From<?, ?> baseLegalJoin = root.join("baseLegal", JoinType.INNER);
        From<?, ?> retencaoJoin = root.join("retencao", JoinType.INNER);
        From<?, ?> informacaoTributariaJoin = root.join("informacaoTributaria", JoinType.INNER);

        Predicate[] predicates = criarRestricoes(tributoFilter, builder, root, baseLegalJoin, retencaoJoin, informacaoTributariaJoin);
        criteria.where(predicates);

        TypedQuery<Tributo> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable,baseLegalJoin,retencaoJoin,informacaoTributariaJoin);

        return new PageImpl<>(query.getResultList(), pageable, total(tributoFilter));
    }

    private Predicate[] criarRestricoes(TributoFilter tributoFilter, CriteriaBuilder builder, Root<Tributo> root, From<?, ?> baseLegalJoin, From<?, ?> retencaoJoin, From<?, ?> informacaoTributariaJoin) {
        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(tributoFilter.getNome())) {
            predicates.add(builder.like(builder.lower(root.get("nome")), "%" + tributoFilter.getNome().toLowerCase() + "%"));
        }

        if (!ObjectUtils.isEmpty(tributoFilter.getOrdemApresentacao()) && tributoFilter.getOrdemApresentacao() != 0) {
            predicates.add(builder.equal(builder.lower(root.get("ordemApresentacao")), tributoFilter.getOrdemApresentacao()));
        }
        return predicates.toArray(new Predicate[0]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable, From<?, ?> baseLegalJoin, From<?, ?> retencaoJoin, From<?, ?> informacaoTributariaJoin) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(TributoFilter tributoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Tributo> root = criteria.from(Tributo.class);
        From<?, ?> baseLegalJoin = root.join("baseLegal", JoinType.INNER);
        From<?, ?> retencaoJoin = root.join("retencao", JoinType.INNER);
        From<?, ?> informacaoTributariaJoin = root.join("informacaoTributaria", JoinType.INNER);

        Predicate[] predicates = criarRestricoes(tributoFilter, builder, root,baseLegalJoin,retencaoJoin,informacaoTributariaJoin);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
