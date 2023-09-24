package pl.tomwodz.lottogame.domain.numberreceiver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tomwodz.lottogame.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.tomwodz.lottogame.domain.validator.ValidatorFacade;

import java.time.Clock;

@Configuration
class NumberReceiverConfiguration {


    @Bean
    HashGenerator hashGenerator(){
        return new HashGeneratorImpl();
    }

    @Bean
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