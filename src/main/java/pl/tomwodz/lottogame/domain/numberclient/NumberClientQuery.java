package pl.tomwodz.lottogame.domain.numberclient;

import pl.tomwodz.lottogame.domain.numberclient.dto.OutsideRandomNumbersResponseDto;

public interface NumberClientQuery {
    public OutsideRandomNumbersResponseDto getSixOutsideRandomNumbers();
}
