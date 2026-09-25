package br.com.projetosigse.dto;

import br.com.projetosigse.model.Aluno;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AlunoDtos {

    public record AlunoRequest(
            @NotBlank String nome,
            String email,
            String cpf,
            LocalDate dataNascimento,
            @NotBlank String matricula,
            String fotoUrl,
            Boolean ativo,
            @NotNull Long turmaId
    ) {
    }

    public record AlunoResponse(
            Long id,
            String nome,
            String email,
            String cpf,
            LocalDate dataNascimento,
            String matricula,
            String fotoUrl,
            boolean ativo
    ) {
        public static AlunoResponse from(Aluno aluno) {
            return new AlunoResponse(
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getEmail(),
                    aluno.getCpf(),
                    aluno.getDataNascimento(),
                    aluno.getMatricula(),
                    aluno.getFotoUrl(),
                    aluno.isAtivo()
            );
        }
    }
}
