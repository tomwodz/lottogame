package pl.tomwodz.lottogame.domain.resultchecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tomwodz.lottogame.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.tomwodz.lottogame.domain.numbergenerator.NumberGeneratorFacade;
import pl.tomwodz.lottogame.domain.numberreceiver.NumberReceiverFacade;

import java.util.List;
import java.util.Optional;

@Configuration
 class ResultCheckerConfiguration {

    @Bean
    public ResultCheckerFacade resultCheckerFacade(NumberReceiverFacade numberReceiverFacade,
                                                   NumberGeneratorFacade numberGeneratorFacade,
                                                   PlayerRepository playerRepository, DrawDateGeneratorFacade drawDateGeneratorFacade){
        ResultFactory resultFactory = new ResultFactory();
        WinnerGenerator winnerGenerator = new WinnerGenerator();
        return new ResultCheckerFacade(numberReceiverFacade, numberGeneratorFacade,
                        playerRepository, winnerGenerator, resultFactory, drawDateGeneratorFacade);
    }
}
