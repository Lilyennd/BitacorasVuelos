package cl.GestionDrones.v1.bitacorasVuelos.dto;

import java.util.List;

import cl.GestionDrones.v1.bitacorasVuelos.model.BitacorasDeVuelos;

public class BitacoraDetalleResponse {

    private BitacorasDeVuelos bitacora;
    private PlanDeVueloDto planDeVuelo;
    private List<ReporteIncidente> incidencias;

    public BitacoraDetalleResponse(BitacorasDeVuelos bitacora, 
                                   PlanDeVueloDto planDeVuelo,
                                   List<ReporteIncidente> incidencias) {
        this.bitacora = bitacora;
        this.planDeVuelo = planDeVuelo;
        this.incidencias = incidencias;
    }

    public BitacorasDeVuelos getBitacora() { return bitacora; }
    public PlanDeVueloDto getPlanDeVuelo() { return planDeVuelo; }
    public List<ReporteIncidente> getIncidencias() { return incidencias; }
}