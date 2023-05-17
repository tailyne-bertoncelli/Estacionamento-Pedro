package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "veiculo", schema = "public")
@AuditTable(value = "veiculo_audit", schema = "audit")
public class Veiculo extends AbstractEntity{
    @Getter @Setter
    @Column(name = "placa", nullable = false, unique = true)
    private String placa;
    @Getter @Setter
    @JoinColumn(name = "modelo", nullable = false)
    @ManyToOne
    @NotNull
    private Modelo modelo;
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "cor", length = 20, nullable = false)
    private Cor cor;
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "tipo", length = 20, nullable = false)
    private Tipo tipo;
    @Getter @Setter
    @Column(name = "ano", nullable = false)
    @NotNull
    private int ano;
}
