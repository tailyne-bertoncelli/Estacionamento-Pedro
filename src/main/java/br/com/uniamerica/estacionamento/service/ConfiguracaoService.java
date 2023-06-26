package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    public Configuracao findById(Long id) {
        Optional<Configuracao> configuracao = this.configuracaoRepository.findById(id);
        return configuracao.orElseThrow(() -> new RuntimeException("Configuração não encontrada!"));
    }

    public List<Configuracao> findAll(){
        return this.configuracaoRepository.findAll();
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
