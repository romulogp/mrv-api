package br.com.okfx.mrv.api.resource;

import br.com.okfx.mrv.api.event.RecursoCriadoEvent;
import br.com.okfx.mrv.api.model.BaseLegal;
import br.com.okfx.mrv.api.repository.BaseLegalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
    private BaseLegalRepository repository;

    @Autowired
    private ApplicationEventPublisher publisher;


    @GetMapping
    public List<BaseLegal> listar(){
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<BaseLegal> criar(@Valid @RequestBody BaseLegal baseLegal, HttpServletResponse response){
        BaseLegal baseLegalSalvo = repository.save(baseLegal);

        publisher.publishEvent((new RecursoCriadoEvent(this,response, baseLegalSalvo.getId())));

        return ResponseEntity.status(HttpStatus.CREATED).body(baseLegalSalvo);
    }



}
