package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    protected Long id;
    @Getter @Setter
    @Column(name = "dtCadastro", nullable = false)
    private LocalDateTime Cadastro;
    @Getter @Setter
    @Column(name = "dtAtualizacao")
    private LocalDateTime edicao;
    @Getter @Setter
    @Column(name = "ativo")
    private boolean ativo;

    @PrePersist
    public void prePersist(){
        this.Cadastro = LocalDateTime.now();
        this.ativo = true;
    }

    @PreUpdate
    public void preUpdate(){
        this.edicao = LocalDateTime.now();
    }
}
