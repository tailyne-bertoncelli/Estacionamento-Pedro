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

@RestController
@RequestMapping(value = "/api/marca")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id")final Long id){
        final Marca marca = this.marcaService.findById(id);
        return marca == null
                ? ResponseEntity.badRequest().body("Nenhum valor encontrado!")
                : ResponseEntity.ok(marca);
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
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar( @Validated @RequestParam("id") final Long id, @RequestBody final Marca marca){

//        final Marca marcaBanco = this.marcaService.findById(id);
//        if (marcaBanco == null || marcaBanco.getId().equals(marca.getId())){
//            throw new RuntimeException("Não foi possivel identificar o registro informado.");
//
//        }

        try {
            this.marcaService.altera(marca);
            return ResponseEntity.ok("Registro atualizado com sucesso!");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getMessage());
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

    @DeleteMapping
    public ResponseEntity<?> delete(
            @RequestParam("id") final Long id
    ){
        final Marca marcaBanco = this.marcaService.findById(id);

        this.marcaService.deleta(marcaBanco);
        return ResponseEntity.ok("Registro Excluido com Sucesso");
    }

}
