package br.com.okfx.mrv.api.resource;

import br.com.okfx.mrv.api.event.RecursoCriadoEvent;
import br.com.okfx.mrv.api.model.Natureza;
import br.com.okfx.mrv.api.repository.filter.NaturezaFilter;
import br.com.okfx.mrv.api.service.NaturezaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/natureza")
public class NaturezaResource {

    @Autowired
    private NaturezaService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Natureza> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Natureza> buscarPeloId(@PathVariable Long id) {
        Natureza natureza = service.buscarPorId(id).get();
        return natureza != null ? ResponseEntity.ok().body(natureza) : ResponseEntity.notFound().build();
    }

    @GetMapping("/filtrar")
    public Page<Natureza> pesquisar(NaturezaFilter naturezaFilter, Pageable pageable) {
        return service.filtrar(naturezaFilter, pageable);
    }

    @PostMapping
    public ResponseEntity<Natureza> criar(@Valid @RequestBody Natureza natureza, HttpServletResponse response) {
        Natureza naturezaSalva = service.salvar(natureza);
        publisher.publishEvent((new RecursoCriadoEvent(this, response, naturezaSalva.getId())));
        return ResponseEntity.status(HttpStatus.CREATED).body(naturezaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Natureza> atualizar(@PathVariable Long id, @Valid @RequestBody Natureza natureza) {
        Natureza naturezaSalva = service.atualizar(id, natureza);
        return ResponseEntity.ok(naturezaSalva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}