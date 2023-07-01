package br.com.okfx.mrv.api.repository.informacaoTributaria;

import br.com.okfx.mrv.api.model.BaseLegal;
import br.com.okfx.mrv.api.model.InformacaoTributaria;
import br.com.okfx.mrv.api.repository.filter.BaseLegalFilter;
import br.com.okfx.mrv.api.repository.filter.InformacaoTributariaFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InformacaoTributariaRepositoryQuery {

    public Page<InformacaoTributaria> filtrar(InformacaoTributariaFilter baseLegalFilter, Pageable pageable);

}