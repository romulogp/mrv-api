package br.com.okfx.mrv.api.resource;

import br.com.okfx.mrv.api.event.RecursoCriadoEvent;
import br.com.okfx.mrv.api.model.Tributo;
import br.com.okfx.mrv.api.repository.filter.TributoFilter;
import br.com.okfx.mrv.api.service.TributoService;
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
@RequestMapping("/tributo")
public class TributoResource {

    @Autowired
    private TributoService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Tributo> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tributo> buscarPeloId(@PathVariable Long id) {
        Tributo tributo = service.buscarPorId(id).get();
        return tributo != null ? ResponseEntity.ok().body(tributo) : ResponseEntity.notFound().build();
    }

    @GetMapping("/filtrar")
    public Page<Tributo> pesquisar(TributoFilter tributoFilter, Pageable pageable) {
        return service.filtrar(tributoFilter, pageable);
    }

    @PostMapping
    public ResponseEntity<Tributo> criar(@Valid @RequestBody Tributo tributo, HttpServletResponse response) {
        Tributo tributoSalva = service.salvar(tributo);
        publisher.publishEvent((new RecursoCriadoEvent(this, response, tributoSalva.getId())));
        return ResponseEntity.status(HttpStatus.CREATED).body(tributoSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tributo> atualizar(@PathVariable Long id, @Valid @RequestBody Tributo tributo) {
        Tributo tributoSalva = service.atualizar(id, tributo);
        return ResponseEntity.ok(tributoSalva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}