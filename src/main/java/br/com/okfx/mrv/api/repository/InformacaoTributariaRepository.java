package br.com.okfx.mrv.api.repository;

import br.com.okfx.mrv.api.model.InformacaoTributaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformacaoTributariaRepository extends JpaRepository<InformacaoTributaria, Long> {}
