package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/condutor")
public class CondutorController {
    @Autowired
    private CondutorService condutorService;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id")final Long id){
        final Condutor condutor = this.condutorService.findById(id);

        return condutor == null
                ? ResponseEntity.badRequest().body("Nenhum condutor encontrado!")
                : ResponseEntity.ok(condutor);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(
            @RequestParam("id") final Long id
    ){
        final Condutor condutorBanco = this.condutorService.findById(id);

        this.condutorService.deleta(condutorBanco);
        return ResponseEntity.ok("Registro Excluido com Sucesso");
    }

//    @GetMapping("/condutor-ativo")
//    public ResponseEntity <?> ativo (){
//
//        List<Condutor> condutor = this.condutorService.findById(findByIdRequest());
//
//        List <Condutor> condutorAtivo = new ArrayList();
//
//        for (Condutor valor: condutor) {
//            if (valor.isAtivo())
//            {
//                condutorAtivo.add(valor);
//            }
//        }
//        return ResponseEntity.ok(condutorAtivo);
//    }

    @GetMapping("/lista-condutores")
    public List<Condutor> findAll(){
        return condutorService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Condutor condutor){

        try {
            this.condutorService.cadastrar(condutor);
            return ResponseEntity.ok("Condutor cadastrado com sucesso!");
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final Condutor condutor){

        final Condutor condutorBanco = this.condutorService.findById(id);
        if (condutorBanco == null || condutorBanco.getId().equals(condutor.getId())){
            throw new RuntimeException("Não foi possivel identificar o condutor informado!");

        }

        try {
            this.condutorService.cadastrar(condutor);
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
