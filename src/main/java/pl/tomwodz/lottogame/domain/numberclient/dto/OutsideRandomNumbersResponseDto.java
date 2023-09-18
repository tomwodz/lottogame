package pl.tomwodz.lottogame.domain.numberclient.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record OutsideRandomNumbersResponseDto(
        Set<Integer> outsideSixRandomNumbers
) {
}
