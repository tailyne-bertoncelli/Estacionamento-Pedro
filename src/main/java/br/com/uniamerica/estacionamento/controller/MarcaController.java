package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "/api/marca")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;
    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id")final Long id){

        try {
            this.marcaService.findById(id);
            return ResponseEntity.ok().body(this.marcaService.findById(id));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Nenhuma marca encontrada!");
        }
    }

    @GetMapping("/marca-ativa")
    public ResponseEntity <?> ativo (){

        List<Marca> marca = this.marcaService.findAll();

        List <Marca> marcaAtiva = new ArrayList();

        for (Marca valor: marca) {
            if (valor.isAtivo())
            {
                marcaAtiva.add(valor);
            }
        }
        return ResponseEntity.ok(marcaAtiva);
    }

    @GetMapping("/lista-marcas")
    public List<Marca> findAll(){
        return marcaService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Validated final Marca marca){
        try {
            this.marcaService.cadastrar(marca);
            return ResponseEntity.ok("Marca cadastrada com sucesso!");
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Marca já cadastrada!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable("id") final Long id,
                                    @Validated @RequestBody final Marca marca){


        Marca marcaBanco = marcaService.findById(id);
        marcaBanco.setNome(marca.getNome());

        try {
            this.marcaService.altera(marcaBanco);
            return ResponseEntity.ok("Marca alterada com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro: "+ e.getMessage());
        }
    }



    @PutMapping("/desativar/{id}")
    public ResponseEntity<?> desativaMarca(@PathVariable Long id){
        try {
            this.marcaService.desativar(id);
            return ResponseEntity.ok("Marca desativada com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Marca não encontrada!");
        }
    }

/*
    @PutMapping("/ativar/{id}")
    public ResponseEntity<?> ativaMarca(@PathVariable Long id){
        try {
            this.marcaService.ativar(id);
            return ResponseEntity.ok("Marca ativada com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Marca não encontrada!");
        }
    }
    */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") final Long id
    ){
        final Marca marcaBanco = this.marcaService.findById(id);

        this.marcaService.deleta(marcaBanco);
        return ResponseEntity.ok("Registro Excluido com Sucesso");
    }

}
