package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private MarcaRepository marcaRepository;
    public Modelo findById(Long id){
        return this.modeloRepository.findById(id).orElse(new Modelo());
    }

    public List<Modelo> findAll(){
        return this.modeloRepository.findAll();
    }

    @Transactional
    public void deleta(final Modelo modelo){
        this.modeloRepository.delete(modelo);
    }

    @Transactional
    public void altera(final Modelo modelo){
        this.modeloRepository.save(modelo);
    }
    
    @Transactional
    public void cadastrar(final Modelo modelo){
        if (modelo.getNome().isEmpty()){
            throw new RuntimeException("Modelo sem nome informado!");
        }
        if (marcaRepository.existsById(modelo.getMarca().getId())){
            throw new RuntimeException("Marca informada não cadastrada!");
        }

        this.modeloRepository.save(modelo);
    }
}
