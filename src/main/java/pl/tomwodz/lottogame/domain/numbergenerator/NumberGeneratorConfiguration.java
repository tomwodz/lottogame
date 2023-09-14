package pl.tomwodz.lottogame.domain.numbergenerator;

import pl.tomwodz.lottogame.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.tomwodz.lottogame.domain.validator.ValidatorFacade;

class NumberGeneratorConfiguration {

    public NumberGeneratorFacade numberGeneratorFacade(DrawDateGeneratorFacade drawDateGeneratorFacade,
                                                       NumberRandomGeneratorRepository numberGeneratorRepository,
                                                       ValidatorFacade validatorFacade,
                                                       WinningNumbersRepository winningNumbersRepository
    ) {
        WinningNumbersFactory winningNumbersFactory = new WinningNumbersFactory();
        return new NumberGeneratorFacade(drawDateGeneratorFacade, numberGeneratorRepository, validatorFacade,
                winningNumbersRepository, winningNumbersFactory);
    }
}
