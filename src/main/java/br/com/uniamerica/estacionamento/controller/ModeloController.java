package br.com.uniamerica.estacionamento.controller;

import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/modelo")
public class ModeloController {
    @Autowired
    private ModeloRepository modeloRepository;

    //Passa na URL /modelo/1
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id")final Long id){
        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);

        return modelo == null
            ? ResponseEntity.badRequest().body("Nenhuma marca encontrada!")
            : ResponseEntity.ok(modelo);
    }

    //Passa na URL /modelo?id=1 (Fica mais claro na url onde estamos mexendo
   @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id")final Long id){
        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);

        return modelo == null
                ? ResponseEntity.badRequest().body("Nenhuma marca encontrada!")
                : ResponseEntity.ok(modelo);
    }

    @GetMapping("/modelo-ativo")
    public ResponseEntity <?> ativo (){

        List<Modelo> modelo = this.modeloRepository.findAll();

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
        return modeloRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Modelo modelo){

        try {
            this.modeloRepository.save(modelo);
            return ResponseEntity.ok("Registro cadastrado com sucesso!");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: ");
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final Modelo modelo){

        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);
        if (modeloBanco == null || modeloBanco.getId().equals(modelo.getId())){
            throw new RuntimeException("Não foi possivel identificar o registro informado.");

        }

        try {
            this.modeloRepository.save(modelo);
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
        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);

        this.modeloRepository.delete(modeloBanco);
        return ResponseEntity.ok("Registro Excluido com Sucesso");
    }


}
