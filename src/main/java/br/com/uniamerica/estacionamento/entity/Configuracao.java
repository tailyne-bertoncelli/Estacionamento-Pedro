package br.com.uniamerica.estacionamento.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Audited
@Table(name = "configuracao", schema = "public")
@AuditTable(value = "configuracao_audit", schema = "audit")
public class Configuracao extends AbstractEntity{
    @Getter @Setter
    @Column(name = "valor_hora")
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name = "valor_minuto")
    private BigDecimal valorMinutoMulta;
    @Getter @Setter
    @Column(name = "hora_inicio_expediente")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalTime inicioExpediente;
    @Getter @Setter
    @Column(name = "hora_fim_expediente")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalTime fimExpediente;
    @Getter @Setter
    @Column(name = "tempo_para_desconto")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Long tempoParaDesconto;
    @Getter @Setter
    @Column(name = "tempo_de_desconto")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Long tempoDeDesconto;
    @Getter @Setter
    @Column(name = "gerar_desconto")
    private boolean gerarDesconto;
    @Getter @Setter
    @Column(name = "vagas_moto")
    private int vagasMoto;
    @Getter @Setter
    @Column(name = "vagas_carro")
    private int vagasCarro;
    @Getter @Setter
    @Column(name = "vagas_van")
    private int vagasVan;
}
