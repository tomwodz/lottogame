package pl.tomwodz.lottogame.domain.validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfiguration {

    @Bean
    public ValidatorFacade validatorFacade(){
        ValidatorNumber validatorNumber = new ValidatorNumber();
        ValidatorWinningNumbers validatorWinningNumbers = new ValidatorWinningNumbers();
        return new ValidatorFacade(validatorNumber, validatorWinningNumbers);
    }
}
