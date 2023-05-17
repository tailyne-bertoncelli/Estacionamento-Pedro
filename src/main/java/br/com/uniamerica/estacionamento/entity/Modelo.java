package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "modelo", schema = "public")
@AuditTable(value = "modelo_audit", schema = "audit")
public class Modelo extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome", nullable = false, unique = true)
    @NotBlank(message = "NOME não pode estar em branco!")
    @NotNull(message = "NOME não pode ser vazio!")
    private String nome;
    @Getter @Setter
    @JoinColumn(name = "marca", nullable = false)
    @ManyToOne
    @NotBlank(message = "MARCA não pode estar em branco!")
    @NotNull(message = "MARCA não pode ser vazio!")
    private Marca marca;
}
