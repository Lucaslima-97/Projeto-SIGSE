package br.com.projetosigse.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("A")
public class Aluno extends Pessoa {

    @Column(unique = true, nullable = false, length = 20)
    private String matricula;

    private String fotoUrl;

    private boolean ativo = true;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }


}
