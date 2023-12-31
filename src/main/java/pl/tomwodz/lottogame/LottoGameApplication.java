package pl.tomwodz.lottogame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;
import pl.tomwodz.lottogame.infrastructure.numberclient.http.NumberClientRestTemplateConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        CriteriaForGenerateNumbersConfigurationProperties.class,
        NumberClientRestTemplateConfigurationProperties.class})
@EnableScheduling
@EnableMongoRepositories
public class LottoGameApplication {


    public static void main(String[] args) {
        SpringApplication.run(LottoGameApplication.class);
    }
}
