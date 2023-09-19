package pl.tomwodz.lottogame.domain.numberclient;

import pl.tomwodz.lottogame.domain.numberclient.dto.OutsideRandomNumbersResponseDto;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.CriteriaForGenerateNumbersConfigurationProperties;

public interface NumberClientQuery {
    public OutsideRandomNumbersResponseDto getSixOutsideRandomNumbers(CriteriaForGenerateNumbersConfigurationProperties criteria);
}
