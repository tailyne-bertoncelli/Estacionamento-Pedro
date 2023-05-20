package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CondutorRepository extends JpaRepository<Condutor, Long> {
    @Modifying
    @Query("UPDATE Condutor condutor SET condutor.ativo=false WHERE condutor.id = :idCondutor")
    public void desativaCondutor (@Param("idCondutor")Long id);

    @Modifying
    @Query("UPDATE Condutor condutor SET condutor.ativo=true WHERE condutor.id = :idCondutor")
    public void ativaCondutor (@Param("idCondutor")Long id);
}
