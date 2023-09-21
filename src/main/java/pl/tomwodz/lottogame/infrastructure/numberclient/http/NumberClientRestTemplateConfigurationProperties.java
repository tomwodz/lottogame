package pl.tomwodz.lottogame.infrastructure.numberclient.http;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lotto-game.number-client.http.client.config")
@Builder
public record NumberClientRestTemplateConfigurationProperties(
        String uri,
        int port,
        int connectionTimeout,
        int readTimeout
) {
}

