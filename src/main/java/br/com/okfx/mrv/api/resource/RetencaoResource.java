package br.com.okfx.mrv.api.resource;

import br.com.okfx.mrv.api.event.RecursoCriadoEvent;
import br.com.okfx.mrv.api.model.Retencao;
import br.com.okfx.mrv.api.repository.filter.RetencaoFilter;
import br.com.okfx.mrv.api.service.RetencaoService;
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
@RequestMapping("/retencao")
public class RetencaoResource {

    @Autowired
    private RetencaoService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Retencao> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Retencao> buscarPeloId(@PathVariable Long id) {
        Retencao retencao = service.buscarPorId(id).get();
        return retencao != null ? ResponseEntity.ok().body(retencao) : ResponseEntity.notFound().build();
    }

    @GetMapping("/filtrar")
    public Page<Retencao> pesquisar(RetencaoFilter retencaoFilter, Pageable pageable) {
        return service.filtrar(retencaoFilter, pageable);
    }

    @PostMapping
    public ResponseEntity<Retencao> criar(@Valid @RequestBody Retencao retencao, HttpServletResponse response) {
        Retencao retencaoSalva = service.salvar(retencao);
        publisher.publishEvent((new RecursoCriadoEvent(this, response, retencaoSalva.getId())));
        return ResponseEntity.status(HttpStatus.CREATED).body(retencaoSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Retencao> atualizar(@PathVariable Long id, @Valid @RequestBody Retencao retencao) {
        Retencao retencaoSalva = service.atualizar(id, retencao);
        return ResponseEntity.ok(retencaoSalva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}