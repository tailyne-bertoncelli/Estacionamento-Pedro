package br.com.uniamerica.estacionamento.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Audited
@Table(name = "movimentacao", schema = "public")
@AuditTable(value = "movimentacao_audit", schema = "audit")
public class Movimentacao extends AbstractEntity{
    @Getter @Setter
    @JoinColumn(name = "veiculo", nullable = false, unique = true)
    @ManyToOne
    @NotNull(message = "VEICULO não pode ser vazio!")
    private Veiculo veiculo;
    @Getter @Setter
    @JoinColumn(name = "condutor", nullable = false)
    @ManyToOne
    @NotNull(message = "CONDUTOR não pode ser vazio!")
    private Condutor condutor;
    @Getter @Setter
    @Column(name = "entrada", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@FutureOrPresent(message = "Ainda nao foi inventada viagem no tempo!")
    private LocalDateTime entrada;
    @Getter @Setter
    @Column(name = "saida")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@FutureOrPresent(message = "Ainda nao foi inventada viagem no tempo!")
    private LocalDateTime saida;
    @Getter@Setter
    @Column(name = "tempo_horas")
    private Long tempoHoras;
    @Getter@Setter
    @Column(name = "tempo_minutos")
    private Long tempoMinutos;
    @Getter @Setter
    @Column(name = "tempo_disponivel_desconto")
    private Long tempoDesconto;
    @Getter @Setter
    @Column(name = "tempo_multa")
    private Long tempoMulta;
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

    public void setEntradaMov() {
        this.entrada = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "----------------------------------------------------------\n                RELATORIO DE MOVIMENTACAO\n----------------------------------------------------------\n" +
                "                     DADOS DO VEICULO                       \n"+
                "\nPlaca: " + veiculo.getPlaca() +
                "\nVeiculo: " + veiculo.getModelo().getNome() +
                "\nMarca: " + veiculo.getModelo().getMarca().getNome()+ "    Tipo: "+ veiculo.getTipo() + "    Ano: " + veiculo.getAno()+"\n\n"+
                "                     DADOS DO CONDUTOR                       \n" +
                "\nNome: " + condutor.getNome()+
                "\nTelefone: " + condutor.getTelefone() +
                "\nCPF: " + condutor.getCpf() +
                "\nHoras já pagas: " + condutor.getTempoPagoHora() +"\n"+
                "                   DADOS DA MOVIMENTAÇÃO                   \n" +
                "\nHorario de entrada: " + entrada +
                "\nHorario de saida: " + saida +
                "\nTempo de permanencia: "+tempoHoras+" horas e "+tempoMinutos+" minutos"+
                "\nMinuros após horario núcleo: " + tempoMulta +
                "\nTempo para desconto: " +tempoDesconto+
                "\n\n---------------------- Descontos -----------------------\n" +
                "   Caso o condutor já tenha horas para desconto\n" +
                "            o mesmo é dado automaticamente"+
                "\nHoras acumuladas: " + condutor.getTempoPagoHora() +
                "\nValor do desconto:" +
                "\n\n----------------------- Valores ------------------------\n" +
                "\nValor de tempo de permanência: " + valorHora +
                "\nValor da multa: " + valorMulta +
                "\nValor de desconto: " + valorDesconto +
                "\n\n                                        TOTAL: R$ " + valorTotal;
    }
}
