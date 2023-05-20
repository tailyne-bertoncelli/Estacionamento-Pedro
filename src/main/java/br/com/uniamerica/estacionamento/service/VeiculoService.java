package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private MarcaRepository marcaRepository;

    public Veiculo findById(Long id) {
        Optional<Veiculo> veiculo = this.veiculoRepository.findById(id);
        return veiculo.orElseThrow(() -> new RuntimeException("Veiculo não encontrado!"));
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
        if (!modeloRepository.existsById(veiculo.getModelo().getId())) {
            throw new RuntimeException("Modelo informado não existe!");
        } else if (veiculo.getAno() > 2024 || veiculo.getAno() < 1900) {
            throw new RuntimeException("Ano invalido! Digite um ano entre 1900 a 2024!");
        }
        else {
            this.veiculoRepository.save(veiculo);
        }
    }

    @Transactional
    public void cadastra(final Veiculo veiculo){
        if (!modeloRepository.existsById(veiculo.getModelo().getId())) {
            throw new RuntimeException("Modelo informado não existe!");
        }
        else if (veiculo.getAno() > 2024 || veiculo.getAno() < 1900) {
            throw new RuntimeException("Ano invalido! Digite um ano entre 1900 a 2024!");
        }
        else {
            this.veiculoRepository.save(veiculo);
        } 
    }

    @Transactional
    public void desativar(Long id){
        var veiculo = veiculoRepository.findById(id);
        if (id == veiculo.get().getId()){
            this.veiculoRepository.desativaVeiculo(id);
        }
        else {
            throw new RuntimeException("A veiculo não encontrado!");
        }
    }

    @Transactional
    public void ativar(Long id){
        var veiculo = veiculoRepository.findById(id);
        if (id == veiculo.get().getId()){
            this.veiculoRepository.ativaVeiculo(id);
        }
        else {
            throw new RuntimeException("A veiculo não encontrado!");
        }
    }
}
