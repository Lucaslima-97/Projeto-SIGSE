package br.com.projetosigse.repository;

import br.com.projetosigse.model.ChamadaPatio;
import br.com.projetosigse.model.StatusChamada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChamadaPatioRepository extends JpaRepository<ChamadaPatio, Long> {

    List<ChamadaPatio> findByStatusOrderByCriadoEmAsc(StatusChamada status);

    Optional<ChamadaPatio> findFirstByAlunoIdAndStatus(Long alunoId, StatusChamada status);
}
