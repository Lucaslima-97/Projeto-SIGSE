package br.com.projetosigse.dto;

import br.com.projetosigse.model.Turma;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TurmaDtos {

    public record TurmaRequest(
            @NotBlank String nome,
            @NotNull @Min(1) @Max(5) Integer serie,
            String turno,
            @NotNull Integer anoLetivo
    ) {
    }

    public record TurmaResponse(Long id, String nome, Integer serie, String turno, Integer anoLetivo) {
        public static TurmaResponse from(Turma turma) {
            return new TurmaResponse(
                    turma.getId(),
                    turma.getNome(),
                    turma.getSerie(),
                    turma.getTurno(),
                    turma.getAnoLetivo()
            );
        }
    }
}
