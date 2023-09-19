package pl.tomwodz.lottogame.domain.numbergenerator;

import org.springframework.stereotype.Component;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
class NumberRandomGenerator implements NumberRandomGeneratorRepository {

    public Set<Integer> generateSixRandomNumbers(CriteriaForGenerateNumbersConfigurationProperties criteria) {
        Set<Integer> winningNumbers = new HashSet<>();
        while (isAmountOfNumbersLowerThanSix(winningNumbers, criteria.count())) {
            int randomNumber = generateRandom(criteria.lowerBand(), criteria.upperBand());
            winningNumbers.add(randomNumber);
        }
        return winningNumbers;
    }

    private boolean isAmountOfNumbersLowerThanSix(Set<Integer> winningNumbers, int count) {
        return winningNumbers.size() < count;
    }

    private int generateRandom(int lowerBand, int upperBand) {
        Random random = new Random();
        return random.nextInt(lowerBand, upperBand + 1);
    }



}
