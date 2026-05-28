package cl.GestionDrones.v1.bitacorasVuelos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.GestionDrones.v1.bitacorasVuelos.model.BitacorasDeVuelos;


@Repository
public interface BitacorasDeVuelosRepository extends JpaRepository<BitacorasDeVuelos, Long> {
    

    
    @Query(value = "SELECT * FROM bitacoras_vuelo WHERE idPlanVuelo = :id", nativeQuery = true)
    List<BitacorasDeVuelos> buscarPorId(@Param("id") String id);

    @Query(value = "SELECT * FROM bitacoras_vuelo WHERE estadoFinal = :estado", nativeQuery = true)
    List<BitacorasDeVuelos> buscarPorEstado(@Param("estado") String estado);

    
}
