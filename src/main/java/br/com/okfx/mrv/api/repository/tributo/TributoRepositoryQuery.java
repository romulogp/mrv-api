package br.com.okfx.mrv.api.repository.tributo;

import br.com.okfx.mrv.api.model.Tributo;
import br.com.okfx.mrv.api.repository.filter.TributoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TributoRepositoryQuery {

    public Page<Tributo> filtrar(TributoFilter tributoFilter, Pageable pageable);

}