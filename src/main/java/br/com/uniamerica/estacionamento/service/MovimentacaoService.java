package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    public Movimentacao findById(Long id) {
        Optional<Movimentacao> movimentacao = this.movimentacaoRepository.findById(id);
        return movimentacao.orElseThrow(() -> new RuntimeException("Movimentação não encontrado!"));
    }

    public List<Movimentacao> findAll(){
        return this.movimentacaoRepository.findAll();
    }

    @Transactional
    public void deleta(final Movimentacao movimentacao){
        this.movimentacaoRepository.delete(movimentacao);
    }

    @Transactional
    public void altera(final Movimentacao movimentacao){
        if (!condutorRepository.existsById(movimentacao.getCondutor().getId())) {
            throw new RuntimeException("Condutor informado não existe!");
        } else if (!condutorRepository.getById(movimentacao.getCondutor().getId()).isAtivo()) {
            throw new RuntimeException("Condutor está desativado");
        } else if (!veiculoRepository.existsById(movimentacao.getVeiculo().getId())) {
            throw new RuntimeException("Veiculo informado não existe!");
        } else if (!veiculoRepository.getById(movimentacao.getVeiculo().getId()).isAtivo()) {
            throw new RuntimeException("Veiculo informado está desativado");
        } else {
            this.movimentacaoRepository.save(movimentacao);
        }
    }

    @Transactional
    public void cadastra(final Movimentacao movimentacao){
        if (!condutorRepository.existsById(movimentacao.getCondutor().getId())) {
            throw new RuntimeException("Condutor informado não existe!");
        } else if (!condutorRepository.getById(movimentacao.getCondutor().getId()).isAtivo()) {
            throw new RuntimeException("Condutor está desativado");
        } else if (!veiculoRepository.existsById(movimentacao.getVeiculo().getId())) {
            throw new RuntimeException("Veiculo informado não existe!");
        } else if (!veiculoRepository.getById(movimentacao.getVeiculo().getId()).isAtivo()) {
            throw new RuntimeException("Veiculo informado está desativado");
        } else {
            //movimentacao.setEntradaMov();
            this.movimentacaoRepository.save(movimentacao);
        }
    }

    @Transactional
    public void desativar(Long id){
        var condutor = movimentacaoRepository.findById(id);
        if (id == condutor.get().getId()){
            this.movimentacaoRepository.desativaMovimetacao(id);
        }
        else {
            throw new RuntimeException("A movimentação não foi encontrado!");
        }
    }

    @Transactional
    public void ativar(Long id){
        var condutor = movimentacaoRepository.findById(id);
        if (id == condutor.get().getId()){
            this.movimentacaoRepository.ativaMovimetacao(id);
        }
        else {
            throw new RuntimeException("A movimentação não foi encontrado!");
        }
    }

    @Transactional
    public void finalizaMov(final Long id, final Movimentacao movimentacao){
        //movimentacao.setSaida(LocalDateTime.now());
        this.movimentacaoRepository.save(movimentacao);
        Configuracao configuracao = movimentacaoRepository.buscaConfig();


        //CALCULANDO AS HORAS E MINUTOS
        Long tempoPermanencia = Duration.between(movimentacao.getEntrada(), movimentacao.getSaida()).getSeconds();
        Long minutos = tempoPermanencia / 60;
        Long minutosTotais = minutos;
        Long horas = minutos / 60;
        minutos = minutos % 60;
        movimentacao.setTempoHoras(horas);
        movimentacao.setTempoMinutos(minutos);

        //CALCULANDO VALOR DAS HORAS
        BigDecimal vHora = configuracao.getValorHora();
        BigDecimal vTotalHora = BigDecimal.valueOf(horas).multiply(vHora);
        movimentacao.setValorHora(vTotalHora);

        //CALCULANDO MULTA
        LocalTime fim = configuracao.getFimExpediente();
        if (fim.isBefore(movimentacao.getSaida().toLocalTime())){
            Long tempoForaExpediente = Duration.between(fim, movimentacao.getSaida()).toMinutes();
            System.out.println(tempoForaExpediente);
            movimentacao.setTempoMulta(tempoForaExpediente);

            BigDecimal vMulta = configuracao.getValorMinutoMulta();
            BigDecimal vMultaTotal = vMulta.multiply(BigDecimal.valueOf(tempoForaExpediente));
            movimentacao.setValorMulta(vMultaTotal);
            int vHoraMulta = configuracao.getValorMinutoMulta().intValue() * 60;
            movimentacao.setValorHoraMulta(BigDecimal.valueOf(vHoraMulta));
        }

        //CALCULA VALOR TOTAL
        int valorMulta = movimentacao.getValorMulta().intValue();
        int valorHoras = movimentacao.getValorHora().intValue();
        int valorTotal = valorMulta + valorHoras;
        movimentacao.setValorTotal(BigDecimal.valueOf(valorTotal));

        //ARMAZENA AS HORAS PAGAS NO CONDUTO
        Long multaEmHoras = movimentacao.getTempoMulta() / 60;
        Long horasPagas = multaEmHoras + movimentacao.getTempoHoras();
        movimentacao.getCondutor().setTempoPagoHora(horasPagas + movimentacao.getCondutor().getTempoPagoHora());


        //CALCULA DESCONTO
        if (configuracao.isGerarDesconto() == true){
            if (movimentacao.getCondutor().getTempoPagoHora() > configuracao.getTempoParaDesconto()){
                Long horasDesconto = configuracao.getTempoDeDesconto();
                movimentacao.setTempoDesconto(horasDesconto);
                BigDecimal vDesconto = vHora.multiply(BigDecimal.valueOf(horasDesconto));
                movimentacao.setValorDesconto(vDesconto);
                int valorDesconto = movimentacao.getValorDesconto().intValue();
                int totalComDescoto = movimentacao.getValorTotal().intValue() - valorDesconto;
                movimentacao.setValorTotal(BigDecimal.valueOf(totalComDescoto));

                Long descontaHorasCondutor = movimentacao.getCondutor().getTempoPagoHora() - configuracao.getTempoParaDesconto();
                movimentacao.getCondutor().setTempoPagoHora(descontaHorasCondutor);
            }
        }
    }
}
