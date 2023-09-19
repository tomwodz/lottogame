package pl.tomwodz.lottogame.domain.numbergenerator;

import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;

import java.util.Set;

interface NumberRandomGeneratorRepository {

    Set<Integer> generateSixRandomNumbers(CriteriaForGenerateNumbersConfigurationProperties criteria);

}
