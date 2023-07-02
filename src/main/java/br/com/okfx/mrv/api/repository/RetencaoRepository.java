package br.com.okfx.mrv.api.repository;

import br.com.okfx.mrv.api.model.Retencao;
import br.com.okfx.mrv.api.repository.retencao.RetencaoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetencaoRepository extends JpaRepository<Retencao, Long>, RetencaoRepositoryQuery {}
