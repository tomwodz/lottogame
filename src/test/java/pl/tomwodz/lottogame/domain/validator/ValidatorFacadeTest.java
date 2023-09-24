package pl.tomwodz.lottogame.domain.validator;

import org.junit.jupiter.api.Test;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ValidatorFacadeTest {

    CriteriaForGenerateNumbersConfigurationProperties criteria = mock(CriteriaForGenerateNumbersConfigurationProperties.class);

    ValidatorFacade validatorFacade = new ValidatorConfiguration().validatorFacade(criteria);

    @Test
    void invalidUpperWinningNumbersShouldThrowException(){

        //given
        //when
        //then
        assertThrows(IllegalStateException.class,
                ()-> validatorFacade.validationWinningNumbers(Set.of(1, 2, 3, 4, 5, 100)));

    }

    @Test
    void invalidLowerWinningNumbersShouldThrowException(){

        //given
        //when
        //then
        assertThrows(IllegalStateException.class,
                ()-> validatorFacade.validationWinningNumbers(Set.of(0, 2, 3, 4, 5, 6)));

    }

    @Test
    void invalidCountWinningNumbersShouldThrowException(){

        //given
        //when
        //then
        assertThrows(IllegalStateException.class,
                ()-> validatorFacade.validationWinningNumbers(Set.of(0, 2, 3, 4, 5)));

    }

}