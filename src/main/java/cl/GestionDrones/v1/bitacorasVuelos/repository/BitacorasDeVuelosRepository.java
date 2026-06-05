package cl.GestionDrones.v1.bitacorasVuelos.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.GestionDrones.v1.bitacorasVuelos.model.BitacorasDeVuelos;


@Repository
public interface BitacorasDeVuelosRepository extends JpaRepository<BitacorasDeVuelos, Long> {
    

    Optional<BitacorasDeVuelos> findByIdPlanVuelo(Long idPlanVuelo);


    
}
