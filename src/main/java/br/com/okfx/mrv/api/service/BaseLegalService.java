package br.com.okfx.mrv.api.service;

import br.com.okfx.mrv.api.model.BaseLegal;
import br.com.okfx.mrv.api.repository.BaseLegalRepository;
import br.com.okfx.mrv.api.repository.filter.BaseLegalFilter;
import br.com.okfx.mrv.api.service.exception.BaseLegalInexistente;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BaseLegalService {
    @Autowired
    private BaseLegalRepository repository;

    public List<BaseLegal> listarTodos() {
        return repository.findAll();
    }

    public Page<BaseLegal> filtrar(BaseLegalFilter baseLegalFilter, Pageable pageable) {
        return repository.filtrar(baseLegalFilter, pageable);
    }

    public Optional<BaseLegal> buscarPorCodigo(Long codigo) {
        Optional<BaseLegal> baseLegal = Optional.ofNullable(repository.findOne(codigo));
        if (!baseLegal.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }
        return baseLegal;
    }

    public BaseLegal salvar(BaseLegal baseLegal) {
        return repository.save(baseLegal);
    }

    public BaseLegal atualizar(Long codigo, BaseLegal baseLegal) {
        BaseLegal baseLegalSalvo = buscarPorCodigo(codigo).orElseThrow(() -> new BaseLegalInexistente());
        BeanUtils.copyProperties(baseLegal,baseLegalSalvo,"codigo");

        return repository.save(baseLegalSalvo);
    }

    public void apagar(Long codigo) {
        repository.delete(codigo);
    }


}
