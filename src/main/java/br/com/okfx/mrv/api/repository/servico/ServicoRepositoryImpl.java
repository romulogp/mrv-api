package br.com.okfx.mrv.api.repository.servico;

import br.com.okfx.mrv.api.model.Servico;
import br.com.okfx.mrv.api.repository.filter.ServicoFilter;
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

public class ServicoRepositoryImpl implements ServicoRepositoryQuery {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Servico> filtrar(ServicoFilter servicoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Servico> criteria = builder.createQuery(Servico.class);
        Root<Servico> root = criteria.from(Servico.class);

        Predicate[] predicates = criarRestricoes(servicoFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Servico> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(servicoFilter));
    }


    private Predicate[] criarRestricoes(ServicoFilter servicoFilter, CriteriaBuilder builder, Root<Servico> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(servicoFilter.getCodigo())) {
            predicates.add(builder.like(builder.lower(root.get("codigo")), "%" + servicoFilter.getCodigo().toLowerCase() + "%"));
        }

        if (!ObjectUtils.isEmpty(servicoFilter.getNome())) {
            predicates.add(builder.like(builder.lower(root.get("nome")), "%" + servicoFilter.getNome().toLowerCase() + "%"));
        }

        if (!ObjectUtils.isEmpty(servicoFilter.getInformacaoGeral())) {
            predicates.add(builder.like(builder.lower(root.get("informacaoGeral")), "%" + servicoFilter.getInformacaoGeral().toLowerCase() + "%"));
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

    private Long total(ServicoFilter servicoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Servico> root = criteria.from(Servico.class);

        Predicate[] predicates = criarRestricoes(servicoFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }
}
