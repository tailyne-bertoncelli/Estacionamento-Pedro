package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Transactional
    public void cadastrar(final Marca marca){
        if (marca.getNome().length() < 3){
            throw new RuntimeException("Marca invalida!");
        }
        this.marcaRepository.save(marca);
    }

    @Transactional
    public void altera(final Marca marca){
        this.marcaRepository.save(marca);
    }

    @Transactional
    public void deleta(final Marca marca){
        this.marcaRepository.delete(marca);
    }

    @Transactional
    public void desativar(Long id){
        var marca = marcaRepository.findById(id);
        if (id == marca.get().getId()){
            this.marcaRepository.desativaMarca(id);
        }
        else {
            throw new RuntimeException("A marca já esta desativada!");
        }
    }

    @Transactional
    public void ativar(Long id){
        var marca = marcaRepository.findById(id);
        if (id == marca.get().getId()){
            this.marcaRepository.ativaMarca(id);
        }
        else {
            throw new RuntimeException("A marca já esta ativada!");
        }
    }
}
