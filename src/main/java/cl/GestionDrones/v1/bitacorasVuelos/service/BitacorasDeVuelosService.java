package cl.GestionDrones.v1.bitacorasVuelos.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;



import org.springframework.stereotype.Service;

import cl.GestionDrones.v1.bitacorasVuelos.dto.CreateBitacorasRequest;
import cl.GestionDrones.v1.bitacorasVuelos.dto.UpdateBitacorasRequest;
import cl.GestionDrones.v1.bitacorasVuelos.exception.BitacoraDuplicadaException;
import cl.GestionDrones.v1.bitacorasVuelos.exception.ResourceNotFoundException;
import cl.GestionDrones.v1.bitacorasVuelos.mapper.BitacorasDeVuelosMapper;
import cl.GestionDrones.v1.bitacorasVuelos.model.BitacorasDeVuelos;
import cl.GestionDrones.v1.bitacorasVuelos.repository.BitacorasDeVuelosRepository;

@Service
public class BitacorasDeVuelosService {
    @Autowired
    private final BitacorasDeVuelosRepository bitacorasDeVuelosRepository;

    public BitacorasDeVuelosService(BitacorasDeVuelosRepository bitacorasDeVuelosRepository) {
        this.bitacorasDeVuelosRepository = bitacorasDeVuelosRepository;
    }

    // (POST)
    public BitacorasDeVuelos crearBitacora(CreateBitacorasRequest request) {
        if (bitacorasDeVuelosRepository.existsById(request.idPlanVuelo())) {
            throw new BitacoraDuplicadaException("Ya existe una bitácora para el plan de vuelo con ID: " + request.idPlanVuelo());
        }

        BitacorasDeVuelos nuevaBitacora = BitacorasDeVuelosMapper.toBitacoras(request);
        return bitacorasDeVuelosRepository.save(nuevaBitacora);
    }

    // (GET)
    public List<BitacorasDeVuelos> obtenerTodas() {
        return bitacorasDeVuelosRepository.findAll();
    }

    // (GET)
    public BitacorasDeVuelos obtenerPorId(Long id) {
        return bitacorasDeVuelosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la bitácora con ID: " + id));
    }

    // (PUT)
    public BitacorasDeVuelos actualizarBitacora(Long id, UpdateBitacorasRequest request) {
        BitacorasDeVuelos bitacoraExistente = obtenerPorId(id);

        BitacorasDeVuelosMapper.updateBitacoras(request, bitacoraExistente);

        return bitacorasDeVuelosRepository.save(bitacoraExistente);
    }

    // DELETE)
    public void eliminarBitacora(Long id) {
        if (!bitacorasDeVuelosRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. No existe la bitácora con ID: " + id);
        }
        bitacorasDeVuelosRepository.deleteById(id);
    }

    
}