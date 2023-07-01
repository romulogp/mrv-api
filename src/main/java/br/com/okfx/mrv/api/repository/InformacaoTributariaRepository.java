package br.com.okfx.mrv.api.repository;

import br.com.okfx.mrv.api.model.InformacaoTributaria;
import br.com.okfx.mrv.api.repository.informacaoTributaria.InformacaoTributariaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformacaoTributariaRepository extends JpaRepository<InformacaoTributaria, Long>, InformacaoTributariaRepositoryQuery {}
