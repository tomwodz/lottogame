package pl.tomwodz.lottogame.domain.numbergenerator;

import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;

import java.util.Set;

public class NumberRandomGeneratorRepositoryTestImpl implements NumberRandomGeneratorRepository{

    private final Set<Integer> generatedNumbers;

    NumberRandomGeneratorRepositoryTestImpl() {
        generatedNumbers = Set.of(1,2,3,4,5,6);
    }

    NumberRandomGeneratorRepositoryTestImpl(Set<Integer> generatedNumbers) {
        this.generatedNumbers = generatedNumbers;
    }
    @Override
    public Set<Integer> generateSixRandomNumbers(CriteriaForGenerateNumbersConfigurationProperties criteria) {
        return generatedNumbers;
    }

}
