package cl.GestionDrones.v1.bitacorasVuelos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBitacorasRequest(
    
    @NotNull(message = "El ID del plan de vuelo es obligatorio")
    Long idPlanVuelo,

    @NotNull(message = "La duración real es obligatoria")
    @Min(value = 1, message = "La duración debe ser de al menos 1 minuto")
    Integer duracionRealMinutos,

    @NotBlank(message = "El estado final es obligatorio (ej. Completado, Cancelado, Incompleto)")
    String estadoFinal,

    // Las observaciones son opcionales
    String observaciones

) {}
    
