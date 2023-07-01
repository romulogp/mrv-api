package br.com.okfx.mrv.api.repository.natureza;

import br.com.okfx.mrv.api.model.Natureza;
import br.com.okfx.mrv.api.repository.filter.NaturezaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class NaturezaRepositoryImpl implements NaturezaRepositoryQuery {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Natureza> filtrar(NaturezaFilter naturezaFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Natureza> criteria = builder.createQuery(Natureza.class);
        Root<Natureza> root = criteria.from(Natureza.class);

        Predicate[] predicates = criarRestricoes(naturezaFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Natureza> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(naturezaFilter));
    }


    private Predicate[] criarRestricoes(NaturezaFilter naturezaFilter, CriteriaBuilder builder, Root<Natureza> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(naturezaFilter.getNome())) {
            predicates.add(builder.like(builder.lower(root.get("nome")), "%" + naturezaFilter.getNome().toLowerCase() + "%"));
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

    private Long total(NaturezaFilter naturezaFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Natureza> root = criteria.from(Natureza.class);

        Predicate[] predicates = criarRestricoes(naturezaFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
