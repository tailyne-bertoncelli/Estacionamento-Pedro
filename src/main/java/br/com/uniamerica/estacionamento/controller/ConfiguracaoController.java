package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.*;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.service.CondutorService;
import br.com.uniamerica.estacionamento.service.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/configuracao")
public class ConfiguracaoController {
    @Autowired
    private ConfiguracaoService configuracaoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id")final Long id){

        try {
            this.configuracaoService.findById(id);
            return ResponseEntity.ok().body(this.configuracaoService.findById(id));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Nenhuma configuração encontrado!");
        }
    }

    @GetMapping("/lista-configuracoes")
    public List<Configuracao> findAll(){
        return configuracaoService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@Validated @RequestBody final Configuracao configuracao){

        try {
            this.configuracaoService.cadastrar(configuracao);
            return ResponseEntity.ok("Configuração realizada com sucesso!");
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable("id") final Long id,
                                    @Validated @RequestBody final Configuracao configuracao){


        Configuracao configuracaoBanco = configuracaoService.findById(id);
        configuracaoBanco.setValorHora(configuracao.getValorHora());
        configuracaoBanco.setValorMinutoMulta(configuracao.getValorMinutoMulta());
        configuracaoBanco.setInicioExpediente(configuracao.getInicioExpediente());
        configuracaoBanco.setFimExpediente(configuracao.getFimExpediente());
        configuracaoBanco.setTempoParaDesconto(configuracao.getTempoParaDesconto());
        configuracaoBanco.setTempoDeDesconto(configuracao.getTempoDeDesconto());
        configuracaoBanco.setGerarDesconto(configuracao.isGerarDesconto()); //Booleano
        configuracaoBanco.setVagasCarro(configuracao.getVagasCarro());
        configuracaoBanco.setVagasMoto(configuracao.getVagasMoto());
        configuracaoBanco.setVagasVan(configuracao.getVagasVan());

        try {
            this.configuracaoService.altera(configuracaoBanco);
            return ResponseEntity.ok("Configuração alterada com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro ao alterar configuração!" +e.getMessage());
        }
    }

}
