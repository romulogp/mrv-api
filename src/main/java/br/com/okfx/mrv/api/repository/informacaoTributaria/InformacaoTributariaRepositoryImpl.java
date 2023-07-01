package br.com.okfx.mrv.api.repository.informacaoTributaria;

import br.com.okfx.mrv.api.model.InformacaoTributaria;
import br.com.okfx.mrv.api.repository.filter.InformacaoTributariaFilter;
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

public class InformacaoTributariaRepositoryImpl implements InformacaoTributariaRepositoryQuery {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<InformacaoTributaria> filtrar(InformacaoTributariaFilter informacaoTributariaFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<InformacaoTributaria> criteria = builder.createQuery(InformacaoTributaria.class);
        Root<InformacaoTributaria> root = criteria.from(InformacaoTributaria.class);
        From<?, ?> naturezaJoin = root.join("natureza", JoinType.INNER);

        Predicate[] predicates = criarRestricoes(informacaoTributariaFilter, builder, root, naturezaJoin);
        criteria.where(predicates);

        TypedQuery<InformacaoTributaria> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(informacaoTributariaFilter));
    }


    private Predicate[] criarRestricoes(InformacaoTributariaFilter informacaoTributariaFilter, CriteriaBuilder builder, Root<InformacaoTributaria> root, From<?, ?> naturezaJoin) {
        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(informacaoTributariaFilter.getDescricao())) {
            predicates.add(builder.like(builder.lower(root.get("descricao")), "%" + informacaoTributariaFilter.getDescricao().toLowerCase() + "%"));
        }

        if (!ObjectUtils.isEmpty(informacaoTributariaFilter.getNatureza())) {
            predicates.add(builder.like(builder.lower(naturezaJoin.get("nome")), informacaoTributariaFilter.getNatureza()));
        }

        return predicates.toArray(new Predicate[0]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(InformacaoTributariaFilter informacaoTributariaFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<InformacaoTributaria> root = criteria.from(InformacaoTributaria.class);
        From<?, ?> naturezaJoin = root.join("natureza", JoinType.INNER);

        Predicate[] predicates = criarRestricoes(informacaoTributariaFilter, builder, root, naturezaJoin);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
