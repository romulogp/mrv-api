package br.com.okfx.mrv.api.repository;

import br.com.okfx.mrv.api.model.Natureza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaturezaRepository extends JpaRepository<Natureza, Long> {}
