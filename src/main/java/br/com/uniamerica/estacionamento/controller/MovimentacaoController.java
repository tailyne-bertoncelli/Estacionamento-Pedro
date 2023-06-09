package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "/api/movimentacao")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoService movimentacaoService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id")final Long id){

        try {
            this.movimentacaoService.findById(id);
            return ResponseEntity.ok().body(this.movimentacaoService.findById(id));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Nenhum movimentação encontrada!");
        }
    }

    @GetMapping("/movimentacao-aberta")
    public ResponseEntity <?> aberta (){

        List<Movimentacao> movimentacao = this.movimentacaoService.findAll();

        List <Movimentacao> movimentacaoAberta = new ArrayList();

        for (Movimentacao valor: movimentacao) {
            if (valor.getSaida() == null)
            {
                movimentacaoAberta.add(valor);
            }
        }
        return ResponseEntity.ok(movimentacaoAberta);
    }

    @GetMapping("/movimentacoes-finalizadas")
    public ResponseEntity <?> finalizadas(){

        List<Movimentacao> movimentacao = this.movimentacaoService.findAll();

        List <Movimentacao> movimentacaoFinalizada = new ArrayList();

        for (Movimentacao valor: movimentacao) {
            if (valor.isAtivo() == false)
            {
                movimentacaoFinalizada.add(valor);
            }
        }
        return ResponseEntity.ok(movimentacaoFinalizada);
    }

    @GetMapping("/lista-movimentacao")
    public List<Movimentacao> findAll(){
        return movimentacaoService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@Validated @RequestBody final Movimentacao movimentacao){
        //movimentacao.setEntradaMov();
        try {
            this.movimentacaoService.cadastra(movimentacao);
            return ResponseEntity.ok("Movimentação registrada com sucesso!");
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable("id") final Long id,
                                    @Validated @RequestBody final Movimentacao movimentacao){

        Movimentacao movimentacaoBanco = movimentacaoService.findById(id);
        movimentacaoBanco.setCondutor(movimentacao.getCondutor());
        movimentacaoBanco.setVeiculo(movimentacao.getVeiculo());
        movimentacaoBanco.setTempoHoras(movimentacao.getTempoHoras());
        movimentacaoBanco.setTempoDesconto(movimentacao.getTempoDesconto());

        try {
            this.movimentacaoService.altera(movimentacaoBanco);
            return ResponseEntity.ok("Movimentacao alterada com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Movimentação já cadasstrada! Ou erro: "+ e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") final Long id
    ){
        final Movimentacao movimentacaoBanco = this.movimentacaoService.findById(id);

        this.movimentacaoService.deleta(movimentacaoBanco);
        return ResponseEntity.ok("Movimentacao excluida com Sucesso");
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<?> desativaMovimentacao(@PathVariable Long id){
        try {
            this.movimentacaoService.desativar(id);
            return ResponseEntity.ok("Movimentação desativada com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Movimentacao não encontrada!");
        }
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<?> ativaMovimentacao(@PathVariable Long id){
        try {
            this.movimentacaoService.ativar(id);
            return ResponseEntity.ok("Movimentação ativada com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Movimentação não encontrada!");
        }
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<?> finalizar(@PathVariable("id")final Long id){
        Movimentacao movimentacao1 = this.movimentacaoService.findById(id);
        //Condutor condutor = movimentacao.getCondutor();
        //movimentacao1.setSaida(movimentacao.getSaida());
        try {
            this.movimentacaoService.finalizaMov(id, movimentacao1);
            return ResponseEntity.ok("Movimentação finalizada com sucesso! Obrigada!");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Erro ao finalizar movimentação!");
        }
    }
}
