package br.com.projetosigse.dto;

import br.com.projetosigse.model.ChamadaPatio;
import br.com.projetosigse.model.OrigemChamada;
import br.com.projetosigse.model.PontoLeitura;
import br.com.projetosigse.model.StatusChamada;
import br.com.projetosigse.util.NomePainel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ChamadaDtos {

    public record LeituraRequest(
            @NotBlank String uid,
            @NotNull PontoLeitura ponto,
            Long alunoId
    ) {
    }

    public record LiberacaoManualRequest(
            Long alunoId,
            String matricula
    ) {
    }

    public record ChamadaResponse(
            Long id,
            Long alunoId,
            String nomeExibicao,
            String fotoUrl,
            StatusChamada status,
            OrigemChamada origem,
            LocalDateTime criadoEm
    ) {
        public static ChamadaResponse from(ChamadaPatio chamada) {
            var aluno = chamada.getAluno();
            return new ChamadaResponse(
                    chamada.getId(),
                    aluno.getId(),
                    NomePainel.formatar(aluno.getNome()),
                    aluno.getFotoUrl(),
                    chamada.getStatus(),
                    chamada.getOrigem(),
                    chamada.getCriadoEm()
            );
        }
    }

    public record PatioEvento(String acao, ChamadaResponse chamada) {
        public static PatioEvento inserir(ChamadaPatio chamada) {
            return new PatioEvento("INSERIR", ChamadaResponse.from(chamada));
        }

        public static PatioEvento remover(ChamadaPatio chamada) {
            return new PatioEvento("REMOVER", ChamadaResponse.from(chamada));
        }
    }
}
