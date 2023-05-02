package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
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
    private String nome;
    @Getter @Setter
    @JoinColumn(name = "marca", nullable = false)
    @ManyToOne
    private Marca marca;

}
