package br.com.okfx.mrv.api.resource;

import br.com.okfx.mrv.api.event.RecursoCriadoEvent;
import br.com.okfx.mrv.api.model.Servico;
import br.com.okfx.mrv.api.repository.filter.ServicoFilter;
import br.com.okfx.mrv.api.service.ServicoService;
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
@RequestMapping("/servico")
public class ServicoResource {

    @Autowired
    private ServicoService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Servico> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPeloId(@PathVariable Long id) {
        Servico servico = service.buscarPorId(id).get();
        return servico != null ? ResponseEntity.ok().body(servico) : ResponseEntity.notFound().build();
    }

    @GetMapping("/filtrar")
    public Page<Servico> pesquisar(ServicoFilter servicoFilter, Pageable pageable) {
        return service.filtrar(servicoFilter, pageable);
    }

    @PostMapping
    public ResponseEntity<Servico> criar(@Valid @RequestBody Servico servico, HttpServletResponse response) {
        Servico servicoSalva = service.salvar(servico);
        publisher.publishEvent((new RecursoCriadoEvent(this, response, servicoSalva.getId())));
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servico> atualizar(@PathVariable Long id, @Valid @RequestBody Servico servico) {
        Servico servicoSalva = service.atualizar(id, servico);
        return ResponseEntity.ok(servicoSalva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.apagar(id);
        return ResponseEntity.noContent().build();
    }
}