package cl.GestionDrones.v1.bitacorasVuelos.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import cl.GestionDrones.v1.bitacorasVuelos.client.IncidenciasWebClient;
import cl.GestionDrones.v1.bitacorasVuelos.client.PlanesDeVuelosClient;
import cl.GestionDrones.v1.bitacorasVuelos.dto.BitacoraDetalleResponse;
import cl.GestionDrones.v1.bitacorasVuelos.dto.CreateBitacorasRequest;
import cl.GestionDrones.v1.bitacorasVuelos.dto.PlanDeVueloDto;
import cl.GestionDrones.v1.bitacorasVuelos.dto.ReporteIncidente;
import cl.GestionDrones.v1.bitacorasVuelos.dto.UpdateBitacorasRequest;
import cl.GestionDrones.v1.bitacorasVuelos.exception.BitacoraDuplicadaException;
import cl.GestionDrones.v1.bitacorasVuelos.exception.ResourceNotFoundException;
import cl.GestionDrones.v1.bitacorasVuelos.mapper.BitacorasDeVuelosMapper;
import cl.GestionDrones.v1.bitacorasVuelos.model.BitacorasDeVuelos;
import cl.GestionDrones.v1.bitacorasVuelos.repository.BitacorasDeVuelosRepository;

@Service
public class BitacorasDeVuelosService {

    private final BitacorasDeVuelosRepository bitacorasDeVuelosRepository;
    private final PlanesDeVuelosClient planesDeVuelosClient;
    private final IncidenciasWebClient incidenciasClient;

    public BitacorasDeVuelosService(BitacorasDeVuelosRepository bitacorasRepository,
                                    PlanesDeVuelosClient planesClient,
                                    IncidenciasWebClient incidenciasClient) {
        this.bitacorasDeVuelosRepository = bitacorasRepository;
        this.planesDeVuelosClient = planesClient;
        this.incidenciasClient = incidenciasClient;
    }

    public BitacorasDeVuelos crearBitacora(CreateBitacorasRequest request) {

        
        bitacorasDeVuelosRepository.findByIdPlanVuelo(request.idPlanVuelo())
            .ifPresent(b -> {
                throw new BitacoraDuplicadaException(
                    "Ya existe una bitácora para el plan de vuelo con ID: " + request.idPlanVuelo());
            });
        
        planesDeVuelosClient.obtenerPorId(request.idPlanVuelo());

        BitacorasDeVuelos nuevaBitacora = BitacorasDeVuelosMapper.toBitacoras(request);
        nuevaBitacora.setFirmaDigital(UUID.randomUUID().toString());
        nuevaBitacora.setFechaCierre(LocalDateTime.now());

        BitacorasDeVuelos guardada = bitacorasDeVuelosRepository.save(nuevaBitacora);

        return guardada;
    }

    public BitacoraDetalleResponse obtenerPorIdConDetalle(Long id) {

        BitacorasDeVuelos bitacora = bitacorasDeVuelosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se encontró la bitácora con ID: " + id));


        PlanDeVueloDto plan = null;
        try {
            plan = planesDeVuelosClient.obtenerPorId(bitacora.getIdPlanVuelo());
        } catch (Exception e) {
            System.out.println("No se pudo obtener el plan de vuelo ID "
                + bitacora.getIdPlanVuelo() + ": " + e.getMessage());
        }

        List<ReporteIncidente> incidencias = Collections.emptyList();
        try {
            incidencias = incidenciasClient.obtenerPorPlanVuelo(bitacora.getIdPlanVuelo());
        } catch (Exception e) {
            System.out.println("No se pudieron obtener incidencias: " + e.getMessage());
        }

        return new BitacoraDetalleResponse(bitacora, plan, incidencias);
    }

    public List<BitacorasDeVuelos> obtenerTodas() {
        return bitacorasDeVuelosRepository.findAll();
    }

    public BitacorasDeVuelos obtenerPorId(Long id) {
        return bitacorasDeVuelosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se encontró la bitácora con ID: " + id));
    }

    public BitacorasDeVuelos actualizarBitacora(Long id, UpdateBitacorasRequest request) {
        BitacorasDeVuelos bitacoraExistente = obtenerPorId(id);
        BitacorasDeVuelosMapper.updateBitacoras(request, bitacoraExistente);
        return bitacorasDeVuelosRepository.save(bitacoraExistente);
    }

    public void eliminarBitacora(Long id) {
        if (!bitacorasDeVuelosRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                "No se puede eliminar. No existe la bitácora con ID: " + id);
        }
        bitacorasDeVuelosRepository.deleteById(id);
    }
}