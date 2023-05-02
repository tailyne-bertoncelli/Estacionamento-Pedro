package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Audited
@Table(name = "movimentacao", schema = "public")
@AuditTable(value = "movimentacao_audit", schema = "audit")
public class Movimentacao extends AbstractEntity{
    @Getter @Setter
    @JoinColumn(name = "veiculo", nullable = false, unique = true)
    @ManyToOne
    private Veiculo veiculo;
    @Getter @Setter
    @JoinColumn(name = "condutor", nullable = false)
    @ManyToOne
    private Condutor condutor;
    @Getter @Setter
    @Column(name = "entrada", nullable = false)
    private LocalDateTime entrada;
    @Getter @Setter
    @Column(name = "saida")
    private LocalDateTime saida;
    @Getter@Setter
    @Column(name = "tempo")
    private LocalTime tempo;
    @Getter @Setter
    @Column(name = "tempo_disponivel_desconto")
    private LocalTime tempoDesconto;
    @Getter @Setter
    @Column(name = "tempo_multa")
    private LocalTime tempoMulta;
    @Getter @Setter
    @Column(name = "desconto_realizado")
    private BigDecimal valorDesconto;
    @Getter @Setter
    @Column(name = "valor_multa")
    private BigDecimal valorMulta;
    @Getter @Setter
    @Column(name = "valor_total")
    private BigDecimal valorTotal;
    @Getter @Setter
    @Column(name = "valor_hora")
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name = "valor_hora_multa")
    private BigDecimal valorHoraMulta;
}
