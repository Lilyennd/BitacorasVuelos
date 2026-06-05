package cl.GestionDrones.v1.bitacorasVuelos.mapper;

import java.time.LocalDateTime;
import java.util.UUID;

import cl.GestionDrones.v1.bitacorasVuelos.dto.CreateBitacorasRequest;
import cl.GestionDrones.v1.bitacorasVuelos.dto.UpdateBitacorasRequest;
import cl.GestionDrones.v1.bitacorasVuelos.model.BitacorasDeVuelos;

public class BitacorasDeVuelosMapper {

    public static BitacorasDeVuelos toBitacoras(CreateBitacorasRequest request) {
        return new BitacorasDeVuelos(
            null,
            request.idPlanVuelo(),
            request.duracionRealMinutos(),
            request.observaciones(),
            UUID.randomUUID().toString(), 
            LocalDateTime.now());
    }

    public static void updateBitacoras(UpdateBitacorasRequest dto, BitacorasDeVuelos entity) {
        entity.setDuracionRealMinutos(dto.duracionRealMinutos());
        entity.setObservaciones(dto.observaciones());
    }
}
