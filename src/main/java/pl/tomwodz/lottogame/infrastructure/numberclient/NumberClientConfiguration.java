package pl.tomwodz.lottogame.infrastructure.numberclient;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.tomwodz.lottogame.domain.numberclient.NumberClientQuery;

import java.time.Duration;

@Configuration
public class NumberClientConfiguration {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler, NumberClientRestTemplateConfigurationProperties properties) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(properties.connectionTimeout()))
                .setReadTimeout(Duration.ofMillis(properties.readTimeout()))
                .build();
    }

    @Bean
    public NumberClientQuery remoteNumberClientQueryRestTemplate(RestTemplate restTemplate, NumberClientRestTemplateConfigurationProperties properties) {
        return new NumberClientRestTemplate(restTemplate, properties.uri(), properties.port());
    }

}
