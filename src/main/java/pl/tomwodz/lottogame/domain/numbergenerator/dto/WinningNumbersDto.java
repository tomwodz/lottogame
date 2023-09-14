package pl.tomwodz.lottogame.domain.numbergenerator.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Data
public class WinningNumbersDto {

    private Set<Integer> winningNumbers;
    private LocalDateTime date;

}
