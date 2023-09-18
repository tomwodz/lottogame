package pl.tomwodz.lottogame.infrastructure.numberclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.tomwodz.lottogame.domain.numberclient.NumberClientQuery;

import java.time.Duration;

@Configuration
public class NumberClientConfig {


    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(1000))
                .setReadTimeout(Duration.ofMillis(1000))
                .build();
    }

    @Bean
    public NumberClientQuery remoteNumberClientQueryRestTemplate(RestTemplate restTemplate,
                                                                 @Value("${lottogame.numberclient.http.client.config.uri}") String uri,
                                                                 @Value("${lottogame.numberclient.http.client.config.port}") int port
                                                               ) {
        return new NumberClientRestTemplate(restTemplate, uri, port);
    }

}
