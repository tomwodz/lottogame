package pl.tomwodz.lottogame.domain.validator;

import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;

import java.util.Set;

class ValidatorNumber {

    ;

    boolean areAllNumbersInRange(Set<Integer> numbersFromUser, CriteriaForGenerateNumbersConfigurationProperties criteria) {
        return numbersFromUser.stream()
                .filter(number -> number >= criteria.lowerBand())
                .filter(number -> number <= criteria.upperBand())
                .count() == criteria.count();
    }
}
