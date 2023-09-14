package pl.tomwodz.lottogame.domain.validator;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorFacadeTest {

    ValidatorFacade validatorFacade = new ValidatorConfiguration().validatorFacade();

    @Test
    void invalidWinningNumbersShouldThrowException(){

        //given
        //when
        //then
        assertThrows(IllegalStateException.class,
                ()-> validatorFacade.validationWinningNumbers(Set.of(1, 2, 3, 4, 5, 100)));

    }

}