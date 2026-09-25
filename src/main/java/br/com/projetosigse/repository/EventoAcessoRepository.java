package br.com.projetosigse.repository;

import br.com.projetosigse.model.EventoAcesso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoAcessoRepository extends JpaRepository<EventoAcesso, Long> {

    List<EventoAcesso> findByAlunoIdOrderByHorarioDesc(Long alunoId);
}
