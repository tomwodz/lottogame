package pl.tomwodz.lottogame.infrastructure.numbergenerator;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.tomwodz.lottogame.domain.numbergenerator.NumberGeneratorFacade;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.WinningNumbersDto;

@Component
@AllArgsConstructor
@Log4j2
public class NumberGeneratorScheduler {

    private final NumberGeneratorFacade numberGeneratorFacade;

    @Scheduled(cron = "${lotto-game.number-generator.lotteryRunOccurrence}")
    public void generateWinningNumbers(){
        log.info("Winning numbers scheduler started.");
        WinningNumbersDto winningNumbersDto = numberGeneratorFacade.generateWinningNumbers();
        log.info(winningNumbersDto.getWinningNumbers());
        log.info(winningNumbersDto.getDate());
    }
}
