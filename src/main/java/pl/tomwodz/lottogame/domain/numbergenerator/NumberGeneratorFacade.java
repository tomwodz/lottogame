package pl.tomwodz.lottogame.domain.numbergenerator;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.tomwodz.lottogame.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.tomwodz.lottogame.domain.numberclient.NumberClientFacade;
import pl.tomwodz.lottogame.domain.numberclient.dto.OutsideRandomNumbersResponseDto;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.WinningNumbersDto;
import pl.tomwodz.lottogame.domain.validator.ValidatorFacade;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@Log4j2
public class NumberGeneratorFacade {

    private final DrawDateGeneratorFacade drawDateGeneratorFacade;
    private final NumberRandomGeneratorRepository numberGeneratorRepository;
    private final ValidatorFacade validatorFacade;
    private final WinningNumbersRepository winningNumbersRepository;
    private final WinningNumbersFactory winningNumbersFactory;
    private final NumberClientFacade numberClientFacade;


    public WinningNumbersDto generateWinningNumbers() {
        LocalDateTime nextDrawDate = drawDateGeneratorFacade.getNextDrawDate();
        try {
            OutsideRandomNumbersResponseDto responseDto = numberClientFacade.getSixOutsideRandomNumbers();
            validatorFacade.validationWinningNumbers(responseDto.outsideSixRandomNumbers());
            WinningNumbers winningOutsideNumbers = winningNumbersFactory
                    .mapFromOutsideRandomNumbersResponseDtoToWinningNumbers(responseDto.outsideSixRandomNumbers(), nextDrawDate);
            WinningNumbers winningNumbersSaved = saveWinningNumber(winningOutsideNumbers);
            return NumberGeneratorMapper.mapFromWinningNumbersToWinningNumbersDto(winningNumbersSaved);
        }
        catch (Exception e){
            log.warn("numberClientFacade error");
        }
        Set<Integer> winningGenerateNumbers = numberGeneratorRepository.generateSixRandomNumbers();
        validatorFacade.validationWinningNumbers(winningGenerateNumbers);
        WinningNumbers winningNumbers = winningNumbersFactory
                .mapFromWinningNumbersDtoToWinningNumbers(winningGenerateNumbers, nextDrawDate);
        WinningNumbers winningNumbersSaved = saveWinningNumber(winningNumbers);
        return NumberGeneratorMapper.mapFromWinningNumbersToWinningNumbersDto(winningNumbersSaved);
    }

    private WinningNumbers saveWinningNumber(WinningNumbers winningNumbers){
        return winningNumbersRepository.save(winningNumbers);
    }

    public WinningNumbersDto retrieveWinningNumberByDate(LocalDateTime date) {
        WinningNumbers numbersByDate = winningNumbersRepository.findByDate(date)
                .orElseThrow(() -> new WinningNumbersNotFoundException("Not Found"));
        return NumberGeneratorMapper.mapFromWinningNumbersToWinningNumbersDto(numbersByDate);
    }

    public boolean areWinningNumbersGeneratedByDate() {
        LocalDateTime nextDrawDate = drawDateGeneratorFacade.getNextDrawDate();
        return winningNumbersRepository.existsByDate(nextDrawDate);
    }

}
