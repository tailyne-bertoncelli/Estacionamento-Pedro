package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

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
        } else if (movimentacao.getVeiculo() == null) {
            throw new RuntimeException("Movimentação sem veiculo!");
        } else {
            this.movimentacaoRepository.save(movimentacao);
        }
    }
}
