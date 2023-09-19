package pl.tomwodz.lottogame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;
import pl.tomwodz.lottogame.infrastructure.numberclient.NumberClientRestTemplateConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        CriteriaForGenerateNumbersConfigurationProperties.class,
        NumberClientRestTemplateConfigurationProperties.class})
public class LottoGameApplication {


    public static void main(String[] args) {
        SpringApplication.run(LottoGameApplication.class);
    }
}
