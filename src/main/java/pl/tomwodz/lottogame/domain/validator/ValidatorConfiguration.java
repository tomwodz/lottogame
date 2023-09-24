package pl.tomwodz.lottogame.domain.validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;

@Configuration
public class ValidatorConfiguration {

    @Bean
    public ValidatorFacade validatorFacade(CriteriaForGenerateNumbersConfigurationProperties criteria){
        ValidatorNumber validatorNumber = new ValidatorNumber();
        ValidatorWinningNumbers validatorWinningNumbers = new ValidatorWinningNumbers();
        return new ValidatorFacade(validatorNumber, validatorWinningNumbers, criteria);
    }
}
