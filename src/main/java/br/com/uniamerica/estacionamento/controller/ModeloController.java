package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/modelo")
public class ModeloController {
    @Autowired
    private ModeloService modeloService;

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id")final Long id){

        try {
            this.modeloService.findById(id);
            return ResponseEntity.ok().body(this.modeloService.findById(id));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Nenhum modelo encontrado!");
        }
    }


    @GetMapping("/modelo-ativo")
    public ResponseEntity <?> ativo (){

        List<Modelo> modelo = this.modeloService.findAll();

        List <Modelo> modeloAtivo = new ArrayList();

        for (Modelo valor: modelo) {
            if (valor.isAtivo())
            {
                modeloAtivo.add(valor);
            }
        }
        return ResponseEntity.ok(modeloAtivo);
    }

    @GetMapping("/lista-modelo")
    public List<Modelo> findAll(){
        return modeloService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Validated final Modelo modelo){

        try {
            this.modeloService.cadastrar(modelo);
            return ResponseEntity.ok("Registro cadastrado com sucesso!");
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @Validated @RequestBody final Modelo modelo){

        Modelo modeloBanco = modeloService.findById(id);
        modeloBanco.setNome(modelo.getNome());

        try {
            this.modeloService.altera(modeloBanco);
            return ResponseEntity.ok("Modelo alterada com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro ao alterar modelo!");
        }
    }

/*
    @PutMapping("/desativar/{id}")
    public ResponseEntity<?> desativaModelo(@PathVariable Long id){
        try {
            this.modeloService.desativar(id);
            return ResponseEntity.ok("Modelo desativada com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Modelo não encontrado!");
        }
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<?> ativaMarca(@PathVariable Long id){
        try {
            this.modeloService.ativar(id);
            return ResponseEntity.ok("Modelo ativada com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Modelo não encontrado!");
        }
    }
    */

    @DeleteMapping
    public ResponseEntity<?> delete(
            @RequestParam("id") final Long id
    ){
        final Modelo modeloBanco = this.modeloService.findById(id);

        this.modeloService.deleta(modeloBanco);
        return ResponseEntity.ok("Registro Excluido com Sucesso");
    }


}
