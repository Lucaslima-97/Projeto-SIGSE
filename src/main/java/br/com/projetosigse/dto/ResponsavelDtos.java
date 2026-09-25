package br.com.projetosigse.dto;

import br.com.projetosigse.model.Responsavel;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class ResponsavelDtos {

    public record ResponsavelRequest(
            @NotBlank String nome,
            String email,
            String cpf,
            LocalDate dataNascimento,
            String telefone,
            String parentesco
    ) {
    }

    public record ResponsavelResponse(
            Long id,
            String nome,
            String email,
            String cpf,
            LocalDate dataNascimento,
            String telefone,
            String parentesco
    ) {
        public static ResponsavelResponse from(Responsavel responsavel) {
            return new ResponsavelResponse(
                    responsavel.getId(),
                    responsavel.getNome(),
                    responsavel.getEmail(),
                    responsavel.getCpf(),
                    responsavel.getDataNascimento(),
                    responsavel.getTelefone(),
                    responsavel.getParentesco()
            );
        }
    }
}
