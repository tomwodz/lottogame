package pl.tomwodz.lottogame.domain.numbergenerator.dto;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "lotto-game.number-generator.criteria")
@Builder
public record CriteriaForGenerateNumbersConfigurationProperties(
        int count,
        int lowerBand,
        int upperBand,

        int countOut
) {
}
