package br.com.okfx.mrv.api.service;

        import br.com.okfx.mrv.api.model.Natureza;
        import br.com.okfx.mrv.api.repository.NaturezaRepository;
        import br.com.okfx.mrv.api.repository.filter.NaturezaFilter;
        import br.com.okfx.mrv.api.service.exception.NaturezaInexistente;
        import org.springframework.beans.BeanUtils;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.dao.EmptyResultDataAccessException;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.Pageable;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Optional;

@Service
public class NaturezaService {
    @Autowired
    private NaturezaRepository repository;

    public List<Natureza> listarTodos() {
        return repository.findAll();
    }

    public Page<Natureza> filtrar(NaturezaFilter naturezaFilter, Pageable pageable) {
        return repository.filtrar(naturezaFilter, pageable);
    }

    public Optional<Natureza> buscarPorCodigo(Long codigo) {
        Optional<Natureza> natureza = Optional.ofNullable(repository.findOne(codigo));
        if (!natureza.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }
        return natureza;
    }

    public Natureza salvar(Natureza natureza) {
        return repository.save(natureza);
    }

    public Natureza atualizar(Long codigo, Natureza natureza) {
        Natureza naturezaSalvo = buscarPorCodigo(codigo).orElseThrow(() -> new NaturezaInexistente());
        BeanUtils.copyProperties(natureza,naturezaSalvo,"codigo");

        return repository.save(naturezaSalvo);
    }

    public void apagar(Long codigo) {
        repository.delete(codigo);
    }
}