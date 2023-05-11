package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.service.CondutorService;
import br.com.uniamerica.estacionamento.service.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/configuracao")
public class ConfiguracaoController {
    @Autowired
    private ConfiguracaoService configuracaoService;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id")final Long id){
        final Configuracao configuracao = this.configuracaoService.findById(id);

        return configuracao == null
                ? ResponseEntity.badRequest().body("Nenhum registro de configuração encontrado!")
                : ResponseEntity.ok(configuracao);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Configuracao configuracao){

        try {
            this.configuracaoService.cadastrar(configuracao);
            return ResponseEntity.ok("Configuração realizada com sucesso!");
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final Configuracao configuracao){

        final Configuracao configuracaoBanco = this.configuracaoService.findById(id);
        if (configuracaoBanco == null || configuracaoBanco.getId().equals(configuracao.getId())){
            throw new RuntimeException("Não foi possivel identificar o registro informado.");

        }

        try {
            this.configuracaoService.altera(configuracao);
            return ResponseEntity.ok("Registro atualizado com sucesso!");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getMessage());
        }
    }

}
