package br.com.projetosigse.controller;

import br.com.projetosigse.dto.CartaoDtos.CartaoRequest;
import br.com.projetosigse.dto.CartaoDtos.CartaoResponse;
import br.com.projetosigse.service.CartaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    private final CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartaoResponse criar(@Valid @RequestBody CartaoRequest request) {
        return cartaoService.criar(request);
    }

    @GetMapping
    public List<CartaoResponse> listar() {
        return cartaoService.listar();
    }

    @GetMapping("/{id}")
    public CartaoResponse buscar(@PathVariable Long id) {
        return cartaoService.buscar(id);
    }

    @PutMapping("/{id}")
    public CartaoResponse atualizar(@PathVariable Long id, @Valid @RequestBody CartaoRequest request) {
        return cartaoService.atualizar(id, request);
    }
}
