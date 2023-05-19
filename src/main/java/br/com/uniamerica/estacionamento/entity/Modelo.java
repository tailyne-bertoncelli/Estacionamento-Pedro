package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1, max = 50, message = "Não tem a quantidade de caracteres permitidos min 3 max 50!")
    private String nome;
    @Getter @Setter
    @JoinColumn(name = "marca", nullable = false)
    @ManyToOne
    @NotNull(message = "MARCA não pode ser vazio!")
    private Marca marca;
}
