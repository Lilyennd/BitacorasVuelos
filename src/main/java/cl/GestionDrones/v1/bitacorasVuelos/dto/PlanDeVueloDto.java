package cl.GestionDrones.v1.bitacorasVuelos.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class PlanDeVueloDto {

    private Long id;
    private String runPiloto;
    private String patenteDron;
    private LocalDate fechaEstimadaVuelo;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String coordenadasOrigen;
    private String coordenadasDestino;
    private Double altitudMaximaMt;
    private String rutContratista;

    public PlanDeVueloDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRunPiloto() { return runPiloto; }
    public void setRunPiloto(String runPiloto) { this.runPiloto = runPiloto; }

    public String getPatenteDron() { return patenteDron; }
    public void setPatenteDron(String patenteDron) { this.patenteDron = patenteDron; }

    public LocalDate getFechaEstimadaVuelo() { return fechaEstimadaVuelo; }
    public void setFechaEstimadaVuelo(LocalDate fechaEstimadaVuelo) { this.fechaEstimadaVuelo = fechaEstimadaVuelo; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public String getCoordenadasOrigen() { return coordenadasOrigen; }
    public void setCoordenadasOrigen(String coordenadasOrigen) { this.coordenadasOrigen = coordenadasOrigen; }

    public String getCoordenadasDestino() { return coordenadasDestino; }
    public void setCoordenadasDestino(String coordenadasDestino) { this.coordenadasDestino = coordenadasDestino; }

    public Double getAltitudMaximaMt() { return altitudMaximaMt; }
    public void setAltitudMaximaMt(Double altitudMaximaMt) { this.altitudMaximaMt = altitudMaximaMt; }

    public String getRutContratista() { return rutContratista; }
    public void setRutContratista(String rutContratista) { this.rutContratista = rutContratista; }

}