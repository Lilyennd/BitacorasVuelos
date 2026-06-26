package cl.GestionDrones.v1.bitacorasVuelos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import cl.GestionDrones.v1.bitacorasVuelos.dto.BitacoraDetalleResponse;
import cl.GestionDrones.v1.bitacorasVuelos.dto.CreateBitacorasRequest;
import cl.GestionDrones.v1.bitacorasVuelos.dto.UpdateBitacorasRequest;
import cl.GestionDrones.v1.bitacorasVuelos.model.BitacorasDeVuelos;
import cl.GestionDrones.v1.bitacorasVuelos.service.BitacorasDeVuelosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Bitácoras", description = "Operaciones relacionadas con las bitácoras de vuelo")
@RestController
@RequestMapping("/api/v1/bitacoras")
public class BitacorasDeVuelosController {

    private final BitacorasDeVuelosService bitacorasDeVuelosService;

    public BitacorasDeVuelosController(BitacorasDeVuelosService bitacorasDeVuelosService) {
        this.bitacorasDeVuelosService = bitacorasDeVuelosService;
    }

    @Operation(summary = "Crear una nueva bitácora", description = "Registra una nueva bitácora de vuelo en el sistema con validación de datos")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Estructura JSON de la nueva bitácora de vuelo",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = CreateBitacorasRequest.class),
            examples = @ExampleObject(
                name = "Ejemplo de Creación de Bitácora",
                value = "{\n  \"idPlanVuelo\": 105,\n  \"duracionRealMinutos\": 45,\n  \"observaciones\": \"Vuelo completado con éxito, viento moderado en zona de aterrizaje.\",\n  \"firmaDigital\": \"SHA256-abc123xyz789...\",\n  \"fechaCierre\": \"2026-06-22T15:30:00\"\n}"
            )
        )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bitácora creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BitacorasDeVuelos.class))),
            @ApiResponse(responseCode = "400", description = "Existen errores de validación en los datos enviados", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(
            @Valid @RequestBody CreateBitacorasRequest request,
            BindingResult result) {
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

    @Operation(summary = "Obtener todas las bitácoras", description = "Retorna una lista completa de todas las bitácoras de vuelo registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BitacorasDeVuelos.class))),
            @ApiResponse(responseCode = "204", description = "No existen bitácoras de vuelo registradas", content = @Content)
    })
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

    @Operation(summary = "Actualizar una bitácora", description = "Modifica los datos de una bitácora de vuelo existente de acuerdo con su ID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Estructura JSON con los nuevos campos de la bitácora",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = UpdateBitacorasRequest.class),
            examples = @ExampleObject(
                name = "Ejemplo de Actualización de Bitácora",
                value = "{\n  \"duracionRealMinutos\": 50,\n  \"observaciones\": \"Actualización: Se extiende el tiempo por espera en zona de control de helipuerto.\",\n  \"firmaDigital\": \"SHA256-mod789efg101...\",\n  \"fechaCierre\": \"2026-06-22T15:45:00\"\n}"
            )
        )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bitácora actualizada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BitacorasDeVuelos.class))),
            @ApiResponse(responseCode = "400", description = "Existen errores de validación en los datos enviados", content = @Content),
            @ApiResponse(responseCode = "404", description = "La bitácora de vuelo no existe", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(
            @Parameter(description = "ID de la bitácora que se desea actualizar", required = true, example = "1") @PathVariable Long id,
            @Valid @RequestBody UpdateBitacorasRequest request,
            BindingResult result) {
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

    @Operation(summary = "Eliminar una bitácora", description = "Elimina de forma permanente una bitácora de vuelo del sistema mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bitácora eliminada correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "La bitácora de vuelo no fue encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(
            @Parameter(description = "ID de la bitácora que se desea eliminar", required = true, example = "1") @PathVariable Long id) {
        bitacorasDeVuelosService.eliminarBitacora(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value()); // Usamos 200 OK para poder enviar el JSON con el mensaje
        response.put("mensaje", "Bitácora eliminada correctamente");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Obtener una bitácora por ID", description = "Busca y retorna los detalles y el reporte enriquecido de una bitácora de vuelo utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bitácora encontrada de forma exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BitacoraDetalleResponse.class))),
            @ApiResponse(responseCode = "404", description = "Bitácora no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(
            @Parameter(description = "ID único de la bitácora a consultar", required = true, example = "1") @PathVariable Long id) {
        BitacoraDetalleResponse detalle = bitacorasDeVuelosService.obtenerPorIdConDetalle(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("mensaje", "Bitácora encontrada");
        response.put("datos", detalle);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

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