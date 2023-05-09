package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.beans.Transient;
import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;


    public Marca findById(Long id){
        return this.marcaRepository.findById(id).orElse(new Marca());
    }
    public List<Marca> findAll(){
        return this.marcaRepository.findAll();
    }
    @Transactional(rollbackOn = Exception.class)
    public void cadastrar(final Marca marca){
        if (marca.getNome().trim().isEmpty()){
            throw new RuntimeException("Condutor sem nome informado!");
        } else {
            this.marcaRepository.save(marca);
        }
    }

    @Transactional
    public void altera(final Marca marca){
        this.marcaRepository.save(marca);
    }
    @Transactional
    public void deleta(final Marca marca){
        this.marcaRepository.delete(marca);
    }
}
