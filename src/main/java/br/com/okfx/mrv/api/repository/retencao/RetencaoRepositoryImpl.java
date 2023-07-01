package br.com.okfx.mrv.api.repository.retencao;

import br.com.okfx.mrv.api.model.Retencao;
import br.com.okfx.mrv.api.repository.filter.RetencaoFilter;
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

public class RetencaoRepositoryImpl implements RetencaoRepositoryQuery {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Retencao> filtrar(RetencaoFilter retencaoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Retencao> criteria = builder.createQuery(Retencao.class);
        Root<Retencao> root = criteria.from(Retencao.class);

        Predicate[] predicates = criarRestricoes(retencaoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Retencao> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(retencaoFilter));
    }


    private Predicate[] criarRestricoes(RetencaoFilter retencaoFilter, CriteriaBuilder builder, Root<Retencao> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(retencaoFilter.getAliquota())) {
            predicates.add(builder.like(builder.lower(root.get("aliquota")), "%" + retencaoFilter.getAliquota().toLowerCase() + "%"));
        }

        if (!ObjectUtils.isEmpty(retencaoFilter.getFatoGerador())) {
            predicates.add(builder.like(builder.lower(root.get("fatoGerador")), "%" + retencaoFilter.getFatoGerador().toLowerCase() + "%"));
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

    private Long total(RetencaoFilter retencaoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Retencao> root = criteria.from(Retencao.class);

        Predicate[] predicates = criarRestricoes(retencaoFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
