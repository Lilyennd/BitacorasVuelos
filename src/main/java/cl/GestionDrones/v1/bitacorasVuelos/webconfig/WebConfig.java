package cl.GestionDrones.v1.bitacorasVuelos.webconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {
    
    @Value("${api.url.planesdevuelos}")
    private String planesDeVuelosUrl;

    @Value("${api.url.incidencias}")
    private String incidenciasUrl;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient planesDeVuelosWebClient(WebClient.Builder builder) {
        return builder.baseUrl(planesDeVuelosUrl).build();
    }

    @Bean
    public WebClient apiIncidenciasWebClient(WebClient.Builder builder) {
        return builder.baseUrl(incidenciasUrl).build();
    }
}