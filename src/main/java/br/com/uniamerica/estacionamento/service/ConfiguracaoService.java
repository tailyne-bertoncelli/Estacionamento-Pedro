package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    public Configuracao findById(Long id){
        return this.configuracaoRepository.findById(id).orElse(new Configuracao());
    }

    @Transactional
    public void altera(final Configuracao configuracao){
        this.configuracaoRepository.save(configuracao);
    }

    @Transactional
    public void cadastrar(final Configuracao configuracao){
        this.configuracaoRepository.save(configuracao);
    }
}
