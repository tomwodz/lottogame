package pl.tomwodz.lottogame.domain.numbergenerator;

import pl.tomwodz.lottogame.domain.numbergenerator.dto.WinningNumbersDto;

class NumberGeneratorMapper {

    public  static  WinningNumbersDto mapFromWinningNumbersToWinningNumbersDto(WinningNumbers winningNumbers){
        return WinningNumbersDto.builder()
                .winningNumbers(winningNumbers.getWinningNumbers())
                .date(winningNumbers.getDate())
                .build();
    }
}
