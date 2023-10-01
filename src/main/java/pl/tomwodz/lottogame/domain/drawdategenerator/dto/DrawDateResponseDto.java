package pl.tomwodz.lottogame.domain.drawdategenerator.dto;

import lombok.Builder;

@Builder
public record DrawDateResponseDto(
        String dateDraw
) {
}
