package pl.tomwodz.lottogame.domain.numberreceiver.dto;

import lombok.Builder;

@Builder
public record InputViewNumbersRequestDto(
  int first,
  int second,
  int third,
  int fourth,
  int fifth,
  int sixth
) {
}
