package pl.tomwodz.lottogame.domain.resultchecker;

import pl.tomwodz.lottogame.domain.numbergenerator.NumberGeneratorFacade;
import pl.tomwodz.lottogame.domain.numberreceiver.NumberReceiverFacade;

 class ResultCheckerConfiguration {

    public ResultCheckerFacade resultCheckerFacade(NumberReceiverFacade numberReceiverFacade,
                                            NumberGeneratorFacade numberGeneratorFacade,
                                            PlayerRepository playerRepository){
        ResultFactory resultFactory = new ResultFactory();
        WinnerGenerator winnerGenerator = new WinnerGenerator();
        return new ResultCheckerFacade(numberReceiverFacade, numberGeneratorFacade,
                        playerRepository, winnerGenerator, resultFactory);
    }
}
