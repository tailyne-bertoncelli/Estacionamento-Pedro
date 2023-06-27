package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private MarcaRepository marcaRepository;
    public Modelo findById(Long id) {
        Optional<Modelo> modelo = this.modeloRepository.findById(id);
        return modelo.orElseThrow(() -> new RuntimeException("Modelo não encontrado!"));
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
        if (!marcaRepository.existsById(modelo.getMarca().getId())){
            throw new RuntimeException("Marca informada não cadastrada!");
        } else if (!marcaRepository.getById(modelo.getMarca().getId()).isAtivo()) {
            throw new RuntimeException("Marca informada está desativada!");
        }
        this.modeloRepository.save(modelo);
    }
    
    @Transactional
    public void cadastrar(final Modelo modelo){
        if (!marcaRepository.existsById(modelo.getMarca().getId())){
            throw new RuntimeException("Marca informada não cadastrada!");
        } else if (!marcaRepository.getById(modelo.getMarca().getId()).isAtivo()) {
            throw new RuntimeException("Marca informada está desativada!");
        }

        this.modeloRepository.save(modelo);
    }


    @Transactional
    public void desativar(Long id){
        var marca = modeloRepository.findById(id);
        if (id == marca.get().getId()){
            this.modeloRepository.desativaModelo(id);
        }
        else {
            throw new RuntimeException("A modelo não encontrado!");
        }
    }
    /*
    @Transactional
    public void ativar(Long id){
        var marca = modeloRepository.findById(id);
        if (id == marca.get().getId()){
            this.modeloRepository.ativaModelo(id);
        }
        else {
            throw new RuntimeException("A modelo não encontrado!");
        }
    }
    */
}
