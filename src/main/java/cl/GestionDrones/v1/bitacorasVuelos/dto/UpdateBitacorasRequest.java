package cl.GestionDrones.v1.bitacorasVuelos.dto;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotNull;


public record UpdateBitacorasRequest(
    
    @NotNull(message = "La duración real es obligatoria")
    @Min(value = 1, message = "La duración debe ser de al menos 1 minuto")
    Integer duracionRealMinutos,
    String observaciones

) {}