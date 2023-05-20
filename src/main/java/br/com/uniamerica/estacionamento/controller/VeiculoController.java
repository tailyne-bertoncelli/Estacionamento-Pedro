package br.com.uniamerica.estacionamento.controller;


import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/veiculo")
public class VeiculoController {
    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id")final Long id){

        try {
            this.veiculoService.findById(id);
            return ResponseEntity.ok().body(this.veiculoService.findById(id));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Nenhum veiculo encontrado!");
        }
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
    public ResponseEntity<?> cadastrar(@Validated @RequestBody final Veiculo veiculo){

        try {
            this.veiculoService.cadastra(veiculo);
            return ResponseEntity.ok("Veiculo cadastrado com sucesso!");
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Um veiculo com essa placa já foi cadastrado!");
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @Validated @RequestBody final Veiculo veiculo){

        Veiculo veiculoBanco = veiculoService.findById(id);
        veiculoBanco.setPlaca(veiculo.getPlaca());
        veiculoBanco.setModelo(veiculo.getModelo());
        veiculoBanco.setTipo(veiculo.getTipo());
        veiculoBanco.setAno(veiculo.getAno());
        veiculoBanco.setCor(veiculo.getCor());

        try {
            this.veiculoService.altera(veiculoBanco);
            return ResponseEntity.ok("Condutor alterada com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro ao alterar condutor!");
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

    @PutMapping("ativa/{id}")
    public ResponseEntity<?> ativaVeiculo(@PathVariable Long id){
        try {
            this.veiculoService.ativar(id);
            return ResponseEntity.ok("Veiculo ativado com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Veiculo já está ativo!");
        }
    }

    @PutMapping("desativa/{id}")
    public ResponseEntity<?> desativaVeiculo(@PathVariable Long id){
        try {
            this.veiculoService.desativar(id);
            return ResponseEntity.ok("Veiculo desativado com sucesso!");
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Veiculo já está desativado!");
        }
    }
}
