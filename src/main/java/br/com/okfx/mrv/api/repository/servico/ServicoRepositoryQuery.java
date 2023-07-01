package br.com.okfx.mrv.api.repository.servico;

import br.com.okfx.mrv.api.model.Servico;
import br.com.okfx.mrv.api.repository.filter.ServicoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServicoRepositoryQuery {

    public Page<Servico> filtrar(ServicoFilter servicoFilter, Pageable pageable);

}