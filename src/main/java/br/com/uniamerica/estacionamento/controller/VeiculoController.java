package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/veiculo")
public class VeiculoController {
    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Veiculo veiculo = this.veiculoService.findById(id);

        return veiculo == null
                ? ResponseEntity.badRequest().body("Nenhum veiculo encontrado!")
                : ResponseEntity.ok(veiculo);
    }

    @GetMapping("/veiculo-ativo")
    public ResponseEntity <?> ativo (){

        List<Veiculo> veiculo = this.veiculoService.findAll();

        List <Veiculo> veiculosAtivo = new ArrayList();

        for (Veiculo valor: veiculo) {
            if (valor.isAtivo())
            {
                veiculosAtivo.add(valor);
            }
        }
        return ResponseEntity.ok(veiculosAtivo);
    }

    @GetMapping("/lista-veiculo")
    public List<Veiculo> findAll(){
        return veiculoService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Veiculo veiculo){

        try {
            this.veiculoService.cadastra(veiculo);
            return ResponseEntity.ok("Veiculo cadastrado com sucesso!");
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final Veiculo veiculo){

        final Veiculo veiculoBanco = this.veiculoService.findById(id);
        if (veiculoBanco == null || veiculoBanco.getId().equals(veiculo.getId())){
            throw new RuntimeException("Não foi possivel identificar o veiculo informado.");

        }

        try {
            this.veiculoService.altera(veiculo);
            return ResponseEntity.ok("Veiculo atualizado com sucesso!");
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
        final Veiculo veiculoBanco = this.veiculoService.findById(id);

        this.veiculoService.deleta(veiculoBanco);
        return ResponseEntity.ok("Veiculo excluido com Sucesso");
    }
}
