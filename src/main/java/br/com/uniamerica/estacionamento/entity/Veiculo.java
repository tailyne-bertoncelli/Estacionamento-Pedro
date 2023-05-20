package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.beans.XMLEncoder;

@Entity
@Audited
@Table(name = "veiculo", schema = "public")
@AuditTable(value = "veiculo_audit", schema = "audit")
public class Veiculo extends AbstractEntity{
    @Getter @Setter
    @Column(name = "placa", nullable = false, unique = true)
    @NotNull(message = "Placa não pode ser vazio!")
    @NotBlank(message = "Placa não pode estar em branco!")
    @Pattern(regexp = "^[A-Z]{3}[0-9][0-9A-Z][0-9]{2}$", message = "Formato incorreto!")
    @Size(min=7, max = 10, message = "Placa não possui quantidade de caracateres necessario min 7 max 10")
    private String placa;
    @Getter @Setter
    @JoinColumn(name = "modelo", nullable = false)
    @ManyToOne
    @NotNull(message = "Modelo não pode ser vazio!")
    //@NotBlank(message = "Modelo não pode estar em branco!")
    private Modelo modelo;
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "cor", length = 20, nullable = false)
    @NotNull(message = "Cor não pode ser vazio!")
    //@NotBlank(message = "Cor não pode estar em branco!")
    private Cor cor;
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "tipo", length = 20, nullable = false)
    @NotNull(message = "Tipo não pode ser vazio!")
    //@NotBlank(message = "Tipo não pode estar em branco!")
    private Tipo tipo;
    @Getter @Setter
    @Column(name = "ano", nullable = false)
    @NotNull(message = "Ano não pode ser vazio!")
    //@NotBlank(message = "Ano não pode estar em branco!")
    private int ano;
}
