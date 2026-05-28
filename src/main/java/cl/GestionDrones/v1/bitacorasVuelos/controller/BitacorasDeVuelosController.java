package cl.GestionDrones.v1.bitacorasVuelos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import cl.GestionDrones.v1.bitacorasVuelos.dto.CreateBitacorasRequest;
import cl.GestionDrones.v1.bitacorasVuelos.dto.UpdateBitacorasRequest;
import cl.GestionDrones.v1.bitacorasVuelos.model.BitacorasDeVuelos;
import cl.GestionDrones.v1.bitacorasVuelos.service.BitacorasDeVuelosService;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/v1/bitacoras")
public class BitacorasDeVuelosController {

    private final BitacorasDeVuelosService bitacorasDeVuelosService;

    public BitacorasDeVuelosController(BitacorasDeVuelosService bitacorasDeVuelosService) {
        this.bitacorasDeVuelosService = bitacorasDeVuelosService;
    }

    // 1. Crear (POST)
  @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@Valid @RequestBody CreateBitacorasRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return manejarErrores(result);
        }

        BitacorasDeVuelos nuevaBitacora = bitacorasDeVuelosService.crearBitacora(request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CREATED.value());
        response.put("mensaje", "Bitácora creada exitosamente");
        response.put("datos", nuevaBitacora);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. Obtener todas (GET)
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTodas() {
        List<BitacorasDeVuelos> bitacoras = bitacorasDeVuelosService.obtenerTodas();
        Map<String, Object> response = new HashMap<>();

        if (bitacoras.isEmpty()) {
            response.put("status", HttpStatus.NO_CONTENT.value());
            response.put("mensaje", "No existen bitácoras de vuelo registradas");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        
        response.put("status", HttpStatus.OK.value());
        response.put("mensaje", "Listado obtenido correctamente");
        response.put("datos", bitacoras);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 3. Obtener por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Long id) {
        BitacorasDeVuelos bitacora = bitacorasDeVuelosService.obtenerPorId(id);
        Map<String, Object> response = new HashMap<>();

        if (bitacora == null) {
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("problema", "No se encontró ninguna bitácora con el ID proporcionado");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        
        response.put("status", HttpStatus.OK.value());
        response.put("mensaje", "Bitácora encontrada");
        response.put("datos", bitacora);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 4. Actualizar (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Long id, @Valid @RequestBody UpdateBitacorasRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return manejarErrores(result);
        }

        BitacorasDeVuelos bitacoraActualizada = bitacorasDeVuelosService.actualizarBitacora(id, request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("mensaje", "Bitácora actualizada correctamente");
        response.put("datos", bitacoraActualizada);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 5. Eliminar (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        bitacorasDeVuelosService.eliminarBitacora(id);
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value()); // Usamos 200 OK para poder enviar el JSON con el mensaje
        response.put("mensaje", "Bitácora eliminada correctamente");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Método privado actualizado para devolver Map<String, Object>
    private ResponseEntity<Map<String, Object>> manejarErrores(BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errores = new HashMap<>();
        
        for (FieldError error : result.getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("problema", "Existen errores de validación en los datos enviados");
        response.put("errores", errores);
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}