package br.com.uniamerica.estacionamento.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.Duration;
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
    private LocalDateTime entrada;
    @Getter @Setter
    @Column(name = "saida")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime saida;
    @Getter@Setter
    @Column(name = "tempo_horas")
    private Long tempoHoras; //foi
    @Getter@Setter
    @Column(name = "tempo_minutos")
    private Long tempoMinutos; //foi
    @Getter @Setter
    @Column(name = "tempo_disponivel_desconto")
    private LocalTime tempoDesconto;
    @Getter @Setter
    @Column(name = "tempo_multa")
    private Long tempoMulta; //foi
    @Getter @Setter
    @Column(name = "desconto_realizado")
    private BigDecimal valorDesconto;
    @Getter @Setter
    @Column(name = "valor_multa")
    private BigDecimal valorMulta; //foi
    @Getter @Setter
    @Column(name = "valor_total")
    private BigDecimal valorTotal;
    @Getter @Setter
    @Column(name = "valor_hora")
    private BigDecimal valorHora; //foi
    @Getter @Setter
    @Column(name = "valor_hora_multa")
    private BigDecimal valorHoraMulta; //foi

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
                "\nHoras já pagas: " + condutor.getTempoPagoHora() +
                "                     DADOS DA MOVIMENTAÇÃO                   \n" +
                "\nHorario de entrada: " + entrada +
                "\nHorario de saida: " + saida +
                "\nMinuros após horario núcleo: " + tempoMulta +
                "\n\n------------------------ Descontos -----------------------\n" +
                "\nHoras acumuladas: " + condutor.getTempoPagoHora() +
                "\nValor do desconto:" +
                "\n\n------------------------- Valores ------------------------\n" +
                "\nValor de tempo de permanência: " + valorHora +
                "\nValor da multa: " + valorMulta +
                "\nValor de desconto: " + valorDesconto +
                "\n\n                                        TOTAL: R$ " + valorTotal;
    }
}
