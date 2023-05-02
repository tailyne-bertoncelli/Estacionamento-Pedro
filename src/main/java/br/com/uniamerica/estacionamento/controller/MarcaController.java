package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/marca")
public class MarcaController {
    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id")final Long id){
        final Marca marca = this.marcaRepository.findById(id).orElse(null);
        return marca == null
                ? ResponseEntity.badRequest().body("Nenhum valor encontrado!")
                : ResponseEntity.ok(marca);
    }

    @GetMapping("/marca-ativa")
    public ResponseEntity <?> ativo (){

        List<Marca> marca = this.marcaRepository.findAll();

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
        return marcaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Marca marca){
        try {
            this.marcaRepository.save(marca);
            return ResponseEntity.ok("Marca cadastrada com sucesso!");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: ");
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final Marca marca){

        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);
        if (marcaBanco == null || marcaBanco.getId().equals(marca.getId())){
            throw new RuntimeException("Não foi possivel identificar o registro informado.");

        }

        try {
            this.marcaRepository.save(marca);
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
        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);

        this.marcaRepository.delete(marcaBanco);
        return ResponseEntity.ok("Registro Excluido com Sucesso");
    }

}
