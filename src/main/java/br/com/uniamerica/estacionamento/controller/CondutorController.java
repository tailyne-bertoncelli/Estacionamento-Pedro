package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/condutor")
public class CondutorController {
    @Autowired
    private CondutorRepository condutorRepository;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id")final Long id){
        final Condutor condutor = this.condutorRepository.findById(id).orElse(null);

        return condutor == null
                ? ResponseEntity.badRequest().body("Nenhum condutor encontrado!")
                : ResponseEntity.ok(condutor);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
            @RequestParam("id") final Long id
    ){
        final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);

        this.condutorRepository.delete(condutorBanco);
        return ResponseEntity.ok("Registro Excluido com Sucesso");
    }

    @GetMapping("/condutor-ativo")
    public ResponseEntity <?> ativo (){

        List<Condutor> condutor = this.condutorRepository.findAll();

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
        return condutorRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Condutor condutor){

        try {
            this.condutorRepository.save(condutor);
            return ResponseEntity.ok("Condutor cadastrado com sucesso!");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: ");
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final Condutor condutor){

        final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);
        if (condutorBanco == null || condutorBanco.getId().equals(condutor.getId())){
            throw new RuntimeException("Não foi possivel identificar o condutor informado!");

        }

        try {
            this.condutorRepository.save(condutor);
            return ResponseEntity.ok("Condutor atualizado com sucesso!");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getMessage());
        }
    }
}
