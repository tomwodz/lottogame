package pl.tomwodz.lottogame.domain.validator;

import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class ValidatorFacade {

    private final ValidatorNumber validatorNumber;
    private final ValidatorWinningNumbers validatorWinningNumbers;


    public boolean validationNumbers(Set<Integer> numbersFromUser){
        return validatorNumber.areAllNumbersInRange(numbersFromUser);
    }

    public Set<Integer> validationWinningNumbers(Set<Integer> winningNumbers){
        return validatorWinningNumbers.validate(winningNumbers);
    }


}
