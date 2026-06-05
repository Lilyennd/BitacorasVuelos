package cl.GestionDrones.v1.bitacorasVuelos.webconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }


    @Bean
    public WebClient planesDeVuelosWebClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8083/api/v1/planesDeVuelos").build();
    }

    @Bean
    public WebClient apiIncidenciasWebClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8089/api/v1/incidencias").build();
    }
}
