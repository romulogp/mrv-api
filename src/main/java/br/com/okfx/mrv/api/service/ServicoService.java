package br.com.okfx.mrv.api.service;

import br.com.okfx.mrv.api.model.Servico;
import br.com.okfx.mrv.api.repository.ServicoRepository;
import br.com.okfx.mrv.api.repository.filter.ServicoFilter;
import br.com.okfx.mrv.api.service.exception.ServicoInexistente;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {
    @Autowired
    private ServicoRepository repository;

    public List<Servico> listarTodos() {
        return repository.findAll();
    }

    public Page<Servico> filtrar(ServicoFilter servicoFilter, Pageable pageable) {
        return repository.filtrar(servicoFilter, pageable);
    }

    public Optional<Servico> buscarPorId(Long id) {
        Optional<Servico> servico = Optional.ofNullable(repository.findOne(id));
        if (!servico.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }
        return servico;
    }

    public Servico salvar(Servico servico) {
        return repository.save(servico);
    }

    public Servico atualizar(Long id, Servico servico) {
        Servico servicoSalvo = buscarPorId(id).orElseThrow(() -> new ServicoInexistente());
        BeanUtils.copyProperties(servico, servicoSalvo, "id");

        return repository.save(servicoSalvo);
    }

    public void apagar(Long id) {
        repository.delete(id);
    }
}

