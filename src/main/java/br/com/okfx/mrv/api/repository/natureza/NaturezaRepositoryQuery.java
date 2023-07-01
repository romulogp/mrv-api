package br.com.okfx.mrv.api.repository.natureza;

import br.com.okfx.mrv.api.model.Natureza;
import br.com.okfx.mrv.api.repository.filter.NaturezaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NaturezaRepositoryQuery {

    public Page<Natureza> filtrar(NaturezaFilter naturezaFilter, Pageable pageable);

}