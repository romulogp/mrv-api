package br.com.okfx.mrv.api.service;

import br.com.okfx.mrv.api.model.Tributo;
import br.com.okfx.mrv.api.repository.TributoRepository;
import br.com.okfx.mrv.api.repository.filter.TributoFilter;
import br.com.okfx.mrv.api.service.exception.TributoInexistente;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TributoService {
    @Autowired
    private TributoRepository repository;

    public List<Tributo> listarTodos() {
        return repository.findAll();
    }

    public Page<Tributo> filtrar(TributoFilter tributoFilter, Pageable pageable) {
        return repository.filtrar(tributoFilter, pageable);
    }

    public Optional<Tributo> buscarPorId(Long id) {
        Optional<Tributo> tributo = Optional.ofNullable(repository.findOne(id));
        if (!tributo.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }
        return tributo;
    }

    public Tributo salvar(Tributo tributo) {
        return repository.save(tributo);
    }

    public Tributo atualizar(Long id, Tributo tributo) {
        Tributo tributoSalvo = buscarPorId(id).orElseThrow(() -> new TributoInexistente());
        BeanUtils.copyProperties(tributo, tributoSalvo, "id");

        return repository.save(tributoSalvo);
    }

    public void apagar(Long id) {
        repository.delete(id);
    }
}

