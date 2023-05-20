package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/movimentacao")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoService movimentacaoService;

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id")final Long id){

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

    @GetMapping("/lista-movimentacao")
    public List<Movimentacao> findAll(){
        return movimentacaoService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@Validated @RequestBody final Movimentacao movimentacao){

        try {
            this.movimentacaoService.cadastra(movimentacao);
            return ResponseEntity.ok("Movimentação registrada com sucesso!");
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@Validated @RequestParam("id") final Long id, @RequestBody final Movimentacao movimentacao){

        final Movimentacao movimentacaoBanco = this.movimentacaoService.findById(id);
        if (movimentacaoBanco == null || movimentacaoBanco.getId().equals(movimentacao.getId())){
            throw new RuntimeException("Não foi possivel identificar o registro informado.");

        }

        try {
            this.movimentacaoService.altera(movimentacao);
            return ResponseEntity.ok("Registro atualizado com sucesso!");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
            @RequestParam("id") final Long id
    ){
        final Movimentacao movimentacaoBanco = this.movimentacaoService.findById(id);

        this.movimentacaoService.deleta(movimentacaoBanco);
        return ResponseEntity.ok("Movimentacao excluida com Sucesso");
    }
}
