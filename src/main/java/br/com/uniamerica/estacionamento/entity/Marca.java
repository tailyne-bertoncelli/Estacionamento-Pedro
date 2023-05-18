package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "marca", schema = "public")
@AuditTable(value = "marca_audit", schema = "audit")
public class Marca extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome", nullable = false ,unique = true)
    @NotBlank(message = "MARCA não pode estar em branco!")
    @NotNull(message = "MARCA não pode ser vazio!")
    @Size(min = 1, max = 50, message = "Marca ultrapassou a quantidade de caracteres permitidos!")
    private String nome;
}
