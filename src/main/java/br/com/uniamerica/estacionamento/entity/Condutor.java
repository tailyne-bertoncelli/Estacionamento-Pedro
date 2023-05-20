package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalTime;
@Entity
@Audited
@Table(name = "condutor", schema = "public")
@AuditTable(value = "condutor_audit", schema = "audit")
public class Condutor extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome", nullable = false, length = 100)
    @NotNull(message = "Nome do condutor não pode ser vazio!")
    //@NotBlank(message = "Nome do condutor não pode estar em branco!")
    @Size(min= 3, max = 50, message = "Nome possui a quantidade de caracteres permitidos min 3 max 50!")
    @Pattern(regexp = "^[a-zA-Z\\u00C0-\\u017F´]+\\s+[a-zA-Z\\u00C0-\\u017F´]{0,}$",message = "Nome não possui formato valido! Infome nome e sobrenome!")
    private String nome;
    @Getter @Setter
    @Column(name = "cpf", nullable = false, unique = true ,length = 15)
    //@CPF(message = "CPF está incorreto!")
    @NotNull(message = "CPF não pode ser vazio!")
    @NotBlank(message = "CPF não pode estar em branco!")
    @Size(min = 11, max = 20, message = "CPF não possui a quantidade de caracteres permitidos!")
    private String cpf;
    @Getter @Setter
    @Column(name = "telefone", nullable = false, length = 17)
    @NotNull(message = "TELEFONE não pode ser vazio!")
    @NotBlank(message = "TELEFONE não pode estar em branco!")
    @Size(min = 8, max = 20, message = "Telefone não possui a quantidade de caracteres permitidos min 8 max 20!")
    //@Pattern(regexp = "^([0-9]{2})\s[0-9]{5}-[0-9]{4}$", message = "Telefone informado de maneira incorreta! (xx) xxxxx-xxxx")
    private String telefone;
    @Getter @Setter
    @Column(name = "tempo_gasto")
    private Long tempoPagoHora;
    @Getter @Setter
    @Column(name = "tempo_desconto")
    private Long tempoDesconto;
}
