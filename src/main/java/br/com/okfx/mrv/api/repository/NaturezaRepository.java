package br.com.okfx.mrv.api.repository;

import br.com.okfx.mrv.api.model.Natureza;
import br.com.okfx.mrv.api.repository.natureza.NaturezaRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaturezaRepository extends JpaRepository<Natureza, Long>, NaturezaRepositoryQuery {}



