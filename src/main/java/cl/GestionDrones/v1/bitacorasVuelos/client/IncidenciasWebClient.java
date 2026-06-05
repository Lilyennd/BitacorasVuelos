package cl.GestionDrones.v1.bitacorasVuelos.client;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import cl.GestionDrones.v1.bitacorasVuelos.dto.ReporteIncidente;

@Component
public class IncidenciasWebClient {

    private final WebClient webClient;

    public IncidenciasWebClient(@Qualifier("apiIncidenciasWebClient") WebClient webClient) {
        this.webClient = webClient;
    }


    public List<ReporteIncidente> obtenerPorPlanVuelo(Long idPlanVuelo) {
        try {
            return webClient.get()
                    .uri("/plan-vuelo/" + idPlanVuelo)
                    .retrieve()
                    .bodyToFlux(ReporteIncidente.class)
                    .collectList()
                    .block();
        } catch (Exception e) {

            return Collections.emptyList();
        }
    }
}