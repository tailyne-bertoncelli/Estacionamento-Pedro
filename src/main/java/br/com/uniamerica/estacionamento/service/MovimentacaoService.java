package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    public Movimentacao findById(Long id){
        return this.movimentacaoRepository.findById(id).orElse(new Movimentacao());
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
        this.movimentacaoRepository.save(movimentacao);
    }

    @Transactional
    public void cadastra(final Movimentacao movimentacao){
        if (movimentacao.getEntrada() == null){
            throw new RuntimeException("Movimentação sem entrada!");
        } else if (movimentacao.getCondutor() == null){
            throw new RuntimeException("Movimentação sem condutor!");
        } else if (!condutorRepository.existsById(movimentacao.getCondutor().getId())) {
            throw new RuntimeException("Condutor informado não existe!");
        } else if (movimentacao.getVeiculo() == null) {
            throw new RuntimeException("Movimentação sem veiculo!");
        } else if (!veiculoRepository.existsById(movimentacao.getVeiculo().getId())) {
            throw new RuntimeException("Veiculo informado não existe!");
        } else {
            this.movimentacaoRepository.save(movimentacao);
        }
    }
}
