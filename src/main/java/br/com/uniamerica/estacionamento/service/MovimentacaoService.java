package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
