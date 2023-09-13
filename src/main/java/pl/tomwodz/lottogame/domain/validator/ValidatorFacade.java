package pl.tomwodz.lottogame.domain.validator;

import java.util.Set;

public class ValidatorFacade {

    private static final int MAX_NUMBERS_FROM_USER = 6;
    private static final int MINIMAL_NUMBER_FROM_USER = 1;
    private static final int MAXIMAL_NUMBER_FROM_USER = 99;

    public boolean validationNumbers(Set<Integer> numbersFromUser){
        return areAllNumbersInRange(numbersFromUser);
    }

    public boolean areAllNumbersInRange(Set<Integer> numbersFromUser) {
        return numbersFromUser.stream()
                .filter(number -> number >= MINIMAL_NUMBER_FROM_USER)
                .filter(number -> number <= MAXIMAL_NUMBER_FROM_USER)
                .count() == MAX_NUMBERS_FROM_USER;
    }
}
