package br.com.projetosigse.repository;

import br.com.projetosigse.model.AlunoResponsavel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoResponsavelRepository extends JpaRepository<AlunoResponsavel, Long> {

    List<AlunoResponsavel> findByResponsavelId(Long responsavelId);

    List<AlunoResponsavel> findByAlunoId(Long alunoId);

    boolean existsByAlunoIdAndResponsavelId(Long alunoId, Long responsavelId);
}
