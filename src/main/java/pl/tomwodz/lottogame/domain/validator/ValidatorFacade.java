package pl.tomwodz.lottogame.domain.validator;

import lombok.AllArgsConstructor;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;

import java.util.Set;

@AllArgsConstructor
public class ValidatorFacade {

    private final ValidatorNumber validatorNumber;
    private final ValidatorWinningNumbers validatorWinningNumbers;
    CriteriaForGenerateNumbersConfigurationProperties criteria;

    public boolean validationNumbers(Set<Integer> numbersFromUser){
        return validatorNumber.areAllNumbersInRange(numbersFromUser, criteria);
    }

    public Set<Integer> validationWinningNumbers(Set<Integer> winningNumbers){
        return validatorWinningNumbers.validate(winningNumbers, criteria);
    }


}
