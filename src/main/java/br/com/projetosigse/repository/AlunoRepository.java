package br.com.projetosigse.repository;

import br.com.projetosigse.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Optional<Aluno> findByMatricula(String matricula);

    List<Aluno> findByNomeContainingIgnoreCase(String nome);
}
