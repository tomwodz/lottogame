package pl.tomwodz.lottogame.domain.numberreceiver;

import pl.tomwodz.lottogame.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.tomwodz.lottogame.domain.validator.ValidatorFacade;

import java.time.Clock;

class NumberReceiverConfiguration {

    NumberReceiverFacade numberReceiverFacade (TicketRepository ticketRepository,
                                               Clock clock,
                                               HashGenerator hashGenerator,
                                               ValidatorFacade validatorFacade,
                                               DrawDateGeneratorFacade drawDateGeneratorFacade
                                               ){
        TicketFactory ticketFactory = new TicketFactory();
        return new NumberReceiverFacade(ticketRepository,
                clock,
                hashGenerator,
                validatorFacade,
                drawDateGeneratorFacade,
                ticketFactory);
    }

}