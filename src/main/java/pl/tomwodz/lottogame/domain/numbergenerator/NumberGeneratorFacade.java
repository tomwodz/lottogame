package pl.tomwodz.lottogame.domain.numbergenerator;

import lombok.AllArgsConstructor;
import pl.tomwodz.lottogame.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.WinningNumbersDto;
import pl.tomwodz.lottogame.domain.validator.ValidatorFacade;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
public class NumberGeneratorFacade {

    private final DrawDateGeneratorFacade drawDateGeneratorFacade;
    private final NumberRandomGeneratorRepository numberGeneratorRepository;
    private final ValidatorFacade validatorFacade;
    private final WinningNumbersRepository winningNumbersRepository;
    private final WinningNumbersFactory winningNumbersFactory;


    public WinningNumbersDto generateWinningNumbers() {
        LocalDateTime nextDrawDate = drawDateGeneratorFacade.getNextDrawDate();
        Set<Integer> winningGenerateNumbers = numberGeneratorRepository.generateSixRandomNumbers();
        validatorFacade.validationWinningNumbers(winningGenerateNumbers);
        WinningNumbers winningNumbers = winningNumbersFactory
                .mapFromWinningNumbersDtoToWinningNumbers(winningGenerateNumbers, nextDrawDate);
        WinningNumbers winningNumbersSaved = winningNumbersRepository.save(winningNumbers);
        return NumberGeneratorMapper.mapFromWinningNumbersToWinningNumbers(winningNumbersSaved);
    }

    public WinningNumbersDto retrieveWinningNumberByDate(LocalDateTime date) {
        WinningNumbers numbersByDate = winningNumbersRepository.findByDate(date)
                .orElseThrow(() -> new WinningNumbersNotFoundException("Not Found"));
        return NumberGeneratorMapper.mapFromWinningNumbersToWinningNumbers(numbersByDate);
    }

    public boolean areWinningNumbersGeneratedByDate() {
        LocalDateTime nextDrawDate = drawDateGeneratorFacade.getNextDrawDate();
        return winningNumbersRepository.existsByDate(nextDrawDate);
    }

}
