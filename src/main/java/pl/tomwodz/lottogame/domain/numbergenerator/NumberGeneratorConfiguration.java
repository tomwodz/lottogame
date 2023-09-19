package pl.tomwodz.lottogame.domain.numbergenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tomwodz.lottogame.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.tomwodz.lottogame.domain.numberclient.NumberClientQuery;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;
import pl.tomwodz.lottogame.domain.validator.ValidatorFacade;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
class NumberGeneratorConfiguration {

    @Bean
    WinningNumbersRepository repository(){
       return new WinningNumbersRepository() {
            @Override
            public WinningNumbers save(WinningNumbers winningNumbers) {
                return winningNumbers;
            }

            @Override
            public Optional<WinningNumbers> findByDate(LocalDateTime localDateTime) {
                return Optional.empty();
            }

            @Override
            public boolean existsByDate(LocalDateTime localDateTime) {
                return false;
            }
        };
    }

    @Bean
    public NumberGeneratorFacade numberGeneratorFacade(DrawDateGeneratorFacade drawDateGeneratorFacade,
                                                       NumberRandomGeneratorRepository numberGeneratorRepository,
                                                       ValidatorFacade validatorFacade,
                                                       WinningNumbersRepository winningNumbersRepository,
                                                       NumberClientQuery numberClientQuery,
                                                       CriteriaForGenerateNumbersConfigurationProperties criteria
    ) {
        WinningNumbersFactory winningNumbersFactory = new WinningNumbersFactory();
        return new NumberGeneratorFacade(drawDateGeneratorFacade, numberGeneratorRepository, validatorFacade,
                winningNumbersRepository, winningNumbersFactory, numberClientQuery, criteria);
    }
}
