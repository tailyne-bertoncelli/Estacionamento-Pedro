package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    @Modifying
    @Query("UPDATE Movimentacao movimentacao SET movimentacao.ativo=false WHERE movimentacao.id = :idMovimentacao")
    public void desativaMovimetacao(@Param("idMovimentacao")Long id);

    @Modifying
    @Query("UPDATE Movimentacao movimentacao SET movimentacao.ativo=true WHERE movimentacao.id = :idMovimentacao")
    public void ativaMovimetacao(@Param("idMovimentacao")Long id);

    @Query("SELECT configuracao FROM Configuracao configuracao")
    Configuracao buscaConfig();
}
