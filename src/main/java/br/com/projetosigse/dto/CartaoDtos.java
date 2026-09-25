package br.com.projetosigse.dto;

import br.com.projetosigse.model.CartaoRfid;
import br.com.projetosigse.model.TipoCartao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CartaoDtos {

    public record CartaoRequest(
            @NotBlank String uid,
            @NotNull TipoCartao tipo,
            Boolean ativo,
            Long alunoId,
            Long responsavelId
    ) {
    }

    public record VinculoRequest(@NotNull Long alunoId, @NotNull Long responsavelId) {
    }

    public record CartaoResponse(
            Long id,
            String uid,
            TipoCartao tipo,
            boolean ativo,
            Long alunoId,
            Long responsavelId
    ) {
        public static CartaoResponse from(CartaoRfid cartao) {
            return new CartaoResponse(
                    cartao.getId(),
                    cartao.getUid(),
                    cartao.getTipo(),
                    cartao.isAtivo(),
                    cartao.getAluno() != null ? cartao.getAluno().getId() : null,
                    cartao.getResponsavel() != null ? cartao.getResponsavel().getId() : null
            );
        }
    }
}
