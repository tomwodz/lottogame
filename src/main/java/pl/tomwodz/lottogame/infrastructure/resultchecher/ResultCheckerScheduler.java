package pl.tomwodz.lottogame.infrastructure.resultchecher;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.tomwodz.lottogame.domain.numbergenerator.NumberGeneratorFacade;
import pl.tomwodz.lottogame.domain.numbergenerator.dto.WinningNumbersDto;
import pl.tomwodz.lottogame.domain.resultchecker.ResultCheckerFacade;
import pl.tomwodz.lottogame.domain.resultchecker.dto.PlayersDto;

@Component
@AllArgsConstructor
@Log4j2
public class ResultCheckerScheduler {

    private final ResultCheckerFacade resultCheckerFacade;
    private final NumberGeneratorFacade numberGeneratorFacade;

    @Scheduled(cron = "${lotto-game.result-checker.lotteryRunOccurrence}")
    public PlayersDto generateWinners(){
        log.info("Result Checker scheduler started.");
        if(!numberGeneratorFacade.areWinningNumbersGeneratedByDate()){
            log.info("Winning numbers are not generated");
            throw new RuntimeException("Winning numbers are not generated");
        }
        log.info("Winning numbers has been generated");
        return resultCheckerFacade.generateResults();
    }
}
