package pl.tomwodz.lottogame.domain.validator;

import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;

import java.util.Set;

class ValidatorWinningNumbers {


    Set<Integer> validate(Set<Integer> winningNumbers, CriteriaForGenerateNumbersConfigurationProperties criteria) {
        if (outOfRange(winningNumbers, criteria)) {
            throw new IllegalStateException("Number out of range!");
        }
        return winningNumbers;
    }

    private boolean outOfRange(Set<Integer> winningNumbers, CriteriaForGenerateNumbersConfigurationProperties criteria) {

        return winningNumbers.stream()
                .anyMatch(number -> number < criteria.lowerBand() || number > criteria.upperBand());
    }

}
