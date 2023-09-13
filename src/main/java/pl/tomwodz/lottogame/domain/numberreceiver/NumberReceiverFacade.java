package pl.tomwodz.lottogame.domain.numberreceiver;

import lombok.AllArgsConstructor;
import pl.tomwodz.lottogame.domain.drawdategenerator.DrawDateGeneratorFacade;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.NumberReceiverResponseDto;
import pl.tomwodz.lottogame.domain.numberreceiver.dto.TicketDto;
import pl.tomwodz.lottogame.domain.validator.ValidatorFacade;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class NumberReceiverFacade {

    private final TicketRepository repository;
    private final Clock clock;
    private final HashGenerator hashGenerator;
    private final ValidatorFacade validatorFacade;
    private final DrawDateGeneratorFacade drawDateGeneratorFacade;
    private final TicketFactory ticketFactory;

    public NumberReceiverResponseDto inputNumbers(Set<Integer> numbersFromUser) {
        if (validatorFacade.validationNumbers(numbersFromUser)) {
            String hash = hashGenerator.getHash();

            TicketDto generatedTicket = TicketDto.builder()
                    .hash(hash)
                    .drawDate(drawDateGeneratorFacade.getNextDrawDate())
                    .numbers(numbersFromUser)
                    .build();

            Ticket ticketToSave = ticketFactory.mapFromTicketDtoToTicket(generatedTicket);

            repository.save(ticketToSave);

            return new NumberReceiverResponseDto(generatedTicket, "Success");
        }
        return new NumberReceiverResponseDto(null, "Failed");
    }

    public List<TicketDto> userNumbers(LocalDateTime date){
        List<Ticket> allTicketsByDrawDate = repository.findAllTicketByDrawDate(date);
        return allTicketsByDrawDate.stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }

}
