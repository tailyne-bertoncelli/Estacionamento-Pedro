package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    /*
    @Query(value = "SELECT marca FROM Marca marca WHERE marca.id != :id")
    public boolean validaId(@Param("id") Long id);
    public Marca findByNomeAndAtivoTrue(final String nome);
    */

    /*
    @Modifying
    @Query("UPDATE Marca marca SET marca.ativo=false WHERE marca.id = :idMarca")
    public void desativaMarca (@Param("idMarca")Long id);

    @Modifying
    @Query("UPDATE Marca marca SET marca.ativo=true WHERE marca.id = :idMarca")
    public void ativaMarca (@Param("idMarca")Long id);
    */
}
