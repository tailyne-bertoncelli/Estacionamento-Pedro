package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    @Modifying
    @Query("UPDATE Modelo modelo SET modelo.ativo=false WHERE modelo.id = :idModelo")
    public void desativaModelo (@Param("idModelo")Long id);
    /*
    @Modifying
    @Query("UPDATE Modelo modelo SET modelo.ativo=true WHERE modelo.id = :idModelo")
    public void ativaModelo (@Param("idModelo")Long id);
    */
}
