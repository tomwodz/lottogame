package pl.tomwodz.lottogame.domain.numbergenerator;

import java.time.LocalDateTime;
import java.util.Set;

class WinningNumbersFactory {


    WinningNumbers mapFromWinningNumbersDtoToWinningNumbers(Set<Integer> winningGenerateNumbers, LocalDateTime nextDrawDate){
        return new WinningNumbers(
                winningGenerateNumbers,
                nextDrawDate
        );
    }

    WinningNumbers mapFromOutsideRandomNumbersResponseDtoToWinningNumbers(Set<Integer> winningOutsideNumbers, LocalDateTime nextDrawDate){
        return new WinningNumbers(
                winningOutsideNumbers,
                nextDrawDate
        );
    }

}
