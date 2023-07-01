package br.com.okfx.mrv.api.service;

import br.com.okfx.mrv.api.model.InformacaoTributaria;
import br.com.okfx.mrv.api.repository.InformacaoTributariaRepository;
import br.com.okfx.mrv.api.repository.filter.InformacaoTributariaFilter;
import br.com.okfx.mrv.api.service.exception.InformacaoTributariaInexistente;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InformacaoTributariaService {
    @Autowired
    private InformacaoTributariaRepository repository;

    public List<InformacaoTributaria> listarTodos() {
        return repository.findAll();
    }

    public Page<InformacaoTributaria> filtrar(InformacaoTributariaFilter informacaoTributariaFilter, Pageable pageable) {
        return repository.filtrar(informacaoTributariaFilter, pageable);
    }

    public Optional<InformacaoTributaria> buscarPorCodigo(Long codigo) {
        Optional<InformacaoTributaria> informacaoTributaria = Optional.ofNullable(repository.findOne(codigo));
        if (!informacaoTributaria.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }
        return informacaoTributaria;
    }

    public InformacaoTributaria salvar(InformacaoTributaria informacaoTributaria) {
        return repository.save(informacaoTributaria);
    }

    public InformacaoTributaria atualizar(Long codigo, InformacaoTributaria informacaoTributaria) {
        InformacaoTributaria informacaoTributariaSalvo = buscarPorCodigo(codigo).orElseThrow(() -> new InformacaoTributariaInexistente());
        BeanUtils.copyProperties(informacaoTributaria,informacaoTributariaSalvo,"codigo");

        return repository.save(informacaoTributariaSalvo);
    }

    public void apagar(Long codigo) {
        repository.delete(codigo);
    }
}