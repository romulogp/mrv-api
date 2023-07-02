package br.com.okfx.mrv.api.repository.retencao;

import br.com.okfx.mrv.api.model.Retencao;
import br.com.okfx.mrv.api.repository.filter.RetencaoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RetencaoRepositoryQuery {

    public Page<Retencao> filtrar(RetencaoFilter retencaoFilter, Pageable pageable);

}