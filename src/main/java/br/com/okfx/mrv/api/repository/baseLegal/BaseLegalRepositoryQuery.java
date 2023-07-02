package br.com.okfx.mrv.api.repository.baseLegal;

import br.com.okfx.mrv.api.model.BaseLegal;
import br.com.okfx.mrv.api.repository.filter.BaseLegalFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseLegalRepositoryQuery {

    public Page<BaseLegal> filtrar(BaseLegalFilter baseLegalFilter, Pageable pageable);

}