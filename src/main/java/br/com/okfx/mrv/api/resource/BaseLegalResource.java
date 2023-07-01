package br.com.okfx.mrv.api.resource;

import br.com.okfx.mrv.api.event.RecursoCriadoEvent;
import br.com.okfx.mrv.api.model.BaseLegal;
import br.com.okfx.mrv.api.repository.filter.BaseLegalFilter;
import br.com.okfx.mrv.api.service.BaseLegalService;
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
@RequestMapping("/baseLegal")
public class BaseLegalResource {

    @Autowired
    private BaseLegalService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<BaseLegal> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseLegal> buscarPeloCodigo(@PathVariable Long id) {
        BaseLegal baseLegal = service.buscarPorId(id).get();
        return baseLegal != null ? ResponseEntity.ok().body(baseLegal) : ResponseEntity.notFound().build();
    }

    @GetMapping("/filtrar")
    public Page<BaseLegal> pesquisar(BaseLegalFilter baseLegalFilter, Pageable pageable) {
        return service.filtrar(baseLegalFilter, pageable);
    }

    @PostMapping
    public ResponseEntity<BaseLegal> criar(@Valid @RequestBody BaseLegal baseLegal, HttpServletResponse response) {
        BaseLegal baseLegalSalvo = service.salvar(baseLegal);
        publisher.publishEvent((new RecursoCriadoEvent(this, response, baseLegalSalvo.getId())));
        return ResponseEntity.status(HttpStatus.CREATED).body(baseLegalSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseLegal> atualizar(@PathVariable Long id, @Valid @RequestBody BaseLegal baseLegal) {
        BaseLegal baseLegalSalva = service.atualizar(id, baseLegal);
        return ResponseEntity.ok(baseLegalSalva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
