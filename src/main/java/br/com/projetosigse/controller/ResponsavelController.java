package br.com.projetosigse.controller;

import br.com.projetosigse.dto.CartaoDtos.VinculoRequest;
import br.com.projetosigse.dto.ResponsavelDtos.ResponsavelRequest;
import br.com.projetosigse.dto.ResponsavelDtos.ResponsavelResponse;
import br.com.projetosigse.service.ResponsavelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/responsaveis")
public class ResponsavelController {

    private final ResponsavelService responsavelService;

    public ResponsavelController(ResponsavelService responsavelService) {
        this.responsavelService = responsavelService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponsavelResponse criar(@Valid @RequestBody ResponsavelRequest request) {
        return responsavelService.criar(request);
    }

    @GetMapping
    public List<ResponsavelResponse> listar() {
        return responsavelService.listar();
    }

    @GetMapping("/{id}")
    public ResponsavelResponse buscar(@PathVariable Long id) {
        return responsavelService.buscar(id);
    }

    @PutMapping("/{id}")
    public ResponsavelResponse atualizar(@PathVariable Long id, @Valid @RequestBody ResponsavelRequest request) {
        return responsavelService.atualizar(id, request);
    }

    @PostMapping("/vinculos")
    @ResponseStatus(HttpStatus.CREATED)
    public void vincular(@Valid @RequestBody VinculoRequest request) {
        responsavelService.vincularAluno(request);
    }
}
