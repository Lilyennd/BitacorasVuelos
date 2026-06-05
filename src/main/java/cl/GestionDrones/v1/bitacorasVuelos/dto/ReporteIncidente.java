package cl.GestionDrones.v1.bitacorasVuelos.dto;

import java.time.LocalDateTime;

public record ReporteIncidente(
    Long idPlanVuelo,
    String origenReporte,
    String tipoIncidencia,
    String descripcion,
    LocalDateTime fechaHoraReporte,
    String ubicacionReferencial
) {}
