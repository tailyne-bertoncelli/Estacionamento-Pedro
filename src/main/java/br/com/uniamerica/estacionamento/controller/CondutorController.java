package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/condutor")
public class CondutorController {
    @Autowired
    private CondutorService condutorService;

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id")final Long id){

        try {
            this.condutorService.findById(id);
            return ResponseEntity.ok().body(this.condutorService.findById(id));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Nenhuma marca encontrada!");
        }
    }


    @DeleteMapping
    public ResponseEntity<?> delete(
            @RequestParam("id") final Long id
    ){
        final Condutor condutorBanco = this.condutorService.findById(id);

        this.condutorService.deleta(condutorBanco);
        return ResponseEntity.ok("Registro excluido com Sucesso");
    }

    @GetMapping("/condutores-ativos")
    public ResponseEntity <?> ativo (){

        List<Condutor> condutor = this.condutorService.findAll();

        List <Condutor> condutorAtivo = new ArrayList();

        for (Condutor valor: condutor) {
            if (valor.isAtivo())
            {
                condutorAtivo.add(valor);
            }
        }
        return ResponseEntity.ok(condutorAtivo);
    }

    @GetMapping("/lista-condutores")
    public List<Condutor> findAll(){
        return condutorService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Validated final Condutor condutor){

        try {
            this.condutorService.cadastrar(condutor);
            return ResponseEntity.ok("Condutor cadastrado com sucesso!");
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Condutor já cadastrado ou erro: "+ e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @Validated @RequestBody final Condutor condutor){

        Condutor condutorBanco = condutorService.findById(id);
        condutorBanco.setNome(condutor.getNome());
        condutorBanco.setCpf(condutor.getCpf());
        condutorBanco.setTelefone(condutor.getTelefone());

        try {
            this.condutorService.altera(condutorBanco);
            return ResponseEntity.ok("Condutor alterado com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro: "+ e.getMessage());
        }
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<?> desativar(@PathVariable("id")Long id){
        try {
            this.condutorService.desativar(id);
            return ResponseEntity.ok("Condutor desativado com sucesso!");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Condutor não encontrado!");
        }
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<?> ativar(@PathVariable Long id){
        try {
            this.condutorService.ativar(id);
            return ResponseEntity.ok("Condutor ativo com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Condutor não encontrado!");
        }
    }

}




















