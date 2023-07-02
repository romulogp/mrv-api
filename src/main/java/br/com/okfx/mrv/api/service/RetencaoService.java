
package br.com.okfx.mrv.api.service;

import br.com.okfx.mrv.api.model.Retencao;
import br.com.okfx.mrv.api.repository.RetencaoRepository;
import br.com.okfx.mrv.api.repository.filter.RetencaoFilter;
import br.com.okfx.mrv.api.service.exception.RetencaoInexistente;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RetencaoService {
    @Autowired
    private RetencaoRepository repository;

    public List<Retencao> listarTodos() {
        return repository.findAll();
    }

    public Page<Retencao> filtrar(RetencaoFilter retencaoFilter, Pageable pageable) {
        return repository.filtrar(retencaoFilter, pageable);
    }

    public Optional<Retencao> buscarPorId(Long id) {
        Optional<Retencao> retencao = Optional.ofNullable(repository.findOne(id));
        if (!retencao.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }
        return retencao;
    }

    public Retencao salvar(Retencao retencao) {
        return repository.save(retencao);
    }

    public Retencao atualizar(Long id, Retencao retencao) {
        Retencao retencaoSalvo = buscarPorId(id).orElseThrow(() -> new RetencaoInexistente());
        BeanUtils.copyProperties(retencao, retencaoSalvo, "id");

        return repository.save(retencaoSalvo);
    }

    public void apagar(Long id) {
        repository.delete(id);
    }
}