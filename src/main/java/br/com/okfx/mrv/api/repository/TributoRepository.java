package br.com.okfx.mrv.api.repository;

import br.com.okfx.mrv.api.model.Tributo;
import br.com.okfx.mrv.api.model.Tributo;
import br.com.okfx.mrv.api.repository.tributo.TributoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TributoRepository extends JpaRepository<Tributo, Long>, TributoRepositoryQuery {
}
