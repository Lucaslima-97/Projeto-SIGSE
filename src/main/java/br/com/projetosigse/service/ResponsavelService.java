package br.com.projetosigse.service;

import br.com.projetosigse.dto.CartaoDtos.VinculoRequest;
import br.com.projetosigse.dto.ResponsavelDtos.ResponsavelRequest;
import br.com.projetosigse.dto.ResponsavelDtos.ResponsavelResponse;
import br.com.projetosigse.exception.RecursoNaoEncontradoException;
import br.com.projetosigse.exception.RegraNegocioException;
import br.com.projetosigse.model.Aluno;
import br.com.projetosigse.model.AlunoResponsavel;
import br.com.projetosigse.model.Responsavel;
import br.com.projetosigse.repository.AlunoResponsavelRepository;
import br.com.projetosigse.repository.ResponsavelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResponsavelService {

    private final ResponsavelRepository responsavelRepository;
    private final AlunoResponsavelRepository alunoResponsavelRepository;
    private final AlunoService alunoService;

    public ResponsavelService(
            ResponsavelRepository responsavelRepository,
            AlunoResponsavelRepository alunoResponsavelRepository,
            AlunoService alunoService
    ) {
        this.responsavelRepository = responsavelRepository;
        this.alunoResponsavelRepository = alunoResponsavelRepository;
        this.alunoService = alunoService;
    }

    @Transactional
    public ResponsavelResponse criar(ResponsavelRequest request) {
        Responsavel responsavel = new Responsavel();
        aplicar(responsavel, request);
        return ResponsavelResponse.from(responsavelRepository.save(responsavel));
    }

    @Transactional(readOnly = true)
    public List<ResponsavelResponse> listar() {
        return responsavelRepository.findAll().stream().map(ResponsavelResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ResponsavelResponse buscar(Long id) {
        return ResponsavelResponse.from(obter(id));
    }

    @Transactional
    public ResponsavelResponse atualizar(Long id, ResponsavelRequest request) {
        Responsavel responsavel = obter(id);
        aplicar(responsavel, request);
        return ResponsavelResponse.from(responsavelRepository.save(responsavel));
    }

    @Transactional
    public void vincularAluno(VinculoRequest request) {
        Aluno aluno = alunoService.obter(request.alunoId());
        Responsavel responsavel = obter(request.responsavelId());
        if (alunoResponsavelRepository.existsByAlunoIdAndResponsavelId(aluno.getId(), responsavel.getId())) {
            throw new RegraNegocioException("Vínculo já existe.");
        }
        AlunoResponsavel vinculo = new AlunoResponsavel();
        vinculo.setAluno(aluno);
        vinculo.setResponsavel(responsavel);
        alunoResponsavelRepository.save(vinculo);
    }

    public Responsavel obter(Long id) {
        return responsavelRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Responsável não encontrado: " + id));
    }

    private void aplicar(Responsavel responsavel, ResponsavelRequest request) {
        responsavel.setNome(request.nome());
        responsavel.setEmail(request.email());
        responsavel.setCpf(request.cpf());
        responsavel.setDataNascimento(request.dataNascimento());
        responsavel.setTelefone(request.telefone());
        responsavel.setParentesco(request.parentesco());
    }
}
