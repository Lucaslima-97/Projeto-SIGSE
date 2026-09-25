package br.com.projetosigse.controller;

import br.com.projetosigse.model.EventoAcesso;
import br.com.projetosigse.repository.EventoAcessoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoAcessoRepository eventoAcessoRepository;

    public EventoController(EventoAcessoRepository eventoAcessoRepository) {
        this.eventoAcessoRepository = eventoAcessoRepository;
    }

    @GetMapping("/aluno/{alunoId}")
    public List<EventoAcesso> porAluno(@PathVariable Long alunoId) {
        return eventoAcessoRepository.findByAlunoIdOrderByHorarioDesc(alunoId);
    }
}
