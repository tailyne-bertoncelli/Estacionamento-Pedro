package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalTime;
@Entity
@Audited
@Table(name = "condutores", schema = "public")
@AuditTable(value = "condutor_audit", schema = "audit")
public class Condutor extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome", nullable = false, length = 100)
    @NotNull(message = "Nome do condutor não pode ser vazio!")
    @NotBlank(message = "Nome do condutor não pode estar em branco!")
    private String nome;
    @Getter @Setter
    @Column(name = "cpf", nullable = false, unique = true ,length = 15)
    @CPF(message = "CPF está incorreto!")
    @NotNull(message = "CPF não pode ser vazio!")
    @NotBlank(message = "CPF não pode estar em branco!")
    private String cpf;
    @Getter @Setter
    @Column(name = "telefone", nullable = false, length = 17)
    @NotNull(message = "TELEFONE não pode ser vazio!")
    @NotBlank(message = "TELEFONE não pode estar em branco!")
    private String telefone;
    @Getter @Setter
    @Column(name = "tempo_gasto")
    private Long tempoPagoHora;
    @Getter @Setter
    @Column(name = "tempo_desconto")
    private Long tempoDesconto;
}
