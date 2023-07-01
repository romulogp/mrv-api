package br.com.okfx.mrv.api.repository;

import br.com.okfx.mrv.api.model.Servico;
import br.com.okfx.mrv.api.repository.servico.ServicoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long>, ServicoRepositoryQuery {
}
