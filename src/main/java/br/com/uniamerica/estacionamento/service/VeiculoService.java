package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepository veiculoRepository;

    public Veiculo findById(Long id){
        return this.veiculoRepository.findById(id).orElse(new Veiculo());
    }

    public List<Veiculo> findAll(){
        return this.veiculoRepository.findAll();
    }

    @Transactional
    public void deleta(final Veiculo veiculo){
        this.veiculoRepository.delete(veiculo);
    }

    @Transactional
    public void altera(final Veiculo veiculo){
        this.veiculoRepository.save(veiculo);
    }

    @Transactional
    public void cadastra(final Veiculo veiculo){
        if (veiculo.getPlaca() == null){
            throw new RuntimeException("Veiculo sem placa informada!");
        } else if (veiculo.getCor() == null) {
            throw new RuntimeException("Cor do veiculo não informada!");
        } else {
            this.veiculoRepository.save(veiculo);
        } 
    }
}
