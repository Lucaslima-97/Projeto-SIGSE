package br.com.projetosigse.service;

import br.com.projetosigse.dto.TurmaDtos.TurmaRequest;
import br.com.projetosigse.dto.TurmaDtos.TurmaResponse;
import br.com.projetosigse.exception.RecursoNaoEncontradoException;
import br.com.projetosigse.model.Turma;
import br.com.projetosigse.repository.TurmaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;

    public TurmaService(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    @Transactional
    public TurmaResponse criar(TurmaRequest request) {
        Turma turma = new Turma();
        aplicar(turma, request);
        return TurmaResponse.from(turmaRepository.save(turma));
    }

    @Transactional(readOnly = true)
    public List<TurmaResponse> listar() {
        return turmaRepository.findAll().stream().map(TurmaResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public TurmaResponse buscar(Long id) {
        return TurmaResponse.from(obter(id));
    }

    @Transactional
    public TurmaResponse atualizar(Long id, TurmaRequest request) {
        Turma turma = obter(id);
        aplicar(turma, request);
        return TurmaResponse.from(turmaRepository.save(turma));
    }

    public Turma obter(Long id) {
        return turmaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Turma não encontrada: " + id));
    }

    private void aplicar(Turma turma, TurmaRequest request) {
        turma.setNome(request.nome());
        turma.setSerie(request.serie());
        turma.setTurno(request.turno());
        turma.setAnoLetivo(request.anoLetivo());
    }
}
