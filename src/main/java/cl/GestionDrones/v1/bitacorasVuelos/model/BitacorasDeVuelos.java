package cl.GestionDrones.v1.bitacorasVuelos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "bitacoras_vuelo")
public class BitacorasDeVuelos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación 1 a 1: Un plan de vuelo tiene solo una bitácora de cierre
    @Column(name = "id_plan_vuelo", nullable = false, unique = true)
    private Long idPlanVuelo;

    @Column(name = "duracion_real_minutos", nullable = false)
    private Integer duracionRealMinutos;

    @Column(name = "estado_final", nullable = false)
    private String estadoFinal; 

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "firma_digital", nullable = false, unique = true)
    private String firmaDigital;

    @Column(name = "fecha_cierre", nullable = false)
    private LocalDateTime fechaCierre;

    public BitacorasDeVuelos() {
    }

    public BitacorasDeVuelos(Long id, Long idPlanVuelo, Integer duracionRealMinutos, String estadoFinal, String observaciones, String firmaDigital, LocalDateTime fechaCierre) {
        this.id = id;
        this.idPlanVuelo = idPlanVuelo;
        this.duracionRealMinutos = duracionRealMinutos;
        this.estadoFinal = estadoFinal;
        this.observaciones = observaciones;
        this.firmaDigital = firmaDigital;
        this.fechaCierre = fechaCierre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPlanVuelo() {
        return idPlanVuelo;
    }

    public void setIdPlanVuelo(Long idPlanVuelo) {
        this.idPlanVuelo = idPlanVuelo;
    }

    public Integer getDuracionRealMinutos() {
        return duracionRealMinutos;
    }

    public void setDuracionRealMinutos(Integer duracionRealMinutos) {
        this.duracionRealMinutos = duracionRealMinutos;
    }

    public String getEstadoFinal() {
        return estadoFinal;
    }

    public void setEstadoFinal(String estadoFinal) {
        this.estadoFinal = estadoFinal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFirmaDigital() {
        return firmaDigital;
    }

    public void setFirmaDigital(String firmaDigital) {
        this.firmaDigital = firmaDigital;
    }

    public LocalDateTime getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDateTime fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
}