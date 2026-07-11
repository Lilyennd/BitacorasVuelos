package cl.GestionDrones.v1.bitacorasVuelos.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import cl.GestionDrones.v1.bitacorasVuelos.dto.PlanDeVueloDto;
import cl.GestionDrones.v1.bitacorasVuelos.exception.PlanInalcanzableException;
import cl.GestionDrones.v1.bitacorasVuelos.exception.ResourceNotFoundException;

@Component
public class PlanesDeVuelosClient {

    private final WebClient webClient;

    public PlanesDeVuelosClient(@Qualifier("planesDeVuelosWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public PlanDeVueloDto obtenerPorId(Long idPlanVuelo) {
        try {
            return webClient.get()
                    .uri("/" + idPlanVuelo)
                    .retrieve()
                    .bodyToMono(PlanDeVueloDto.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new ResourceNotFoundException(
                "El plan de vuelo con ID " + idPlanVuelo + " no existe.");
        } catch (Exception e) {
            throw new PlanInalcanzableException(
                "No se puede conectar con el servicio de Planes de Vuelo.");
        }
    }
}