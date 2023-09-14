package pl.tomwodz.lottogame.domain.validator;

import java.util.Set;

class ValidatorWinningNumbers {

    private final int LOWER_BAND = 0;
    private final int UPPER_BAND = 99;


    Set<Integer> validate(Set<Integer> winningNumbers) {
        if (outOfRange(winningNumbers)) {
            throw new IllegalStateException("Number out of range!");
        }
        return winningNumbers;
    }

    private boolean outOfRange(Set<Integer> winningNumbers) {
        return winningNumbers.stream()
                .anyMatch(number -> number < LOWER_BAND || number > UPPER_BAND);
    }

}
