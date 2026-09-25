package br.com.projetosigse.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chamada_patio")
public class ChamadaPatio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusChamada status = StatusChamada.AGUARDANDO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrigemChamada origem;

    private String uidOrigem;

    @Column(nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    private LocalDateTime atualizadoEm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public StatusChamada getStatus() {
        return status;
    }

    public void setStatus(StatusChamada status) {
        this.status = status;
    }

    public OrigemChamada getOrigem() {
        return origem;
    }

    public void setOrigem(OrigemChamada origem) {
        this.origem = origem;
    }

    public String getUidOrigem() {
        return uidOrigem;
    }

    public void setUidOrigem(String uidOrigem) {
        this.uidOrigem = uidOrigem;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}
