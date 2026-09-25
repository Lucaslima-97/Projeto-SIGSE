package br.com.projetosigse.controller;

import br.com.projetosigse.dto.TurmaDtos.TurmaRequest;
import br.com.projetosigse.dto.TurmaDtos.TurmaResponse;
import br.com.projetosigse.service.TurmaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {

    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TurmaResponse criar(@Valid @RequestBody TurmaRequest request) {
        return turmaService.criar(request);
    }

    @GetMapping
    public List<TurmaResponse> listar() {
        return turmaService.listar();
    }

    @GetMapping("/{id}")
    public TurmaResponse buscar(@PathVariable Long id) {
        return turmaService.buscar(id);
    }

    @PutMapping("/{id}")
    public TurmaResponse atualizar(@PathVariable Long id, @Valid @RequestBody TurmaRequest request) {
        return turmaService.atualizar(id, request);
    }
}
